package com.pozwizd.prominadaadmin.entity.property.builderProperty;

import com.pozwizd.prominadaadmin.entity.other.City;
import com.pozwizd.prominadaadmin.entity.other.District;
import com.pozwizd.prominadaadmin.entity.other.RegDistrict;
import com.pozwizd.prominadaadmin.entity.other.Topozone;
import com.pozwizd.prominadaadmin.entity.property.BuildingCompany;
import com.pozwizd.prominadaadmin.entity.property.enums.DeliveryDate;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class BuilderProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "reg_district_id")
    private RegDistrict regDistrict;

    @ManyToOne
    @JoinColumn(name = "distinct_id")
    private District distinct;

    @ManyToOne
    @JoinColumn(name = "topozone_id")
    private Topozone topozone;

    private String street;

    private Integer houseNumber;

    private Integer houseSection;

    private Integer totalFloor;

    @ManyToOne
    @JoinColumn(name = "building_company_id")
    private BuildingCompany buildingCompany;

    // Срок сдачи обьекта
    private DeliveryDate deliveryDate;

    private String phoneNumber;

    // Файл шахматного плана
    private String pathToChessPlanFile;

    // Файл с условия рассрочки
    private String pathToMortgageConditionsFile;

    // Файл с ценами
    private String pathToPriceFile;

    @Lob
    private String description;

    private String actionTitle;

    @Lob
    private String actionDescription;

    private Boolean isAction;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "builderProperty")
    private List<BuilderPropertyGalleryImage> builderPropertyGalleryImages;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "builderProperty")
    private List<BuilderPropertyLayouts> builderPropertyLayouts;

    private LocalDate dateOfCreating;
}