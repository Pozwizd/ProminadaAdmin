package com.pozwizd.prominadaadmin.models.property.secondaryProperty.request;

import com.pozwizd.prominadaadmin.entity.SourceInformation;
import com.pozwizd.prominadaadmin.entity.property.enums.*;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyMain;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Request for {@link SecondaryPropertyMain}
 */
@Data
public class SecondaryPropertyMainRequest implements Serializable {
    Long id;
    PublicationStatus publicationStatus;
    String objectCode;
    Integer branchCode;
    Integer employeeCode;
    String personalName;
    String landmark;
    int floor;
    int floors;
    int rooms;
    Double price;
    LocalDate commissioningDate;
    TypePropertySecondary typeProperty;
    double totalArea;
    double livingArea;
    double kitchenArea;
    ApartmentLayout apartmentLayout;
    String roomSizes;
    double ceilingHeight;
    ProjectHouse projectHouse;
    WallMaterial wallMaterial;
    ConditionFlat conditionFlat;
    Kitchen kitchen;
    Integer bathroom;
    BalconyType balcony;
    String viewFromWindows;
    Cooker cooker;
    Heating heating;
    Stairs stairs;
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
    Boolean forOffice;
    Boolean fromMediator;
    Boolean withFurniture;
    String description;
    String AdvertisingHeadline;
    String AdvertisingText;
    Boolean isAdvertising;
}