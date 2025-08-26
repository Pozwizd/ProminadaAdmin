package com.pozwizd.prominadaadmin.mapper.property.commercial;

import com.pozwizd.prominadaadmin.entity.location.*;
import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialProperties;
import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialPropertiesFile;
import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialPropertiesGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialPropertiesMain;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.request.CommercialPropertiesFileRequest;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.request.CommercialPropertiesGalleryImageRequest;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.request.CommercialPropertiesMainRequest;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.request.CommercialPropertiesRequest;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.response.CommercialPropertiesMainResponse;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.response.CommercialPropertiesResponse;
import com.pozwizd.prominadaadmin.service.forMapper.EntityLookupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommercialPropertiesMapperRefactorTest {

    @Mock
    private EntityLookupService entityLookupService;
    @Mock
    private CommercialPropertiesMainMapper mainMapper;
    @Mock
    private CommercialPropertiesFileMapper fileMapper;
    @Mock
    private CommercialPropertiesGalleryImageMapper imageMapper;

    private CommercialPropertiesMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(CommercialPropertiesMapper.class);
        ReflectionTestUtils.setField(mapper, "entityLookupService", entityLookupService);
        ReflectionTestUtils.setField(mapper, "commercialPropertiesMainMapper", mainMapper);
        ReflectionTestUtils.setField(mapper, "commercialPropertiesFileMapper", fileMapper);
        ReflectionTestUtils.setField(mapper, "commercialPropertiesGalleryImageMapper", imageMapper);
    }

    @Test
    void toEntity_mapsReferencesAndChildren() {
        CommercialPropertiesMainRequest mainReq = new CommercialPropertiesMainRequest();
        mainReq.setObjectCode("C-001");

        CommercialPropertiesFileRequest fileReq = new CommercialPropertiesFileRequest();
        fileReq.setName("doc");

        CommercialPropertiesGalleryImageRequest imgReq = new CommercialPropertiesGalleryImageRequest();
        imgReq.setName("img");

        CommercialPropertiesRequest req = new CommercialPropertiesRequest();
        req.setRegionId(1L);
        req.setCityId(2L);
        req.setDistrictId(3L);
        req.setStreetId(4L);
        req.setHouseId(5L);
        req.setTopozoneId(6L);
        req.setCommercialPropertiesMain(mainReq);
        req.setCommercialPropertiesFiles(List.of(fileReq));
        req.setCommercialPropertiesGalleryImages(List.of(imgReq));

        Region region = Region.builder().id(1L).build();
        City city = City.builder().id(2L).build();
        District district = District.builder().id(3L).build();
        Street street = Street.builder().id(4L).build();
        House house = House.builder().id(5L).build();
        Topozone topozone = Topozone.builder().id(6L).build();

        CommercialPropertiesMain main = CommercialPropertiesMain.builder().objectCode("C-001").build();
        CommercialPropertiesFile file = CommercialPropertiesFile.builder().name("doc").build();
        CommercialPropertiesGalleryImage img = CommercialPropertiesGalleryImage.builder().name("img").build();

        when(entityLookupService.findRegionById(1L)).thenReturn(region);
        when(entityLookupService.findCityById(2L)).thenReturn(city);
        when(entityLookupService.findDistrictById(3L)).thenReturn(district);
        when(entityLookupService.findStreetById(4L)).thenReturn(street);
        when(entityLookupService.findHouseById(5L)).thenReturn(house);
        when(entityLookupService.findTopozoneById(6L)).thenReturn(topozone);

        when(mainMapper.toEntity(mainReq)).thenReturn(main);
        when(fileMapper.toEntity(fileReq)).thenReturn(file);
        when(imageMapper.toEntity(imgReq)).thenReturn(img);

        CommercialProperties entity = mapper.toEntity(req);

        assertNotNull(entity);
        assertEquals(region, entity.getRegion());
        assertEquals(city, entity.getCity());
        assertEquals(district, entity.getDistrict());
        assertEquals(street, entity.getStreet());
        assertEquals(house, entity.getHouse());
        assertEquals(topozone, entity.getTopozone());
        assertEquals(main, entity.getCommercialPropertiesMain());
        assertEquals(1, entity.getCommercialPropertiesFiles().size());
        assertEquals(1, entity.getCommercialPropertiesGalleryImages().size());

        verify(mainMapper).toEntity(mainReq);
        verify(fileMapper).toEntity(fileReq);
        verify(imageMapper).toEntity(imgReq);
    }

    @Test
    void toResponse_mapsIdsAndMain() {
        CommercialPropertiesMain main = CommercialPropertiesMain.builder()
                .id(10L).objectCode("OC").build();

        Region region = Region.builder().id(1L).build();
        City city = City.builder().id(2L).build();
        District district = District.builder().id(3L).build();
        Street street = Street.builder().id(4L).build();
        House house = House.builder().id(5L).build();
        Topozone topozone = Topozone.builder().id(6L).build();

        CommercialProperties entity = CommercialProperties.builder()
                .id(100L)
                .region(region).city(city).district(district).street(street).house(house).topozone(topozone)
                .commercialPropertiesMain(main)
                .build();

        CommercialPropertiesMainResponse mainResp = new CommercialPropertiesMainResponse();
        mainResp.setId(10L);
        mainResp.setObjectCode("OC");

        when(mainMapper.toResponse(main)).thenReturn(mainResp);

        CommercialPropertiesResponse resp = mapper.toResponse(entity);

        assertNotNull(resp);
        assertEquals(100L, resp.getId());
        assertEquals(1L, resp.getRegionId());
        assertEquals(2L, resp.getCityId());
        assertEquals(3L, resp.getDistrictId());
        assertEquals(4L, resp.getStreetId());
        assertEquals(5L, resp.getHouseId());
        assertEquals(6L, resp.getTopozoneId());
        assertEquals(mainResp, resp.getCommercialPropertiesMain());

        verify(mainMapper).toResponse(main);
    }

    @Test
    void partialUpdate_updatesRefsAndSyncsCollections() {
        CommercialProperties existing = new CommercialProperties();
        existing.setId(1L);
        existing.setCommercialPropertiesFiles(new ArrayList<>());
        existing.setCommercialPropertiesGalleryImages(new ArrayList<>());
        existing.setCommercialPropertiesMain(new CommercialPropertiesMain());

        CommercialPropertiesMainRequest mainReq = new CommercialPropertiesMainRequest();
        mainReq.setObjectCode("UPD");

        CommercialPropertiesFileRequest fileReq = new CommercialPropertiesFileRequest();
        fileReq.setId(null);
        fileReq.setName("add-file");

        CommercialPropertiesGalleryImageRequest imgReq = new CommercialPropertiesGalleryImageRequest();
        imgReq.setId(null);
        imgReq.setName("add-img");

        CommercialPropertiesRequest req = new CommercialPropertiesRequest();
        req.setRegionId(1L);
        req.setCityId(2L);
        req.setDistrictId(3L);
        req.setStreetId(4L);
        req.setHouseId(5L);
        req.setTopozoneId(6L);
        req.setCommercialPropertiesMain(mainReq);
        req.setCommercialPropertiesFiles(List.of(fileReq));
        req.setCommercialPropertiesGalleryImages(List.of(imgReq));

        when(entityLookupService.findRegionById(1L)).thenReturn(Region.builder().id(1L).build());
        when(entityLookupService.findCityById(2L)).thenReturn(City.builder().id(2L).build());
        when(entityLookupService.findDistrictById(3L)).thenReturn(District.builder().id(3L).build());
        when(entityLookupService.findStreetById(4L)).thenReturn(Street.builder().id(4L).build());
        when(entityLookupService.findHouseById(5L)).thenReturn(House.builder().id(5L).build());
        when(entityLookupService.findTopozoneById(6L)).thenReturn(Topozone.builder().id(6L).build());

        // Verify that our sub-mapper methods are used
        doAnswer(inv -> {
            CommercialPropertiesMainRequest r = inv.getArgument(0);
            CommercialPropertiesMain target = inv.getArgument(1);
            target.setObjectCode(r.getObjectCode());
            return null;
        }).when(mainMapper).partialUpdate(any(), any());

        doAnswer(inv -> {
            List<CommercialPropertiesFileRequest> reqs = inv.getArgument(0);
            List<CommercialPropertiesFile> target = inv.getArgument(1);
            target.clear();
            target.add(CommercialPropertiesFile.builder().name(reqs.get(0).getName()).build());
            return null;
        }).when(fileMapper).partialUpdateList(any(), any());

        doAnswer(inv -> {
            List<CommercialPropertiesGalleryImageRequest> reqs = inv.getArgument(0);
            List<CommercialPropertiesGalleryImage> target = inv.getArgument(1);
            target.clear();
            target.add(CommercialPropertiesGalleryImage.builder().name(reqs.get(0).getName()).build());
            return null;
        }).when(imageMapper).partialUpdateList(any(), any());

        mapper.partialUpdate(req, existing);

        assertEquals("UPD", existing.getCommercialPropertiesMain().getObjectCode());
        assertEquals(1, existing.getCommercialPropertiesFiles().size());
        assertEquals("add-file", existing.getCommercialPropertiesFiles().get(0).getName());
        assertEquals(1, existing.getCommercialPropertiesGalleryImages().size());
        assertEquals("add-img", existing.getCommercialPropertiesGalleryImages().get(0).getName());

        verify(mainMapper).partialUpdate(any(), any());
        verify(fileMapper).partialUpdateList(any(), any());
        verify(imageMapper).partialUpdateList(any(), any());
    }
}