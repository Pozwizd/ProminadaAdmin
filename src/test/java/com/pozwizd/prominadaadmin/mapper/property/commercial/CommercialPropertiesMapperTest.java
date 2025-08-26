package com.pozwizd.prominadaadmin.mapper.property.commercial;

import com.pozwizd.prominadaadmin.entity.Realtor;
import com.pozwizd.prominadaadmin.entity.location.*;
import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialProperties;
import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialPropertiesMain;
import com.pozwizd.prominadaadmin.entity.property.enums.OwnershipDoc;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommercialPropertiesMapperTest {

    @Mock
    private EntityLookupService entityLookupService;

    private CommercialPropertiesMapper commercialPropertiesMapper;

    @BeforeEach
    void setUp() {
        commercialPropertiesMapper = Mappers.getMapper(CommercialPropertiesMapper.class);
        ReflectionTestUtils.setField(commercialPropertiesMapper, "entityLookupService", entityLookupService);
    }

    @Test
    void toResponse_withIds() {
        Region region = Region.builder().id(10L).name("Регион").build();
        City city = City.builder().id(20L).name("Город").build();
        District district = District.builder().id(30L).name("Район").build();
        Street street = Street.builder().id(40L).name("Улица").build();
        House house = House.builder().id(50L).number("10").build();
        Topozone topozone = Topozone.builder().id(60L).name("Зона").build();
        Realtor realtor = Realtor.builder().id(70L).name("Риелтор").build();

        CommercialPropertiesMain main = CommercialPropertiesMain.builder()
                .id(1L)
                .objectCode("CP12345")
                .build();

        CommercialProperties entity = CommercialProperties.builder()
                .id(100L)
                .region(region)
                .city(city)
                .district(district)
                .street(street)
                .house(house)
                .topozone(topozone)
                .realtor(realtor)
                .ownerName("Владелец")
                .phoneNumber("+7 000 000 00 00")
                .acquisitionDate(LocalDate.of(2020, 6, 14))
                .ownershipDoc(OwnershipDoc.OWNERSHIP_CERTIFICATE)
                .comment("Комментарий")
                .cadastralNumber("77:01:0004010:123")
                .langPurpose("Назначение")
                .adminComment("Админ")
                .commercialPropertiesMain(main)
                .dateOfCreating(LocalDate.of(2021, 10, 2))
                .build();

        CommercialPropertiesMainResponse mainResponse = new CommercialPropertiesMainResponse();
        mainResponse.setId(1L);
        mainResponse.setObjectCode("CP12345");

        CommercialPropertiesResponse response = commercialPropertiesMapper.toResponse(entity);

        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals(10L, response.getRegionId());
        assertEquals(20L, response.getCityId());
        assertEquals(30L, response.getDistrictId());
        assertEquals(40L, response.getStreetId());
        assertEquals(50L, response.getHouseId());
        assertEquals(60L, response.getTopozoneId());

    }

    @Test
    void partialUpdate_updatesEntityFromRequest() {
        CommercialProperties existing = new CommercialProperties();
        existing.setId(1L);
        existing.setOwnerName("Старый владелец");
        existing.setPhoneNumber("Старый телефон");

        CommercialPropertiesMain existingMain = CommercialPropertiesMain.builder()
                .id(1L)
                .objectCode("OLD123")
                .build();
        existing.setCommercialPropertiesMain(existingMain);

        CommercialPropertiesMainRequest mainRequest = new CommercialPropertiesMainRequest();
        mainRequest.setObjectCode("NEW456");

        CommercialPropertiesRequest request = new CommercialPropertiesRequest();
        request.setRegionId(1L);
        request.setCityId(2L);
        request.setDistrictId(3L);
        request.setStreetId(4L);
        request.setHouseId(5L);
        request.setTopozoneId(6L);
        request.setOwnerName("Новый владелец");
        request.setPhoneNumber("Новый телефон");
        request.setCommercialPropertiesMain(mainRequest);

        Region region = Region.builder().id(1L).build();
        City city = City.builder().id(2L).build();
        District district = District.builder().id(3L).build();
        Street street = Street.builder().id(4L).build();
        House house = House.builder().id(5L).build();
        Topozone topozone = Topozone.builder().id(6L).build();

        when(entityLookupService.findRegionById(1L)).thenReturn(region);
        when(entityLookupService.findCityById(2L)).thenReturn(city);
        when(entityLookupService.findDistrictById(3L)).thenReturn(district);
        when(entityLookupService.findStreetById(4L)).thenReturn(street);
        when(entityLookupService.findHouseById(5L)).thenReturn(house);
        when(entityLookupService.findTopozoneById(6L)).thenReturn(topozone);

        commercialPropertiesMapper.partialUpdate(request, existing);

        assertEquals(1L, existing.getId());
        assertEquals(region, existing.getRegion());
        assertEquals(city, existing.getCity());
        assertEquals(district, existing.getDistrict());
        assertEquals(street, existing.getStreet());
        assertEquals(house, existing.getHouse());
        assertEquals(topozone, existing.getTopozone());
        assertEquals("Новый владелец", existing.getOwnerName());
        assertEquals("Новый телефон", existing.getPhoneNumber());

        verify(entityLookupService).findRegionById(1L);
        verify(entityLookupService).findCityById(2L);
        verify(entityLookupService).findDistrictById(3L);
        verify(entityLookupService).findStreetById(4L);
        verify(entityLookupService).findHouseById(5L);
        verify(entityLookupService).findTopozoneById(6L);

    }

    @Test
    void partialUpdate_withNullValues() {
        CommercialProperties existing = new CommercialProperties();
        existing.setId(2L);
        existing.setOwnerName("Сохранить владельца");
        existing.setPhoneNumber("Сохранить телефон");

        CommercialPropertiesRequest request = new CommercialPropertiesRequest();
        request.setRegionId(null);
        request.setCityId(null);
        request.setOwnerName(null);
        request.setPhoneNumber("Новый телефон");

        commercialPropertiesMapper.partialUpdate(request, existing);

        assertEquals(2L, existing.getId());
        assertEquals("Сохранить владельца", existing.getOwnerName());
        assertEquals("Новый телефон", existing.getPhoneNumber());
        assertNull(existing.getRegion());
        assertNull(existing.getCity());

        verify(entityLookupService, never()).findRegionById(any());
        verify(entityLookupService, never()).findCityById(any());
    }

    @Test
    void toEntity_createsEntityFromRequest() {
        CommercialPropertiesMainRequest mainRequest = new CommercialPropertiesMainRequest();
        mainRequest.setObjectCode("CREATE123");

        CommercialPropertiesMain createdMain = CommercialPropertiesMain.builder()
                .objectCode("CREATE123")
                .build();

        CommercialPropertiesRequest request = new CommercialPropertiesRequest();
        request.setRegionId(7L);
        request.setCityId(8L);
        request.setDistrictId(9L);
        request.setStreetId(10L);
        request.setHouseId(11L);
        request.setTopozoneId(12L);
        request.setOwnerName("Создаваемый владелец");
        request.setPhoneNumber("Создаваемый телефон");
        request.setAcquisitionDate(LocalDate.of(2023, 1, 15));
        request.setCommercialPropertiesMain(mainRequest);

        Region region = Region.builder().id(7L).build();
        City city = City.builder().id(8L).build();
        District district = District.builder().id(9L).build();
        Street street = Street.builder().id(10L).build();
        House house = House.builder().id(11L).build();
        Topozone topozone = Topozone.builder().id(12L).build();

        when(entityLookupService.findRegionById(7L)).thenReturn(region);
        when(entityLookupService.findCityById(8L)).thenReturn(city);
        when(entityLookupService.findDistrictById(9L)).thenReturn(district);
        when(entityLookupService.findStreetById(10L)).thenReturn(street);
        when(entityLookupService.findHouseById(11L)).thenReturn(house);
        when(entityLookupService.findTopozoneById(12L)).thenReturn(topozone);

        CommercialProperties result = commercialPropertiesMapper.toEntity(request);

        assertNotNull(result);
        assertEquals(region, result.getRegion());
        assertEquals(city, result.getCity());
        assertEquals(district, result.getDistrict());
        assertEquals(street, result.getStreet());
        assertEquals(house, result.getHouse());
        assertEquals(topozone, result.getTopozone());
        assertEquals("Создаваемый владелец", result.getOwnerName());
        assertEquals("Создаваемый телефон", result.getPhoneNumber());
        assertEquals(LocalDate.of(2023, 1, 15), result.getAcquisitionDate());

        verify(entityLookupService).findRegionById(7L);
        verify(entityLookupService).findCityById(8L);
        verify(entityLookupService).findDistrictById(9L);
        verify(entityLookupService).findStreetById(10L);
        verify(entityLookupService).findHouseById(11L);
        verify(entityLookupService).findTopozoneById(12L);

    }

    @Test
    void toEntity_withNullReferences() {
        when(entityLookupService.findRegionById(null)).thenReturn(null);
        when(entityLookupService.findCityById(null)).thenReturn(null);
        when(entityLookupService.findDistrictById(null)).thenReturn(null);
        when(entityLookupService.findStreetById(null)).thenReturn(null);
        when(entityLookupService.findHouseById(null)).thenReturn(null);
        when(entityLookupService.findTopozoneById(null)).thenReturn(null);

        CommercialPropertiesRequest request = new CommercialPropertiesRequest();
        request.setRegionId(null);
        request.setCityId(null);
        request.setOwnerName("Владелец без региона");
        request.setPhoneNumber("Телефон");

        CommercialProperties result = commercialPropertiesMapper.toEntity(request);

        assertNotNull(result);
        assertNull(result.getRegion());
        assertNull(result.getCity());
        assertEquals("Владелец без региона", result.getOwnerName());
        assertEquals("Телефон", result.getPhoneNumber());

        verify(entityLookupService, times(1)).findRegionById(null);
        verify(entityLookupService, times(1)).findCityById(null);
    }

    @Test
    void toResponsePage_mapsPage() {
        CommercialProperties cp = CommercialProperties.builder()
                .id(1L)
                .ownerName("Владелец 1")
                .build();

        List<CommercialProperties> content = Arrays.asList(cp);
        Page<CommercialProperties> page = new PageImpl<>(content);

        Page<CommercialPropertiesResponse> responsePage = commercialPropertiesMapper.toResponsePage(page);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("Владелец 1", responsePage.getContent().get(0).getOwnerName());
    }
}