package com.pozwizd.prominadaadmin.mapper.property.residentialLand;

import com.pozwizd.prominadaadmin.entity.Realtor;
import com.pozwizd.prominadaadmin.entity.location.*;
import com.pozwizd.prominadaadmin.entity.property.enums.*;
import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLandGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLandMain;
import com.pozwizd.prominadaadmin.models.property.residentialLand.request.ResidentialLandMainRequest;
import com.pozwizd.prominadaadmin.models.property.residentialLand.request.ResidentialLandRequest;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandMainResponse;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandResponse;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.table.ResidentialLandTableResponse;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResidentialLandMapperTest {

    @Mock
    private EntityLookupService entityLookupService;

    @Mock
    private ResidentialLandMainMapper residentialLandMainMapper;

    @Mock
    private ResidentialLandGalleryImageMapper residentialLandGalleryImageMapper;

    private ResidentialLandMapper residentialLandMapper;

    @BeforeEach
    void setUp() {
        residentialLandMapper = Mappers.getMapper(ResidentialLandMapper.class);
        ReflectionTestUtils.setField(residentialLandMapper, "entityLookupService", entityLookupService);
        ReflectionTestUtils.setField(residentialLandMapper, "residentialLandMainMapper", residentialLandMainMapper);
        ReflectionTestUtils.setField(residentialLandMapper, "residentialLandGalleryImageMapper", residentialLandGalleryImageMapper);
    }

    @Test
    void toEntityTableResponse_withPhotos() {
        // Подготовка тестовых данных
        Region region = Region.builder().id(1L).name("Московская область").build();
        City city = City.builder().id(2L).name("Подольск").build();
        District district = District.builder().id(3L).name("Центральный район").build();
        Street street = Street.builder().id(4L).name("улица Кирова").build();
        Topozone topozone = Topozone.builder().id(5L).name("Лесопарковая зона").build();

        ResidentialLandMain residentialLandMain = ResidentialLandMain.builder()
                .id(1L)
                .publicationStatus(PublicationStatus.PUBLICATED)
                .objectCode("RL12345")
                .build();

        ResidentialLandGalleryImage image = ResidentialLandGalleryImage.builder()
                .id(1L)
                .name("Фасад дома")
                .pathImage("uploads/facade.jpg")
                .build();

        ResidentialLand residentialLand = ResidentialLand.builder()
                .id(1L)
                .region(region)
                .city(city)
                .district(district)
                .street(street)
                .topozone(topozone)
                .ownerFullName("Иван Петрович Сидоров")
                .phoneNumber("+7 912 345 67 89")
                .acquisitionDate(LocalDate.of(2020, 6, 14))
                .ownershipDoc(OwnershipDoc.OWNERSHIP_CERTIFICATE)
                .importantComment("Отличный участок")
                .cadastralNumber("77:01:0004010:123")
                .langPurpose("Жилое строительство")
                .adminComment("Админ комментарий")
                .residentialLandMain(residentialLandMain)
                .dateOfCreating(LocalDate.of(2021, 10, 2))
                .residentialLandGalleryImages(new ArrayList<>())
                .build();

        residentialLand.getResidentialLandGalleryImages().add(image);

        ResidentialLandTableResponse response = residentialLandMapper.toTableResponse(residentialLand);

        assertNotNull(response);
        assertEquals("улица Кирова", response.getStreet());
        assertEquals("Московская область", response.getRegDistrictName());
        assertEquals("Центральный район", response.getDistrictName());
        assertEquals("Лесопарковая зона", response.getTopozoneName());
        assertEquals("Иван Петрович Сидоров", response.getOwnerFullName());
        assertEquals("+7 912 345 67 89", response.getPhoneNumber());
        assertEquals(LocalDate.of(2020, 6, 14), response.getAcquisitionDate());
        assertEquals(OwnershipDoc.OWNERSHIP_CERTIFICATE, response.getOwnershipDoc());
        assertEquals("Отличный участок", response.getImportantComment());
        assertEquals("77:01:0004010:123", response.getCadastralNumber());
        assertEquals("Жилое строительство", response.getLangPurpose());
        assertEquals("Админ комментарий", response.getAdminComment());
        assertEquals(LocalDate.of(2021, 10, 2), response.getDateOfCreating());
        assertTrue(response.getHasPhotos());
    }

    @Test
    void toEntityTableResponse_withoutPhotos() {
        ResidentialLand residentialLand = ResidentialLand.builder()
                .id(2L)
                .ownerFullName("Петр Иванович")
                .phoneNumber("+7 999 888 77 66")
                .build();

        ResidentialLandTableResponse response = residentialLandMapper.toTableResponse(residentialLand);

        assertNotNull(response);
        assertEquals("Петр Иванович", response.getOwnerFullName());
        assertEquals("+7 999 888 77 66", response.getPhoneNumber());
        assertFalse(response.getHasPhotos());
    }

    @Test
    void toTableResponses_list() {
        ResidentialLand land1 = ResidentialLand.builder()
                .id(1L)
                .ownerFullName("Владелец 1")
                .build();

        ResidentialLand land2 = ResidentialLand.builder()
                .id(2L)
                .ownerFullName("Владелец 2")
                .build();

        List<ResidentialLand> residentialLands = Arrays.asList(land1, land2);

        List<ResidentialLandTableResponse> responses = residentialLandMapper.toTableResponses(residentialLands);

        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("Владелец 1", responses.get(0).getOwnerFullName());
        assertEquals("Владелец 2", responses.get(1).getOwnerFullName());
    }

    @Test
    void toTableResponses_nullList() {
        List<ResidentialLandTableResponse> responses = residentialLandMapper.toTableResponses((List<ResidentialLand>) null);

        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }

    @Test
    void toTableResponses_page() {
        ResidentialLand land = ResidentialLand.builder()
                .id(1L)
                .ownerFullName("Владелец страницы")
                .build();

        List<ResidentialLand> content = Arrays.asList(land);
        Page<ResidentialLand> page = new PageImpl<>(content);

        Page<ResidentialLandTableResponse> responsePage = residentialLandMapper.toTableResponses(page);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("Владелец страницы", responsePage.getContent().get(0).getOwnerFullName());
    }

    @Test
    void toResponse() {
        Region region = Region.builder().id(10L).name("Регион").build();
        City city = City.builder().id(20L).name("Город").build();
        District district = District.builder().id(30L).name("Район").build();
        Street street = Street.builder().id(40L).name("Улица").build();
        House house = House.builder().id(50L).number("10").build();
        Topozone topozone = Topozone.builder().id(60L).name("Зона").build();
        Realtor realtor = Realtor.builder().id(70L).name("Риелтор").build();

        ResidentialLandMain residentialLandMain = ResidentialLandMain.builder()
                .id(1L)
                .objectCode("TEST123")
                .build();

        ResidentialLandMainResponse mainResponse = ResidentialLandMainResponse.builder()
                .id(1L)
                .objectCode("TEST123")
                .build();

        ResidentialLand residentialLand = ResidentialLand.builder()
                .id(100L)
                .region(region)
                .city(city)
                .district(district)
                .street(street)
                .house(house)
                .topozone(topozone)
                .realtor(realtor)
                .residentialLandMain(residentialLandMain)
                .ownerFullName("Тестовый владелец")
                .build();

        when(residentialLandMainMapper.toResponse(residentialLandMain)).thenReturn(mainResponse);

        ResidentialLandResponse response = residentialLandMapper.toResponse(residentialLand);

        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals(10L, response.getRegionId());
        assertEquals(20L, response.getCityId());
        assertEquals(30L, response.getDistrictId());
        assertEquals(40L, response.getStreetId());
        assertEquals(50L, response.getHouseId());
        assertEquals(60L, response.getTopozoneId());
        assertEquals(70L, response.getRealtorId());
        assertEquals(mainResponse, response.getResidentialLandMain());
        assertEquals("Тестовый владелец", response.getOwnerFullName());

        verify(residentialLandMainMapper).toResponse(residentialLandMain);
    }

    @Test
    void updateFromRequest() {
        ResidentialLand existingLand = new ResidentialLand();
        existingLand.setId(1L);
        existingLand.setOwnerFullName("Старый владелец");
        existingLand.setPhoneNumber("Старый телефон");

        ResidentialLandMain existingMain = new ResidentialLandMain();
        existingMain.setId(1L);
        existingMain.setObjectCode("OLD123");
        existingLand.setResidentialLandMain(existingMain);

        ResidentialLandMainRequest mainRequest = ResidentialLandMainRequest.builder()
                .objectCode("NEW456")
                .build();

        ResidentialLandRequest request = ResidentialLandRequest.builder()
                .regionId(1L)
                .cityId(2L)
                .districtId(3L)
                .streetId(4L)
                .houseId(5L)
                .topozoneId(6L)
                .ownerFullName("Новый владелец")
                .phoneNumber("Новый телефон")
                .residentialLandMain(mainRequest)
                .build();

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

        residentialLandMapper.updateFromRequest(request, existingLand);

        assertEquals(1L, existingLand.getId());
        assertEquals(region, existingLand.getRegion());
        assertEquals(city, existingLand.getCity());
        assertEquals(district, existingLand.getDistrict());
        assertEquals(street, existingLand.getStreet());
        assertEquals(house, existingLand.getHouse());
        assertEquals(topozone, existingLand.getTopozone());
        assertEquals("Новый владелец", existingLand.getOwnerFullName());
        assertEquals("Новый телефон", existingLand.getPhoneNumber());

        verify(entityLookupService).findRegionById(1L);
        verify(entityLookupService).findCityById(2L);
        verify(entityLookupService).findDistrictById(3L);
        verify(entityLookupService).findStreetById(4L);
        verify(entityLookupService).findHouseById(5L);
        verify(entityLookupService).findTopozoneById(6L);
        verify(residentialLandMainMapper).partialUpdate(mainRequest, existingMain);
    }

    @Test
    void updateFromRequest_withNullValues() {
        ResidentialLand existingLand = new ResidentialLand();
        existingLand.setId(2L);
        existingLand.setOwnerFullName("Сохранить владельца");
        existingLand.setPhoneNumber("Сохранить телефон");

        ResidentialLandRequest request = ResidentialLandRequest.builder()
                .regionId(null)
                .cityId(null)
                .ownerFullName(null)
                .phoneNumber("Новый телефон")
                .build();

        residentialLandMapper.updateFromRequest(request, existingLand);

        assertEquals(2L, existingLand.getId());
        assertEquals("Сохранить владельца", existingLand.getOwnerFullName()); // null игнорируется
        assertEquals("Новый телефон", existingLand.getPhoneNumber());
        assertNull(existingLand.getRegion());
        assertNull(existingLand.getCity());

        verify(entityLookupService, never()).findRegionById(any());
        verify(entityLookupService, never()).findCityById(any());
    }

    @Test
    void toEntity() {
        ResidentialLandMainRequest mainRequest = ResidentialLandMainRequest.builder()
                .objectCode("CREATE123")
                .build();

        ResidentialLandMain createdMain = ResidentialLandMain.builder()
                .objectCode("CREATE123")
                .build();

        ResidentialLandRequest request = ResidentialLandRequest.builder()
                .regionId(7L)
                .cityId(8L)
                .districtId(9L)
                .streetId(10L)
                .houseId(11L)
                .topozoneId(12L)
                .ownerFullName("Создаваемый владелец")
                .phoneNumber("Создаваемый телефон")
                .acquisitionDate(LocalDate.of(2023, 1, 15))
                .residentialLandMain(mainRequest)
                .build();

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
        when(residentialLandMainMapper.toEntity(mainRequest)).thenReturn(createdMain);

        ResidentialLand result = residentialLandMapper.toEntity(request);

        assertNotNull(result);
        assertEquals(region, result.getRegion());
        assertEquals(city, result.getCity());
        assertEquals(district, result.getDistrict());
        assertEquals(street, result.getStreet());
        assertEquals(house, result.getHouse());
        assertEquals(topozone, result.getTopozone());
        assertEquals("Создаваемый владелец", result.getOwnerFullName());
        assertEquals("Создаваемый телефон", result.getPhoneNumber());
        assertEquals(LocalDate.of(2023, 1, 15), result.getAcquisitionDate());
        assertEquals(createdMain, result.getResidentialLandMain());

        verify(entityLookupService).findRegionById(7L);
        verify(entityLookupService).findCityById(8L);
        verify(entityLookupService).findDistrictById(9L);
        verify(entityLookupService).findStreetById(10L);
        verify(entityLookupService).findHouseById(11L);
        verify(entityLookupService).findTopozoneById(12L);
        verify(residentialLandMainMapper).toEntity(mainRequest);
    }

    @Test
    void toEntity_withNullReferences() {
        when(entityLookupService.findRegionById(null)).thenReturn(null);
        when(entityLookupService.findCityById(null)).thenReturn(null);
        when(entityLookupService.findDistrictById(null)).thenReturn(null);
        when(entityLookupService.findStreetById(null)).thenReturn(null);
        when(entityLookupService.findHouseById(null)).thenReturn(null);
        when(entityLookupService.findTopozoneById(null)).thenReturn(null);

        ResidentialLandRequest request = ResidentialLandRequest.builder()
                .regionId(null)
                .cityId(null)
                .ownerFullName("Владелец без региона")
                .phoneNumber("Телефон")
                .build();

        ResidentialLand result = residentialLandMapper.toEntity(request);

        assertNotNull(result);
        assertNull(result.getRegion());
        assertNull(result.getCity());
        assertEquals("Владелец без региона", result.getOwnerFullName());
        assertEquals("Телефон", result.getPhoneNumber());

        verify(entityLookupService, times(1)).findRegionById(null);
        verify(entityLookupService, times(1)).findCityById(null);
    }

}
