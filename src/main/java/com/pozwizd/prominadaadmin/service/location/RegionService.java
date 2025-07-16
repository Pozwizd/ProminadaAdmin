package com.pozwizd.prominadaadmin.service.location;

import com.pozwizd.prominadaadmin.entity.location.Region;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface RegionService {

    Region save(Region region);


    CompletableFuture<Region> getOrCreate(String name);


    CompletableFuture<List<Region>>  getRegions();

    List<Region> getPageableRegions(String region);

}
