package com.pozwizd.prominadaadmin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.pozwizd.prominadaadmin.models.RegDistrict;
import com.pozwizd.prominadaadmin.models.RegDistrictResponse;
import com.pozwizd.prominadaadmin.repository.RegDistrictRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {
    @Value("${delengine-key}")
    private String token;


    private final RegDistrictRepository regDistrictRepository;


    private final String url = "https://api.delengine.com/v1.0/regions?token="+ token;

    public List<RegDistrict> getRegions() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode dataNode = rootNode.path("data");
            return objectMapper.readValue(dataNode.toString(),
                    objectMapper
                            .getTypeFactory()
                            .constructCollectionType(List.class,
                            RegDistrict.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
