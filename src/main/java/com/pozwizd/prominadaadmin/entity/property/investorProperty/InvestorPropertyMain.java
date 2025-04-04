package com.pozwizd.prominadaadmin.entity.property.investorProperty;

import com.pozwizd.prominadaadmin.entity.SourceInformation;
import com.pozwizd.prominadaadmin.entity.property.HousingState;
import com.pozwizd.prominadaadmin.entity.property.enums.*;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class InvestorPropertyMain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "investor_property_id")
    private InvestorProperty investorProperty;

    private PublicationStatus publicationStatus;

    private String objectCode;

    private Integer branchCode;

    private Integer employeeCode;

    @OneToOne
    @JoinColumn(name = "housing_state_id")
    private HousingState housingState;

    private String landmark;

    private int floor;

    private int floors;

    private int rooms;

    private Double price;

    // Срок сдачи
    private DeliveryDate deliveryDate;

    // Дата ввода в эксплуатацию
    private LocalDate commissioningDate;

    private double totalArea;

    private double livingArea;

    private double kitchenArea;

    private String roomSizes;

    private double ceilingHeight;

    private WallMaterial wallMaterial;

    private ConditionFlat conditionFlat;

    private Kitchen kitchen;

    private Integer bathroom;

    private BalconyType balcony;

    private String viewFromWindows;

    private Cooker cooker;

    private Heating heating;

    private LocalDate lastCommunication;

    private Boolean isVnp;

    private LocalDate vnpDate;

    private SourceInformation sourceInformation;

    private Boolean hasTrade;

    private Boolean hasExclusive;

    private Boolean urgent;

    private Boolean isFree;

    private Boolean isOpenObject;

    private Boolean fromMediator;

    @Lob
    private String description;

    private String AdvertisingHeadline;

    @Lob
    private String AdvertisingText;

    private Boolean isAdvertising;
}
