package com.pozwizd.prominadaadmin.service.location;

import com.pozwizd.prominadaadmin.entity.location.Topozone;

import java.util.List;

public interface TopozoneService {
    Topozone save(Topozone topozone);

    List<Topozone> getAll();

    Topozone getById(Long topozoneId);
}
