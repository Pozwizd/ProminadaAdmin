package com.pozwizd.prominadaadmin.entity.property.commercial;


import com.pozwizd.prominadaadmin.entity.SourceInformation;
import com.pozwizd.prominadaadmin.entity.property.HousingState;
import com.pozwizd.prominadaadmin.entity.property.enums.DesignatedUseOfLand;
import com.pozwizd.prominadaadmin.entity.property.enums.*;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class CommercialPropertiesMain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "commercial_properties_id")
    private CommercialProperties commercialProperties;

    private PublicationStatus publicationStatus;

    private String objectCode;

    // Код филиала
    private String branchName;

    private String personalName;

    private Double price;

    // Ориентир
    private String landmark;

    @OneToOne
    @JoinColumn(name = "housing_state_id")
    private HousingState housingState;

    // Срок сдачи
    private LocalDate completionDate;

    // Дата ввода в эксплуатацию
    private LocalDate commissioningDate;

    private Integer floor;

    private Integer totalFloor;

    private Integer roomCount;

    private TypeCommercialBuilding typeCommBuilding;

    private Boolean isVnp;

    private String vnpDate;

    private SourceInformation sourceInformation;

    private Double area;

    private Double livingArea;

    // Метраж комнат
    private String roomSizes;


    private String ceilingHeight;
    // Площадь участка
    private String siteArea;

    // Свободная площадь
    private Double livingSiteArea;


    private DesignatedUseOfLand designatedUseOfLand;

    private Boolean landOwnership;

    private ConditionInterior conditionInterior;

    private ConditionBuilding conditionBuilding;

    private Integer bathroom;

    private String viewFromWindows;

    private Boolean hasFurnishings;

    private Boolean hasCarPark;

    private Boolean hasHousingStock;

    private Boolean hasFacade;

    private Boolean hasRailwayTracks;

    private Gas gas;

    private WaterSupply waterSupply;

    private Sewage sewage;

    private Heating heating;

    private AirConditioner airConditioner;

    private Ventilation ventilation;

    // Лестница
    private Stairs stairs;

    private Electrification electrification;

    private FloorType floorType;

    private TypeWindows typeWindows;

    private CarpentryCondition carpentryCondition;

    private EntranceDoor entranceDoor;

    private LocalDate lastCommunication;

    private Boolean hasTrade;

    private Boolean hasExclusive;

    // Срочное предложение
    private Boolean urgent;

    private Boolean isFree;

    private Boolean isOpenObject;

    private Boolean fromMediator;

    private String description;


    private String AdvertisingHeadline;

    private String AdvertisingText;

    private Boolean isAdvertising;


}
