package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorProperty;
import com.pozwizd.prominadaadmin.exception.OperationException;
import com.pozwizd.prominadaadmin.mapper.property.investor.InvestorPropertyMapper;
import com.pozwizd.prominadaadmin.models.filter.PropertiesFilter;
import com.pozwizd.prominadaadmin.models.property.investor.request.InvestorPropertyRequest;
import com.pozwizd.prominadaadmin.models.property.investor.response.InvestorPropertyResponse;
import com.pozwizd.prominadaadmin.models.property.investor.response.InvestorPropertyResponseForTable;
import com.pozwizd.prominadaadmin.repository.InvestorPropertyRepository;
import com.pozwizd.prominadaadmin.service.InvestorPropertyService;
import com.pozwizd.prominadaadmin.specification.BuilderPropertySpecification;
import com.pozwizd.prominadaadmin.specification.InvestorPropertySpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvestorPropertyServiceImp implements InvestorPropertyService {

    private final InvestorPropertyRepository investorPropertyRepository;
    private final InvestorPropertyMapper investorPropertyMapper;

    @Override
    public InvestorProperty create(InvestorProperty investorProperty) {
        return investorPropertyRepository.save(investorProperty);
    }

    @Override
    public InvestorProperty readById(Long id) {
        return investorPropertyRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new OperationException("retrieving investor property",
                        "Investor property with ID " + id + " not found", HttpStatus.NOT_FOUND));
    }


    @Override
    @Transactional
    public InvestorPropertyResponse createFromRequest(InvestorPropertyRequest investorPropertyRequest) {
        try {
            InvestorProperty investorProperty = investorPropertyMapper.toEntity(investorPropertyRequest);
            InvestorProperty investorPropertySave = investorPropertyRepository.save(investorProperty);
            log.info("Investor property with ID {} successfully created", investorProperty.getId());
            return investorPropertyMapper.toResponse(investorPropertySave);
        } catch (Exception e) {
            log.error("Error creating investor property", e);
            throw new RuntimeException("Error creating investor property", e);
        }
    }

    @Override
    @Transactional
    public InvestorPropertyResponse readResponseById(Long id) {
        InvestorProperty investorProperty = investorPropertyRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new OperationException("retrieving investor property",
                        "Investor property with ID " + id + " not found", HttpStatus.NOT_FOUND));
        log.info("Investor property with ID {} successfully retrieved", id);
        return investorPropertyMapper.toResponse(investorProperty);
    }

    @Override
    @Transactional
    public InvestorPropertyResponse update(InvestorPropertyRequest investorPropertyRequest) {
        InvestorProperty investorProperty = readById(investorPropertyRequest.getId());
        investorPropertyMapper.updateFromRequest(investorPropertyRequest, investorProperty);

        log.info("Saving investor property with ID {}", investorProperty.getId());
        try {
            investorProperty = investorPropertyRepository.save(investorProperty);
            log.info("Investor property with ID {} successfully saved", investorProperty.getId());
        } catch (Exception e) {
            log.error("Failed to save investor property with ID {}: {}",
                    investorProperty.getId(), e.getMessage(), e);
            throw e;
        }

        return investorPropertyMapper.toResponse(investorProperty);
    }


    @Override
    @Transactional
    public Boolean deleteById(Long id) {
        InvestorProperty entity = readById(id);
        try {
            Hibernate.initialize(entity.getInvestorPropertyMain());
            Hibernate.initialize(entity.getInvestorPropertyFiles());
            Hibernate.initialize(entity.getInvestorPropertyGalleryImages());

            entity.getInvestorPropertyFiles().clear();
            entity.getInvestorPropertyGalleryImages().clear();

            if (entity.getInvestorPropertyMain() != null) {
                entity.setInvestorPropertyMain(null);
            }

            investorPropertyRepository.saveAndFlush(entity);
            investorPropertyRepository.delete(entity);
            investorPropertyRepository.flush();

            log.info("Investor property with ID {} successfully deleted", id);
            return true;
        } catch (Exception e) {
            log.error("Error deleting investor property with ID {}: {}", id, e.getMessage(), e);
            throw new OperationException("deleting investor property",
                    "Failed to delete: " + e.getMessage());
        }
    }

    @Override
    public Page<InvestorPropertyResponseForTable> getInvestorPropertyByPagination(PropertiesFilter filter) {
        PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize());
        return investorPropertyMapper.toResponseForTablePage(
                investorPropertyRepository.findAll(
                        InvestorPropertySpecification.search(filter),
                        pageRequest
                )
        );
    }

}