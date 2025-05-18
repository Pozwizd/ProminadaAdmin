package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty;
import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderPropertyGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderPropertyLayouts;
import com.pozwizd.prominadaadmin.mapper.BuilderPropertyMapper;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderForView;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDto;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDtoForTable;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyLayoutDto;
import com.pozwizd.prominadaadmin.models.media.MediaDtoDrop;
import com.pozwizd.prominadaadmin.repository.BuilderPropertyRepository;
import com.pozwizd.prominadaadmin.service.*;
import com.pozwizd.prominadaadmin.specification.BuilderPropertySpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuilderPropertyServiceImp implements BuilderPropertyService {
    private final BuilderPropertyRepository builderPropertyRepository;
    private final BuilderPropertyMapper builderPropertyMapper;
    private final ImageService imageService;
    private final RegDistrictService regDistrictService;
    private final DistrictServiceImp districtServiceImp;
    private final CityServiceImp cityServiceImp;
    private final TopozoneService topozoneService;
    private final FileService fileService;

    @Value("${file.upload.dir}")
    private String contextPath;

    @Override
    public Page<BuilderPropertyDtoForTable> getPageableBuilders(int page, Integer size, BuilderPropertyDtoForTable builderPropertyDto) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return builderPropertyMapper.toDto(builderPropertyRepository.findAll(BuilderPropertySpecification.search(builderPropertyDto), pageRequest));
    }

    @Override
    public void deleteById(Long id) {
        builderPropertyRepository.deleteById(id);
    }

    @Override
    public BuilderProperty getById(Long id) {
        return builderPropertyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Builder property with id: " + id + " was not found!"));
    }

    @Override
    public BuilderPropertyDto getByIdInDto(Long id) {
        return builderPropertyMapper.toDto(getById(id));
    }

    @Override
    public BuilderForView getByIdInDtoForView(Long id) {
        return builderPropertyMapper.toDtoForView(getById(id));
    }

    @Override
    public BuilderProperty save(BuilderProperty builderProperty) {
        return builderPropertyRepository.save(builderProperty);
    }

    @SneakyThrows
    @Override
    public BuilderProperty save(BuilderPropertyDto dto) {
        if (dto.getChessPlanFile() != null) {
            try {
                dto.setPathToChessPlanFile(fileService.uploadFile(dto.getChessPlanFile()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            dto.setPathToChessPlanFile(dto.getPathToChessPlanFile());
        }

        if (dto.getPriceFile() != null) {
            try {
                dto.setPathToPriceFile(fileService.uploadFile(dto.getPriceFile()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            dto.setPathToPriceFile(dto.getPathToPriceFile());
        }

        if (dto.getMortgageConditionsFile() != null) {
            try {
                dto.setPathToMortgageConditionsFile(fileService.uploadFile(dto.getMortgageConditionsFile()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            dto.setPathToMortgageConditionsFile(dto.getPathToMortgageConditionsFile());
        }

        BuilderProperty builderProperty = builderPropertyMapper.toEntityFromRequest(dto);
        if (dto.getRegDistrictId() != null && !dto.getRegDistrictId().isEmpty())
            builderProperty.setRegDistrict(regDistrictService.getById(Long.parseLong(dto.getRegDistrictId())));
        if (dto.getCityId() != null && !dto.getCityId().isEmpty())
            builderProperty.setCity(cityServiceImp.getById(Long.parseLong(dto.getCityId())));
        if (dto.getDistrictId() != null && !dto.getDistrictId().isEmpty())
            builderProperty.setDistinct(districtServiceImp.getById(Long.parseLong(dto.getDistrictId())));
        if (dto.getTopozoneId() != null && !dto.getTopozoneId().isEmpty())
            builderProperty.setTopozone(topozoneService.getById(Long.parseLong(dto.getTopozoneId())));

        if(dto.getLayoutDto()!=null){
            for (BuilderPropertyLayoutDto m : dto.getLayoutDto()) {
                if (m.getFile1() != null) {
                    try {
                        m.setPathImage1(fileService.uploadFile(m.getFile1()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    m.setPathImage1(m.getPathImage1());
                }

                if (m.getFile2() != null) {
                    try {
                        m.setPathImage2(fileService.uploadFile(m.getFile2()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    m.setPathImage2(m.getPathImage2());
                }

                if (m.getFile3() != null) {
                    try {
                        m.setPathImage3(fileService.uploadFile(m.getFile3()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    m.setPathImage3(m.getPathImage3());
                }
            }
            List<BuilderPropertyLayouts> layouts = dto.getLayoutDto().stream().map(d -> builderPropertyMapper.toEntityFromRequest(d, builderProperty)).toList();
            builderProperty.setBuilderPropertyLayouts(layouts);
        }
        if (dto.getFilesDto() != null) {
            for (MediaDtoDrop m : dto.getFilesDto()) {
                if (m.getFile() != null) {
                    try {
                        m.setPathImage(fileService.uploadFile(m.getFile()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    m.setPathImage(m.getPathImage());
                }
            }
            List<BuilderPropertyGalleryImage> gallery = dto.getFilesDto().stream().map(d -> builderPropertyMapper.toEntityFromRequest(d, builderProperty)).toList();
            builderProperty.setBuilderPropertyGalleryImages(gallery);
        }

        BuilderProperty entity = save(builderProperty);
        return entity;
    }

    @SneakyThrows
    @Override
    public BuilderProperty update(BuilderPropertyDto dto) {
        Optional<BuilderProperty> oldBuilderProperty = builderPropertyRepository.findById(dto.getId());
        if (dto.getChessPlanFile() != null) {
            try {
                dto.setPathToChessPlanFile(fileService.uploadFile(dto.getChessPlanFile()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            dto.setPathToChessPlanFile(dto.getPathToChessPlanFile());
        }

        if (dto.getPriceFile() != null) {
            try {
                dto.setPathToPriceFile(fileService.uploadFile(dto.getPriceFile()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            dto.setPathToPriceFile(dto.getPathToPriceFile());
        }

        if (dto.getMortgageConditionsFile() != null) {
            try {
                dto.setPathToMortgageConditionsFile(fileService.uploadFile(dto.getMortgageConditionsFile()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            dto.setPathToMortgageConditionsFile(dto.getPathToMortgageConditionsFile());
        }

        if (dto.getId() != null && oldBuilderProperty.isPresent()) {
            if (dto.getPathToChessPlanFile() != null && dto.getPathToChessPlanFile().isEmpty()) {
                imageService.deleteByPath(oldBuilderProperty.get().getPathToChessPlanFile());
            }
            if (dto.getPathToMortgageConditionsFile() != null && dto.getPathToMortgageConditionsFile().isEmpty()) {
                imageService.deleteByPath(oldBuilderProperty.get().getPathToMortgageConditionsFile());
            }
            if (dto.getPathToPriceFile() != null && dto.getPathToPriceFile().isEmpty()) {
                imageService.deleteByPath(oldBuilderProperty.get().getPathToPriceFile());
            }
        }

        BuilderProperty newBuilderProperty = builderPropertyMapper.toEntityFromRequest(dto);
        if (dto.getRegDistrictId() != null && !dto.getRegDistrictId().isEmpty())
            newBuilderProperty.setRegDistrict(regDistrictService.getById(Long.parseLong(dto.getRegDistrictId())));
        if (dto.getCityId() != null && !dto.getCityId().isEmpty())
            newBuilderProperty.setCity(cityServiceImp.getById(Long.parseLong(dto.getCityId())));
        if (dto.getDistrictId() != null && !dto.getDistrictId().isEmpty())
            newBuilderProperty.setDistinct(districtServiceImp.getById(Long.parseLong(dto.getDistrictId())));
        if (dto.getTopozoneId() != null && !dto.getTopozoneId().isEmpty())
            newBuilderProperty.setTopozone(topozoneService.getById(Long.parseLong(dto.getTopozoneId())));

        if (dto.getLayoutDto() != null) {
            for (BuilderPropertyLayoutDto m : dto.getLayoutDto()) {
                if (m.getFile1() != null) {
                    try {
                        m.setPathImage1(fileService.uploadFile(m.getFile1()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    m.setPathImage1(m.getPathImage1());
                }

                if (m.getFile2() != null) {
                    try {
                        m.setPathImage2(fileService.uploadFile(m.getFile2()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    m.setPathImage2(m.getPathImage2());
                }

                if (m.getFile3() != null) {
                    try {
                        m.setPathImage3(fileService.uploadFile(m.getFile3()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    m.setPathImage3(m.getPathImage3());
                }
            }
            List<BuilderPropertyLayouts> layouts = dto.getLayoutDto().stream().map(d -> builderPropertyMapper.toEntityFromRequest(d, newBuilderProperty)).toList();
            newBuilderProperty.setBuilderPropertyLayouts(layouts);
        }
        if (dto.getFilesDto() != null) {
            for (MediaDtoDrop m : dto.getFilesDto()) {
                if (m.getFile() != null) {
                    try {
                        m.setPathImage(fileService.uploadFile(m.getFile()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    m.setPathImage(m.getPathImage());
                }
            }
            List<BuilderPropertyGalleryImage> gallery = dto.getFilesDto().stream().map(d -> builderPropertyMapper.toEntityFromRequest(d, newBuilderProperty)).toList();
            newBuilderProperty.setBuilderPropertyGalleryImages(gallery);
        }

        return save(newBuilderProperty);
    }

    @Override
    public List<BuilderProperty> getAll() {
        return builderPropertyRepository.findAll();
    }
}
