package com.pozwizd.prominadaadmin.entity.property.secondaryProperty;

import com.pozwizd.prominadaadmin.entity.Branch;
import com.pozwizd.prominadaadmin.entity.Realtor;
import com.pozwizd.prominadaadmin.entity.SourceInformation;
import com.pozwizd.prominadaadmin.entity.property.HousingState;
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
@Table(name = "secondary_property_main")
public class SecondaryPropertyMain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secondary_property_id")
    @ToString.Exclude
    private SecondaryProperty secondaryProperty;

    @ManyToOne
    @JoinColumn(name = "realtor_id")
    private Realtor realtor;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "housing_state_id")
    private HousingState housingState;

    private PublicationStatus publicationStatus;

    private String objectCode;

    private String branchCode;

    private String employeeCode;

    private String personalName;

    private String landmark;

    private int floor;

    private int floors;

    private int rooms;

    private Double price;

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

    private Boolean withFurniture;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String description;

    private String AdvertisingHeadline;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String AdvertisingText;

    private Boolean isAdvertising;

}
