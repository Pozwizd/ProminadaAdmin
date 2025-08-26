package com.pozwizd.prominadaadmin.mapper.property.commercial;

import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialPropertiesMain;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.request.CommercialPropertiesMainRequest;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.response.CommercialPropertiesMainResponse;
import com.pozwizd.prominadaadmin.service.forMapper.EntityLookupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommercialPropertiesMainMapperTest {

    @Mock
    private EntityLookupService entityLookupService;

    private CommercialPropertiesMainMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(CommercialPropertiesMainMapper.class);
        ReflectionTestUtils.setField(mapper, "entityLookupService", entityLookupService);
    }

    @Test
    void toResponse_mapsHousingStateId() {
        CommercialPropertiesMain main = CommercialPropertiesMain.builder()
                .id(1L)
                .objectCode("C001")
                .build();
        CommercialPropertiesMainResponse resp = mapper.toResponse(main);

        assertNotNull(resp);
        assertEquals(1L, resp.getId());
        assertEquals("C001", resp.getObjectCode());
        assertNull(resp.getHousingStateId());
    }

    @Test
    void toEntity_resolvesHousingState() {
        CommercialPropertiesMainRequest req = new CommercialPropertiesMainRequest();
        req.setHousingStateId(5L);
        req.setObjectCode("NEW");

        when(entityLookupService.findHousingStateById(5L)).thenReturn(null);

        CommercialPropertiesMain entity = mapper.toEntity(req);

        assertNotNull(entity);
        assertEquals("NEW", entity.getObjectCode());
        assertNull(entity.getCommercialProperties());
        verify(entityLookupService).findHousingStateById(5L);
    }

    @Test
    void partialUpdate_updatesScalarAndResolvesState() {
        CommercialPropertiesMain entity = new CommercialPropertiesMain();
        entity.setId(10L);
        entity.setObjectCode("OLD");

        CommercialPropertiesMainRequest req = new CommercialPropertiesMainRequest();
        req.setObjectCode("UPD");
        req.setHousingStateId(7L);

        when(entityLookupService.findHousingStateById(7L)).thenReturn(null);

        mapper.partialUpdate(req, entity);

        assertEquals(10L, entity.getId());
        assertEquals("UPD", entity.getObjectCode());
        verify(entityLookupService).findHousingStateById(7L);
    }
}