package com.pozwizd.prominadaadmin.entity.property.residentialLand;

import com.pozwizd.prominadaadmin.entity.SourceInformation;
import com.pozwizd.prominadaadmin.entity.property.enums.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class ResidentialLandMain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residential_land_id")
    @ToString.Exclude
    private ResidentialLand residentialLand;

    private PublicationStatus publicationStatus;

    private String objectCode;

    private String branchName;

    private String personalName;

    private String landmark;

    private Double price;

    private TypeProperty typeProperty;

    private Double landAreaAcres;

    private Double freePlotAreaAcres;

    private Boolean landOwnership;

    private DesignatedUseOfLand designatedUseOfLand;

    private Integer houseCount;

    private Integer floors;

    private Integer rooms;

    private Integer bedrooms;

    private Double ceilingHeight;

    private Double totalArea;

    private Double livingArea;

    private Double kitchenArea;

    private String wallMaterial;

    private ConditionInterior conditionInterior;

    private ConditionBuilding conditionBuilding;

    private Kitchen kitchen;

    private Integer bathroom;

    private Gas gas;

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

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String vnpDate;

    private SourceInformation sourceInformation;

    private Boolean hasTrade;

    private Boolean hasExclusive;

    private Boolean urgent;

    private Boolean isFree;

    private Boolean isOpenObject;

    private Boolean fromMediator;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String description;

    private String AdvertisingHeadline;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String AdvertisingText;

    private Boolean isAdvertising;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ResidentialLandMain that = (ResidentialLandMain) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}