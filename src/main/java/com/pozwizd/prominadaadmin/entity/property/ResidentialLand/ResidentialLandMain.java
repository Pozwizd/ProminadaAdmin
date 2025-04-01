package com.pozwizd.prominadaadmin.entity.property.ResidentialLand;

import com.pozwizd.prominadaadmin.entity.SourceInformation;
import com.pozwizd.prominadaadmin.entity.property.enums.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Entity
public class ResidentialLandMain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "residential_land_id")
    private ResidentialLand residentialLand;

    private PublicationStatus publicationStatus;

    private String objectCode;

    private String branchName;

    private String personalName;

    private String landmark;

    private Double price;

    private TypeProperty typeProperty;

    // Площадь участка
    private Double landAreaAcres;

    private Double FreePlotAreaAcres;

    private Boolean landOwnership;

    private DesignatedUseOfLand designatedUseOfLand;

    private int houseCount;

    private int floors;

    private int rooms;

    private int bedrooms;

    private double ceilingHeight;

    private double totalArea;

    private double livingArea;

    private double kitchenArea;

    private String wallMaterial;

    private ConditionInterior conditionInterior;

    private ConditionBuilding conditionBuilding;

    private Kitchen kitchen;

    private Integer bathroom;

    private Gas gas;
// Водоснабжение
    private WaterSupply waterSupply;

    private Sewage sewage;

    private Heating heating;

    private Stairs stairs;

    private RoofType roofType;

    private FloorType floorType;

    private TypeWindows typeWindows;

    private CarpentryCondition carpentryCondition;

    private EntranceDoor entranceDoor;

    private LocalDate lastCommunication;

    private Boolean isVnp;

    private String vnpDate;

    private SourceInformation sourceInformation;

    private Boolean hasTrade;

    private Boolean hasExclusive;

    private Boolean urgent;

    private Boolean isFree;

    private Boolean isOpenObject;

    private Boolean fromMediator;

    private String description;

    private String AdvertisingHeadline;

    private String AdvertisingText;

    private Boolean isAdvertising;


}