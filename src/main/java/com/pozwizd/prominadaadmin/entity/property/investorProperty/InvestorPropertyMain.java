package com.pozwizd.prominadaadmin.entity.property.investorProperty;

import com.pozwizd.prominadaadmin.entity.SourceInformation;
import com.pozwizd.prominadaadmin.entity.property.HousingState;
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
@Table(name = "investor_property_main")
public class InvestorPropertyMain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investor_property_id")
    @ToString.Exclude
    private InvestorProperty investorProperty;

    @ManyToOne
    @JoinColumn(name = "housing_state_id")
    private HousingState housingState;

    private PublicationStatus publicationStatus;

    private String objectCode;

    private Integer branchCode;

    private Integer employeeCode;

    private String landmark;

    private int floor;

    private int floors;

    private int rooms;

    private Double price;

    private DeliveryDate deliveryDate;

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
        InvestorPropertyMain that = (InvestorPropertyMain) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
