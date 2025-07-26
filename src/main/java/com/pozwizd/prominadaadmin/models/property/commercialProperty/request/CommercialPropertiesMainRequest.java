package com.pozwizd.prominadaadmin.models.property.commercialProperty.request;

import com.pozwizd.prominadaadmin.entity.SourceInformation;
import com.pozwizd.prominadaadmin.entity.property.commercial.CommercialPropertiesMain;
import com.pozwizd.prominadaadmin.models.property.HousingStateResponse;
import com.pozwizd.prominadaadmin.entity.property.enums.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Response for {@link CommercialPropertiesMain}
 */
@Data
public class CommercialPropertiesMainRequest implements Serializable {
    Long id;
    Long housingStateId;
    PublicationStatus publicationStatus;
    String objectCode;
    Integer branchCode;
    Integer employeeCode;
    String personalName;
    Double price;
    String landmark;
    HousingStateResponse housingState;
    LocalDate completionDate;
    LocalDate commissioningDate;
    Integer floor;
    Integer totalFloor;
    Integer roomCount;
    TypeCommercialBuilding typeCommBuilding;
    Boolean isVnp;
    LocalDate vnpDate;
    SourceInformation sourceInformation;
    Double area;
    Double livingArea;
    String roomSizes;
    String ceilingHeight;
    String siteArea;
    Double livingSiteArea;
    DesignatedUseOfLand designatedUseOfLand;
    Boolean landOwnership;
    ConditionInterior conditionInterior;
    ConditionBuilding conditionBuilding;
    Integer bathroom;
    String viewFromWindows;
    Boolean hasFurnishings;
    Boolean hasCarPark;
    Boolean hasHousingStock;
    Boolean hasFacade;
    Boolean hasRailwayTracks;
    Gas gas;
    WaterSupply waterSupply;
    Sewage sewage;
    Heating heating;
    AirConditioner airConditioner;
    Ventilation ventilation;
    Stairs stairs;
    Electrification electrification;
    FloorType floorType;
    TypeWindows typeWindows;
    CarpentryCondition carpentryCondition;
    EntranceDoor entranceDoor;
    LocalDate lastCommunication;
    Boolean hasTrade;
    Boolean hasExclusive;
    Boolean urgent;
    Boolean isFree;
    Boolean isOpenObject;
    Boolean fromMediator;
    String description;
    String advertisingHeadline;
    String advertisingText;
    Boolean isAdvertising;
}