package com.pozwizd.prominadaadmin.entity.property.commercialProperty;


import com.pozwizd.prominadaadmin.entity.SourceInformation;
import com.pozwizd.prominadaadmin.entity.property.HousingState;
import com.pozwizd.prominadaadmin.entity.property.enums.DesignatedUseOfLand;
import com.pozwizd.prominadaadmin.entity.property.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "commercial_properties_main")
public class CommercialPropertiesMain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commercial_properties_id")
    @ToString.Exclude
    private CommercialProperties commercialProperties;

    private PublicationStatus publicationStatus;

    private String objectCode;

    private Integer branchCode;

    private Integer employeeCode;

    private String personalName;

    private Double price;

    private String landmark;

    @ManyToOne
    @JoinColumn(name = "housing_state_id")
    private HousingState housingState;

    private LocalDate completionDate;

    private LocalDate commissioningDate;

    private Integer floor;

    private Integer totalFloor;

    private Integer roomCount;

    private TypeCommercialBuilding typeCommBuilding;

    private Boolean isVnp;

    private LocalDate vnpDate;

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

    @Lob
    private String description;

    private String advertisingHeadline;

    @Lob
    private String advertisingText;

    private Boolean isAdvertising;


}
