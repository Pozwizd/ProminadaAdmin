package com.pozwizd.prominadaadmin.models.property.commercialProperty.request;

import com.pozwizd.prominadaadmin.entity.SourceInformation;
import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialPropertiesMain;
import com.pozwizd.prominadaadmin.entity.property.enums.*;
import com.pozwizd.prominadaadmin.validator.branch.NotFoundBranch;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Request for {@link CommercialPropertiesMain}
 */
@Data
@NotFoundBranch(
        message = "Филиал по branchCode не найден",
        branchCodeField = "branchCode",
        nullable = false
)
public class CommercialPropertiesMainRequest implements Serializable {

    // Identifiers
    Long id;
    Long housingStateId;

    // Strings
    String objectCode;
    String personalName;
    String landmark;
    String roomSizes;
    String ceilingHeight;
    String siteArea;
    String viewFromWindows;
    String description;
    String advertisingHeadline;
    String advertisingText;

    // Integers (codes and counts)
    String branchCode;
    String employeeCode;
    Integer floor;
    Integer totalFloor;
    Integer roomCount;
    Integer bathroom;

    // Numerics
    Double price;
    Double area;
    Double livingArea;
    Double livingSiteArea;

    // Booleans
    Boolean isVnp;
    Boolean landOwnership;
    Boolean hasFurnishings;
    Boolean hasCarPark;
    Boolean hasHousingStock;
    Boolean hasFacade;
    Boolean hasRailwayTracks;
    Boolean hasTrade;
    Boolean hasExclusive;
    Boolean urgent;
    Boolean isFree;
    Boolean isOpenObject;
    Boolean fromMediator;
    Boolean isAdvertising;

    // Enums
    PublicationStatus publicationStatus;
    TypeCommercialBuilding typeCommercialBuilding;
    DesignatedUseOfLand designatedUseOfLand;
    ConditionInterior conditionInterior;
    ConditionBuilding conditionBuilding;
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

    // Dates
    LocalDate completionDate;
    LocalDate commissioningDate;
    LocalDate vnpDate;
    LocalDate lastCommunication;

    // Complex types
    SourceInformation sourceInformation;

    // Enums
}