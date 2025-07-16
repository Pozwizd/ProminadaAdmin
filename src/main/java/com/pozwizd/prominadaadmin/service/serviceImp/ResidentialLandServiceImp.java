package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLandFile;
import com.pozwizd.prominadaadmin.filter.ResidentialLandFilterRequest;
import com.pozwizd.prominadaadmin.mapper.ResidentialLandMapper;
import com.pozwizd.prominadaadmin.models.property.residentialLand.request.ResidentialLandRequest;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandTableResponse;
import com.pozwizd.prominadaadmin.repository.primary.DataTablesRepository.ResidentialLandRepositoryDT;
import com.pozwizd.prominadaadmin.repository.primary.ResidentialLandFileRepository;
import com.pozwizd.prominadaadmin.repository.primary.ResidentialLandRepository;
import com.pozwizd.prominadaadmin.service.FileService;
import com.pozwizd.prominadaadmin.service.ImageService;
import com.pozwizd.prominadaadmin.service.ResidentialLandService;
import com.pozwizd.prominadaadmin.specification.ResidentialLandSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public void updateResidentialLand(ResidentialLandRequest residentialLandRequest) {
        ResidentialLand residentialLand = residentialLandRepository.findById(residentialLandRequest.getId())
                .orElseThrow(() -> {
                    log.error("ResidentialLand not found with id {}", residentialLandRequest.getId());
                    return new RuntimeException("ResidentialLand not found with id " + residentialLandRequest.getId());
                });

        residentialLandMapper.updateResidentialLandFromRequest(residentialLandRequest, residentialLand);

        if (residentialLandRequest.getResidentialLandGalleryImages() != null && !residentialLandRequest.getResidentialLandGalleryImages().isEmpty()) {
            residentialLandRequest.getResidentialLandGalleryImages().forEach(imageRequest -> {
                if (imageRequest.getFile() != null) {
                    imageService.saveResidentialLandGalleryImage(imageRequest.getFile(), residentialLand);
                }
            });
        }

        if (residentialLandRequest.getResidentialLandFiles() != null && !residentialLandRequest.getResidentialLandFiles().isEmpty()) {
            residentialLandRequest.getResidentialLandFiles().forEach(fileRequest -> {
                if (fileRequest.getFile() != null) {
                    fileService.saveResidentialLandFile(fileRequest.getFile(), residentialLand);
                    try {
                        String filePath = fileService.uploadFile(fileRequest.getFile());
                        ResidentialLandFile residentialLandFile = new ResidentialLandFile();
                        residentialLandFile.setFilePath(filePath);
                        residentialLandFile.setResidentialLand(residentialLand);
                        residentialLandFileRepository.save(residentialLandFile);
                    } catch (IOException e) {
                        log.error("Failed to store file {}: {}", fileRequest.getFile().getOriginalFilename(), e.getMessage());
                        throw new RuntimeException("Failed to store file " + fileRequest.getFile().getOriginalFilename(), e);
                    }
                }
            });
        }

        residentialLandRepository.save(residentialLand);
    }

    @Override
    public void saveFromRequest(ResidentialLandRequest residentialLandRequest) {

    }
}
