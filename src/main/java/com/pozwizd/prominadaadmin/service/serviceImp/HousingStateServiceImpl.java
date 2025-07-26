package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.property.HousingState;
import com.pozwizd.prominadaadmin.repository.HousingStateRepository;
import com.pozwizd.prominadaadmin.service.HousingStateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HousingStateServiceImpl implements HousingStateService {

    private final HousingStateRepository housingStateRepository;

    @Override
    public HousingState create(HousingState housingState) {
        return housingStateRepository.save(housingState);
    }

    @Override
    public HousingState readById(Long id) {
        return housingStateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Housing state not found with id: " + id));
    }

    @Override
    public HousingState update(Long id, HousingState housingState) {
        return housingStateRepository.findById(id)
                .map(existingState -> {
                    existingState.setName(housingState.getName());
                    existingState.setDescription(housingState.getDescription());
                    return housingStateRepository.save(existingState);
                })
                .orElseThrow(() -> new IllegalArgumentException("Housing state not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        if (!housingStateRepository.existsById(id)) {
            throw new IllegalArgumentException("Housing state not found with id: " + id);
        }
        housingStateRepository.deleteById(id);
    }
}
