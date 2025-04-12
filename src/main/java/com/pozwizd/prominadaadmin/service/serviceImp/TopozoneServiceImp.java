package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.other.Topozone;
import com.pozwizd.prominadaadmin.repository.TopozoneRepository;
import com.pozwizd.prominadaadmin.service.TopozoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopozoneServiceImp implements TopozoneService {
    private final TopozoneRepository topozoneRepository;
    @Override
    public Topozone save(Topozone topozone) {
        return topozoneRepository.save(topozone);
    }

    @Override
    public List<Topozone> getAll() {
        return topozoneRepository.findAll();
    }
}
