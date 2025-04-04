package com.pozwizd.prominadaadmin.entity.property.secondaryProperty;

import com.pozwizd.prominadaadmin.entity.SourceInformation;
import com.pozwizd.prominadaadmin.entity.property.HousingState;
import com.pozwizd.prominadaadmin.entity.property.enums.*;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class SecondaryPropertyMain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "secondary_property_id")
    private SecondaryProperty secondaryProperty;

    private PublicationStatus publicationStatus;

    private String objectCode;

    private int branchCode;

    private int employeeCode;

    private String personalName;

    private String landmark;

    @OneToOne
    @JoinColumn(name = "housing_state_id")
    private HousingState housingState;

    private int floor;

    private int floors;

    private int rooms;

    private Double price;

    // Дата ввода в эксплуатацию
    private LocalDate commissioningDate;

    private TypePropertySecondary typeProperty;

    private double totalArea;

    private double livingArea;

    private double kitchenArea;

    private ApartmentLayout apartmentLayout;

    private String roomSizes;

    private double ceilingHeight;

    private ProjectHouse projectHouse;

    private WallMaterial wallMaterial;

    private ConditionFlat conditionFlat;

    private Kitchen kitchen;

    private Integer bathroom;

    private BalconyType balcony;

    private String viewFromWindows;

    private Cooker cooker;

    private Heating heating;

    private Stairs stairs;

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

    private Boolean forOffice;

    private Boolean fromMediator;

    // С обстановкой
    private Boolean withFurniture;

    @Lob
    private String description;

    private String AdvertisingHeadline;

    @Lob
    private String AdvertisingText;

    private Boolean isAdvertising;

}
