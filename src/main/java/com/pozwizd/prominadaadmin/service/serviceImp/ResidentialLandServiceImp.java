package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.exception.OperationException;
import com.pozwizd.prominadaadmin.filter.ResidentialLandFilterRequest;
import com.pozwizd.prominadaadmin.mapper.property.residentialLand.ResidentialLandMapper;
import com.pozwizd.prominadaadmin.models.property.residentialLand.request.ResidentialLandRequest;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandResponse;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandTableResponse;
import com.pozwizd.prominadaadmin.repository.primary.DataTablesRepository.ResidentialLandRepositoryDT;
import com.pozwizd.prominadaadmin.repository.primary.ResidentialLandFileRepository;
import com.pozwizd.prominadaadmin.repository.primary.ResidentialLandRepository;
import com.pozwizd.prominadaadmin.service.FileService;
import com.pozwizd.prominadaadmin.service.ImageService;
import com.pozwizd.prominadaadmin.service.ResidentialLandService;
import com.pozwizd.prominadaadmin.specification.ResidentialLandSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResidentialLandServiceImp implements ResidentialLandService {

    private final ResidentialLandRepository residentialLandRepository;
    private final ResidentialLandRepositoryDT residentialLandRepositoryDT;
    private final ResidentialLandMapper residentialLandMapper;
    private final FileService fileService;
    private final ImageService imageService;
    private final ResidentialLandFileRepository residentialLandFileRepository;

    @Override
    public void save(ResidentialLand residentialLand) {
        residentialLandRepository.save(residentialLand);
    }

    @Override
    public Optional<ResidentialLand> findById(Long id) {
        return residentialLandRepository.findById(id);
    }

    @Override
    @Transactional
    public ResidentialLandResponse readById(Long id) {
        ResidentialLand residentialLand = residentialLandRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new OperationException("получении участка",
                "Участок с ID " + id + " не найден", HttpStatus.NOT_FOUND));
        log.info("Участок с ID {} успешно получен", id);
        return residentialLandMapper.toResidentialLandResponse(residentialLand);
    }

    @Override
    public void deleteById(Long id) {
        residentialLandRepository.deleteById(id);
    }


    @Override
    public Page<ResidentialLandTableResponse> getAll(String street, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ResidentialLand> residentialLands
                = residentialLandRepository.findAll(
                (root, query, criteriaBuilder)
                        -> criteriaBuilder.like(criteriaBuilder.lower(root.get("street")), "%" + street.toLowerCase() + "%"), pageRequest);
        return residentialLandMapper.toResidentialLandTableResponses(residentialLands);
    }

    @Override
    public Page<ResidentialLandTableResponse> getAllByFilter(Integer page, Integer size,
                                                             ResidentialLandFilterRequest filterRequest) {

        PageRequest pageRequest = PageRequest.of(page, size);


        Page<ResidentialLand> residentialLands
                = residentialLandRepository.findAll(ResidentialLandSpecification.search(filterRequest), pageRequest);

        return residentialLandMapper.toResidentialLandTableResponses(residentialLands);
    }

    @Override
    public DataTablesOutput<ResidentialLandTableResponse> getAllByFilterDT(DataTablesInput input) {

        DataTablesOutput<ResidentialLand> output1 = residentialLandRepositoryDT.findAll(input);
        List<ResidentialLandTableResponse> response = residentialLandMapper.toResidentialLandTableResponses(output1.getData());
        DataTablesOutput<ResidentialLandTableResponse> output2 = new DataTablesOutput<>();
        output2.setDraw(input.getDraw());
        output2.setRecordsTotal(output1.getRecordsTotal());
        output2.setRecordsFiltered(output1.getRecordsFiltered());
        output2.setData(response);

        return output2;
    }



    @Override
    @Transactional
    public ResidentialLandResponse updateResidentialLand(ResidentialLandRequest residentialLandRequest) {
        ResidentialLand residentialLand = residentialLandRepository.findByIdWithDetails(
                residentialLandRequest.getId()
        ).orElseThrow(() -> new OperationException("получении участка",
                "Участок с ID " + residentialLandRequest.getId() + " не найден", HttpStatus.NOT_FOUND));

        residentialLandMapper.updateResidentialLandFromRequest(residentialLandRequest, residentialLand);
        log.info("Участок с ID {} успешно обновлен", residentialLandRequest.getId());
        return residentialLandMapper.toResidentialLandResponse(residentialLand);


    }

    @Override
    public ResidentialLandResponse saveFromRequest(ResidentialLandRequest residentialLandRequest) {
        try {
            ResidentialLand residentialLand =
                    residentialLandRepository.save(
                            residentialLandMapper.toResidentialLand(residentialLandRequest));
            log.info("Участок с ID {} успешно создан", residentialLandRequest.getId());
            return residentialLandMapper.toResidentialLandResponse(residentialLand);
        } catch (Exception e) {
            log.error("Ошибка при создании участка", e);
            throw new RuntimeException("Ошибка при создании участка", e);
        }

    }
}
