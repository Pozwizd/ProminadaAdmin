package com.pozwizd.prominadaadmin.models.property.investor.request;

import com.pozwizd.prominadaadmin.entity.SourceInformation;
import com.pozwizd.prominadaadmin.entity.property.enums.*;
import com.pozwizd.prominadaadmin.validator.branch.NotFoundBranch;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Request for {@link com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorPropertyMain}
 */
@Data
@NotFoundBranch(
        message = "Филиал по branchCode не найден",
        branchCodeField = "branchCode",
        nullable = false
)
public class InvestorPropertyMainRequest implements Serializable {
    Long id;
    Long housingStateId;
    PublicationStatus publicationStatus;
    String objectCode;
    String branchCode;
    String employeeCode;
    String landmark;
    int floor;
    int floors;
    int rooms;
    Double price;
    DeliveryDate deliveryDate;
    LocalDate commissioningDate;
    double totalArea;
    double livingArea;
    double kitchenArea;
    String roomSizes;
    double ceilingHeight;
    WallMaterial wallMaterial;
    ConditionFlat conditionFlat;
    Kitchen kitchen;
    Integer bathroom;
    BalconyType balcony;
    String viewFromWindows;
    Cooker cooker;
    Heating heating;
    LocalDate lastCommunication;
    Boolean isVnp;
    LocalDate vnpDate;
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