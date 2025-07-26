package com.pozwizd.prominadaadmin.models.property.residentialLand.response;

import com.pozwizd.prominadaadmin.entity.SourceInformation;
import com.pozwizd.prominadaadmin.entity.property.enums.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
@Data
public class ResidentialLandMainResponse implements Serializable {
    Long id;
    PublicationStatus publicationStatus;
    String objectCode;
    String branchName;
    String personalName;
    String landmark;
    Double price;
    TypeProperty typeProperty;
    Double landAreaAcres;
    Double FreePlotAreaAcres;
    Boolean landOwnership;
    DesignatedUseOfLand designatedUseOfLand;

    Integer houseCount;
    Integer floors;
    Integer rooms;
    Integer bedrooms;
    Double ceilingHeight;
    Double totalArea;
    Double livingArea;
    Double kitchenArea;

    String wallMaterial;
    ConditionInterior conditionInterior;
    ConditionBuilding conditionBuilding;
    Kitchen kitchen;
    Integer bathroom;
    Gas gas;
    WaterSupply waterSupply;
    Sewage sewage;
    Heating heating;
    Stairs stairs;
    RoofType roofType;
    FloorType floorType;
    TypeWindows typeWindows;
    CarpentryCondition carpentryCondition;
    EntranceDoor entranceDoor;
    LocalDate lastCommunication;
    Boolean isVnp;
    String vnpDate;
    SourceInformation sourceInformation;
    Boolean hasTrade;
    Boolean hasExclusive;
    Boolean urgent;
    Boolean isFree;
    Boolean isOpenObject;
    Boolean fromMediator;
    String description;
    String AdvertisingHeadline;
    String AdvertisingText;
    Boolean isAdvertising;
}
