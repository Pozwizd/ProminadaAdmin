package com.pozwizd.prominadaadmin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.request.CommercialPropertiesRequest;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.response.CommercialPropertiesResponse;
import com.pozwizd.prominadaadmin.service.CommercialPropertiesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommercialPropertiesController.class)
class CommercialPropertiesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommercialPropertiesService commercialPropertiesService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCommercialProperties_ValidRequest_ReturnsCreated() throws Exception {
        CommercialPropertiesRequest request = createValidRequest();
        CommercialPropertiesResponse response = createValidResponse();
        
        when(commercialPropertiesService.create(any(CommercialPropertiesRequest.class)))
            .thenReturn(response);

        mockMvc.perform(post("/commercialProperty/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.realtorId").value(5L))
                .andExpect(jsonPath("$.commercialPropertiesMain.publicationStatus").value("PUBLICATED"));
    }

    private CommercialPropertiesRequest createValidRequest() {
        CommercialPropertiesRequest request = new CommercialPropertiesRequest();
        request.setId(1L);
        request.setRealtorId(5L);
        request.setRegionId(1L);
        request.setCityId(1L);
        request.setDistrictId(1L);
        request.setStreetId(1L);
        request.setHouseId(15L);
        request.setTopozoneId(1L);
        request.setHouseSection("A");
        request.setFlatNumber("12");
        request.setOwnerName("Иванов Иван Иванович");
        request.setPhoneNumber("+7-999-123-45-67");
        request.setAcquisitionDate(LocalDate.of(2023, 1, 15));
        request.setComment("Важная информация об объекте");
        request.setCadastralNumber("77:01:0001001:1");
        request.setLangPurpose("Для торговли и обслуживания");
        request.setAdminComment("Проверено администратором");
        request.setDateOfCreating(LocalDate.of(2024, 1, 1));
        return request;
    }

    private CommercialPropertiesResponse createValidResponse() {
        CommercialPropertiesResponse response = new CommercialPropertiesResponse();
        response.setId(1L);
        response.setRealtorId(5L);
        return response;
    }
}