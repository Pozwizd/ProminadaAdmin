package com.pozwizd.prominadaadmin.mapper.property.residentialLand;

import com.pozwizd.prominadaadmin.entity.SourceInformation;
import com.pozwizd.prominadaadmin.entity.property.enums.*;
import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLandMain;
import com.pozwizd.prominadaadmin.models.property.residentialLand.request.ResidentialLandMainRequest;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandMainResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ResidentialLandMainMapperTest {

    private ResidentialLandMainMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(ResidentialLandMainMapper.class);
    }

    @Test
    void toEntity_happyPath() {
        ResidentialLandMainRequest request = new ResidentialLandMainRequest();
        request.setId(1L);
        request.setPublicationStatus(PublicationStatus.PUBLICATED);
        request.setObjectCode("RL12345");
        request.setBranchName("Главное отделение");
        request.setPersonalName("Участок Иванова");
        request.setLandmark("Улица Лесная");
        request.setPrice(3457250.61);
        request.setTypeProperty(TypeProperty.LAND);
        request.setLandAreaAcres(1.17);
        request.setFreePlotAreaAcres(0.93);
        request.setLandOwnership(true);
        request.setDesignatedUseOfLand(DesignatedUseOfLand.RESIDENTIAL);
        request.setHouseCount(1);
        request.setFloors(2);
        request.setRooms(5);
        request.setBedrooms(3);
        request.setCeilingHeight(2.75);
        request.setTotalArea(150.0);
        request.setLivingArea(120.0);
        request.setKitchenArea(30.0);
        request.setWallMaterial("Кирпич");
        request.setConditionInterior(ConditionInterior.FromBuilders);
        request.setConditionBuilding(ConditionBuilding.RESIDENTIAL);
        request.setKitchen(Kitchen.STANDARD);
        request.setBathroom(2);
        request.setGas(Gas.LIQUID_GAS);
        request.setWaterSupply(WaterSupply.AUTONOMOUS);
        request.setSewage(Sewage.AUTONOMOUS);
        request.setHeating(Heating.AUTONOMOUS);
        request.setStairs(Stairs.Concrete);
        request.setRoofType(RoofType.SHINGLE);
        request.setFloorType(FloorType.PARQUET);
        request.setTypeWindows(TypeWindows.WITHOUT_WINDOW);
        request.setCarpentryCondition(CarpentryCondition.CUSTOM);
        request.setEntranceDoor(EntranceDoor.ARMORED);
        request.setLastCommunication(LocalDate.of(2025, 6, 23));
        request.setIsVnp(false);
        request.setVnpDate("Example VNP Date");
        request.setSourceInformation(SourceInformation.ADVERTISING);
        request.setHasTrade(true);
        request.setHasExclusive(false);
        request.setUrgent(false);
        request.setIsFree(false);
        request.setIsOpenObject(true);
        request.setFromMediator(false);
        request.setDescription("Прекрасный жилой участок с домом.");
        request.setAdvertisingHeadline("Уникальное предложение");
        request.setAdvertisingText("Продается отличный участок с коммуникациями.");
        request.setIsAdvertising(true);

        ResidentialLandMain entity = mapper.toEntity(request);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals(PublicationStatus.PUBLICATED, entity.getPublicationStatus());
        assertEquals("RL12345", entity.getObjectCode());
        assertEquals("Главное отделение", entity.getBranchName());
        assertEquals("Участок Иванова", entity.getPersonalName());
        assertEquals("Улица Лесная", entity.getLandmark());
        assertEquals(3457250.61, entity.getPrice());
        assertEquals(TypeProperty.LAND, entity.getTypeProperty());
        assertEquals(1.17, entity.getLandAreaAcres());
        assertEquals(0.93, entity.getFreePlotAreaAcres());
        assertTrue(entity.getLandOwnership());
        assertEquals(DesignatedUseOfLand.RESIDENTIAL, entity.getDesignatedUseOfLand());
        assertEquals(1, entity.getHouseCount());
        assertEquals(2, entity.getFloors());
        assertEquals(5, entity.getRooms());
        assertEquals(3, entity.getBedrooms());
        assertEquals(2.75, entity.getCeilingHeight());
        assertEquals(150.0, entity.getTotalArea());
        assertEquals(120.0, entity.getLivingArea());
        assertEquals(30.0, entity.getKitchenArea());
        assertEquals("Кирпич", entity.getWallMaterial());
        assertEquals(ConditionInterior.FromBuilders, entity.getConditionInterior());
        assertEquals(ConditionBuilding.RESIDENTIAL, entity.getConditionBuilding());
        assertEquals(Kitchen.STANDARD, entity.getKitchen());
        assertEquals(2, entity.getBathroom());
        assertEquals(Gas.LIQUID_GAS, entity.getGas());
        assertEquals(WaterSupply.AUTONOMOUS, entity.getWaterSupply());
        assertEquals(Sewage.AUTONOMOUS, entity.getSewage());
        assertEquals(Heating.AUTONOMOUS, entity.getHeating());
        assertEquals(Stairs.Concrete, entity.getStairs());
        assertEquals(RoofType.SHINGLE, entity.getRoofType());
        assertEquals(FloorType.PARQUET, entity.getFloorType());
        assertEquals(TypeWindows.WITHOUT_WINDOW, entity.getTypeWindows());
        assertEquals(CarpentryCondition.CUSTOM, entity.getCarpentryCondition());
        assertEquals(EntranceDoor.ARMORED, entity.getEntranceDoor());
        assertEquals(LocalDate.of(2025, 6, 23), entity.getLastCommunication());
        assertFalse(entity.getIsVnp());
        assertEquals("Example VNP Date", entity.getVnpDate());
        assertEquals(SourceInformation.ADVERTISING, entity.getSourceInformation());
        assertTrue(entity.getHasTrade());
        assertFalse(entity.getHasExclusive());
        assertFalse(entity.getUrgent());
        assertFalse(entity.getIsFree());
        assertTrue(entity.getIsOpenObject());
        assertFalse(entity.getFromMediator());
        assertEquals("Прекрасный жилой участок с домом.", entity.getDescription());
        assertEquals("Уникальное предложение", entity.getAdvertisingHeadline());
        assertEquals("Продается отличный участок с коммуникациями.", entity.getAdvertisingText());
        assertTrue(entity.getIsAdvertising());
        assertNull(entity.getResidentialLand());
    }

    @Test
    void toEntity_withNullFields() {
        ResidentialLandMainRequest request = new ResidentialLandMainRequest();
        request.setId(2L);
        request.setObjectCode("RL67890");

        ResidentialLandMain entity = mapper.toEntity(request);

        assertNotNull(entity);
        assertEquals(2L, entity.getId());
        assertEquals("RL67890", entity.getObjectCode());
        assertNull(entity.getPublicationStatus());
        assertNull(entity.getBranchName());
        assertNull(entity.getPersonalName());
        assertNull(entity.getResidentialLand());
    }

    @Test
    void toResponse() {
        ResidentialLandMain entity = ResidentialLandMain.builder()
                .id(3L)
                .publicationStatus(PublicationStatus.PUBLICATED)
                .objectCode("RL12345")
                .branchName("Главное отделение")
                .personalName("Участок Иванова")
                .landmark("Улица Лесная")
                .price(3457250.61)
                .typeProperty(TypeProperty.LAND)
                .landAreaAcres(1.17)
                .freePlotAreaAcres(0.93)
                .landOwnership(true)
                .designatedUseOfLand(DesignatedUseOfLand.RESIDENTIAL)
                .houseCount(1)
                .floors(2)
                .rooms(5)
                .bedrooms(3)
                .ceilingHeight(2.75)
                .totalArea(150.0)
                .livingArea(120.0)
                .kitchenArea(30.0)
                .wallMaterial("Кирпич")
                .conditionInterior(ConditionInterior.FromBuilders)
                .conditionBuilding(ConditionBuilding.RESIDENTIAL)
                .kitchen(Kitchen.STANDARD)
                .bathroom(2)
                .gas(Gas.LIQUID_GAS)
                .waterSupply(WaterSupply.AUTONOMOUS)
                .sewage(Sewage.AUTONOMOUS)
                .heating(Heating.AUTONOMOUS)
                .stairs(Stairs.Concrete)
                .roofType(RoofType.SHINGLE)
                .floorType(FloorType.PARQUET)
                .typeWindows(TypeWindows.WITHOUT_WINDOW)
                .carpentryCondition(CarpentryCondition.CUSTOM)
                .entranceDoor(EntranceDoor.ARMORED)
                .lastCommunication(LocalDate.of(2025, 6, 23))
                .isVnp(false)
                .vnpDate("Example VNP Date")
                .sourceInformation(SourceInformation.ADVERTISING)
                .hasTrade(true)
                .hasExclusive(false)
                .urgent(false)
                .isFree(false)
                .isOpenObject(true)
                .fromMediator(false)
                .description("Прекрасный жилой участок с домом.")
                .advertisingHeadline("Уникальное предложение")
                .advertisingText("Продается отличный участок с коммуникациями.")
                .isAdvertising(true)
                .build();

        ResidentialLandMainResponse response = mapper.toResponse(entity);

        assertNotNull(response);
        assertEquals(3L, response.getId());
        assertEquals(PublicationStatus.PUBLICATED, response.getPublicationStatus());
        assertEquals("RL12345", response.getObjectCode());
        assertEquals("Главное отделение", response.getBranchName());
        assertEquals("Участок Иванова", response.getPersonalName());
        assertEquals("Улица Лесная", response.getLandmark());
        assertEquals(3457250.61, response.getPrice());
        assertEquals(TypeProperty.LAND, response.getTypeProperty());
        assertEquals(1.17, response.getLandAreaAcres());
        assertEquals(0.93, response.getFreePlotAreaAcres());
        assertTrue(response.getLandOwnership());
        assertEquals(DesignatedUseOfLand.RESIDENTIAL, response.getDesignatedUseOfLand());
        assertEquals("Прекрасный жилой участок с домом.", response.getDescription());
        assertEquals("Уникальное предложение", response.getAdvertisingHeadline());
        assertEquals("Продается отличный участок с коммуникациями.", response.getAdvertisingText());
        assertTrue(response.getIsAdvertising());
    }

    @Test
    void partialUpdate_fullUpdate() {
        // Существующая entity
        ResidentialLandMain entity = new ResidentialLandMain();
        entity.setId(4L);
        entity.setObjectCode("OLD123");
        entity.setBranchName("Старое отделение");
        entity.setPersonalName("Старый участок");
        entity.setPrice(1000000.0);
        entity.setLandAreaAcres(0.5);
        entity.setDescription("Старое описание");

        // Request с новыми данными
        ResidentialLandMainRequest request = new ResidentialLandMainRequest();
        request.setObjectCode("NEW456");
        request.setBranchName("Новое отделение");
        request.setPersonalName("Новый участок");
        request.setPrice(2000000.0);
        request.setLandAreaAcres(1.0);
        request.setDescription("Новое описание");
        request.setPublicationStatus(PublicationStatus.PUBLICATED);

        mapper.partialUpdate(request, entity);

        assertEquals(4L, entity.getId());
        assertEquals("NEW456", entity.getObjectCode());
        assertEquals("Новое отделение", entity.getBranchName());
        assertEquals("Новый участок", entity.getPersonalName());
        assertEquals(2000000.0, entity.getPrice());
        assertEquals(1.0, entity.getLandAreaAcres());
        assertEquals("Новое описание", entity.getDescription());
        assertEquals(PublicationStatus.PUBLICATED, entity.getPublicationStatus());
        assertNull(entity.getResidentialLand());
    }

    @Test
    void partialUpdate_withNullValues() {
        ResidentialLandMain entity = new ResidentialLandMain();
        entity.setId(5L);
        entity.setObjectCode("KEEP123");
        entity.setBranchName("Сохранить отделение");
        entity.setPrice(1500000.0);
        entity.setDescription("Сохранить описание");

        ResidentialLandMainRequest request = new ResidentialLandMainRequest();
        request.setObjectCode(null);
        request.setBranchName(null);
        request.setPrice(null);
        request.setDescription(null);
        request.setPersonalName("Новое имя");

        mapper.partialUpdate(request, entity);

        assertEquals(5L, entity.getId());
        assertEquals("KEEP123", entity.getObjectCode());
        assertEquals("Сохранить отделение", entity.getBranchName());
        assertEquals(1500000.0, entity.getPrice());
        assertEquals("Сохранить описание", entity.getDescription());
        assertEquals("Новое имя", entity.getPersonalName());
    }

    @Test
    void partialUpdate_emptyRequest() {
        ResidentialLandMain entity = new ResidentialLandMain();
        entity.setId(6L);
        entity.setObjectCode("ORIGINAL123");
        entity.setBranchName("Оригинальное отделение");
        entity.setPrice(3000000.0);

        ResidentialLandMainRequest request = new ResidentialLandMainRequest();

        mapper.partialUpdate(request, entity);

        assertEquals(6L, entity.getId());
        assertEquals("ORIGINAL123", entity.getObjectCode());
        assertEquals("Оригинальное отделение", entity.getBranchName());
        assertEquals(3000000.0, entity.getPrice());
    }
}
