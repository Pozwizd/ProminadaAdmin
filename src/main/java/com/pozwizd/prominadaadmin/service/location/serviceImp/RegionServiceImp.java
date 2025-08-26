package com.pozwizd.prominadaadmin.service.location.serviceImp;

import com.pozwizd.prominadaadmin.entity.location.Region;
import com.pozwizd.prominadaadmin.repository.secondary.RegionRepository;
import com.pozwizd.prominadaadmin.service.location.RegionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "regions") // дефолтный кэш для класса
public class RegionServiceImp implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public Region save(Region region) {
        return regionRepository.save(region);
    }

    @Override
    @Cacheable(key = "#name") // берёт cacheNames = "regions" из @CacheConfig
    public CompletableFuture<Region> getOrCreate(String name) {
        return CompletableFuture.supplyAsync(() -> {
            log.debug("Attempting to get or create region with name: {}", name);
            try {
                Region region = regionRepository.findByName(name)
                        .orElseGet(() -> {
                            log.info("Region with name '{}' not found, creating new one", name);
                            Region newRegion = new Region();
                            newRegion.setName(name);
                            return regionRepository.save(newRegion);
                        });
                log.debug("Successfully processed region: {}", region);
                return region;
            } catch (Exception e) {
                log.error("Failed to get or create region: {}", name, e);
                throw e;
            }
        });
    }

    @Override
    @Cacheable(cacheNames = "allRegions") // точечное переопределение кэша
    public CompletableFuture<List<Region>> getRegions() {
        return CompletableFuture.supplyAsync(() -> {
            log.debug("Fetching all regions");
            List<Region> regions = regionRepository.findAll();
            log.debug("Fetched {} regions", regions.size());
            return regions;
        });
    }

    @Override
    public List<Region> getPageableRegions(String region) {
        return regionRepository.findAll((root, query, cb) ->
                cb.like(cb.lower(root.get("name")), "%" + region.toLowerCase() + "%")
        );
    }
}
