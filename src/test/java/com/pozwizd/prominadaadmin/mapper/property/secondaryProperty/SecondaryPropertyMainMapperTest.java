package com.pozwizd.prominadaadmin.mapper.property.secondaryProperty;

import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyMain;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.request.SecondaryPropertyMainRequest;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.response.SecondaryPropertyMainResponse;
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
class SecondaryPropertyMainMapperTest {

    @Mock
    private EntityLookupService entityLookupService;

    private SecondaryPropertyMainMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(SecondaryPropertyMainMapper.class);
        ReflectionTestUtils.setField(mapper, "entityLookupService", entityLookupService);
    }

    @Test
    void toResponse_mapsHousingStateId() {
        SecondaryPropertyMain main = SecondaryPropertyMain.builder()
                .id(1L)
                .objectCode("S001")
                .build();

        SecondaryPropertyMainResponse resp = mapper.toResponse(main);

        assertNotNull(resp);
        assertEquals(1L, resp.getId());
        assertEquals("S001", resp.getObjectCode());
        assertNull(resp.getHousingStateId());
    }

    @Test
    void toEntity_resolvesHousingState() {
        SecondaryPropertyMainRequest req = new SecondaryPropertyMainRequest();
        req.setHousingStateId(5L);
        req.setObjectCode("NEW");

        when(entityLookupService.findHousingStateById(5L)).thenReturn(null);

        SecondaryPropertyMain entity = mapper.toEntity(req);

        assertNotNull(entity);
        assertEquals("NEW", entity.getObjectCode());
        assertNull(entity.getSecondaryProperty());
        verify(entityLookupService).findHousingStateById(5L);
    }

    @Test
    void partialUpdate_updatesScalarAndResolvesState() {
        SecondaryPropertyMain entity = new SecondaryPropertyMain();
        entity.setId(10L);
        entity.setObjectCode("OLD");

        SecondaryPropertyMainRequest req = new SecondaryPropertyMainRequest();
        req.setObjectCode("UPD");
        req.setHousingStateId(7L);

        when(entityLookupService.findHousingStateById(7L)).thenReturn(null);

        mapper.partialUpdate(req, entity);

        assertEquals(10L, entity.getId());
        assertEquals("UPD", entity.getObjectCode());
        verify(entityLookupService).findHousingStateById(7L);
    }
}