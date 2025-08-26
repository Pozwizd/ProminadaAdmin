package com.pozwizd.prominadaadmin.entity.property.builderProperty;

import com.pozwizd.prominadaadmin.entity.location.*;
import com.pozwizd.prominadaadmin.entity.property.BuildingCompany;
import com.pozwizd.prominadaadmin.entity.property.enums.DeliveryDate;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "builder_property")
public class BuilderProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER,
            mappedBy = "builderProperty")
    @ToString.Exclude
    private List<BuilderPropertyGalleryImage> builderPropertyGalleryImages;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER,
            mappedBy = "builderProperty")
    @ToString.Exclude
    private List<BuilderPropertyLayouts> builderPropertyLayouts;

    @ManyToOne
    @JoinColumn(name = "building_company_id")
    private BuildingCompany buildingCompany;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "street_id")
    private Street street;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne
    @JoinColumn(name = "topozone_id")
    private Topozone topozone;

    private String name;

    private Integer houseNumber;

    private Integer houseSection;

    private Integer totalFloor;

    private DeliveryDate deliveryDate;

    private String phoneNumber;

    private String pathToChessPlanFile;

    private String pathToMortgageConditionsFile;

    private String pathToPriceFile;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String description;

    private String actionTitle;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String actionDescription;

    private Boolean isAction;

    private LocalDate dateOfCreating;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        BuilderProperty that = (BuilderProperty) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}