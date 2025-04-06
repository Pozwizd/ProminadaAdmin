package com.pozwizd.prominadaadmin.entity.property.builderProperty;

import com.pozwizd.prominadaadmin.entity.other.City;
import com.pozwizd.prominadaadmin.entity.other.Distinct;
import com.pozwizd.prominadaadmin.entity.other.RegDistrict;
import com.pozwizd.prominadaadmin.entity.other.Topozone;
import com.pozwizd.prominadaadmin.entity.property.BuildingCompany;
import com.pozwizd.prominadaadmin.entity.property.enums.DeliveryDate;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class BuilderProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToOne
    @JoinColumn(name = "reg_district_id")
    private RegDistrict regDistrict;

    @OneToOne
    @JoinColumn(name = "distinct_id")
    private Distinct distinct;

    @OneToOne
    @JoinColumn(name = "topozone_id")
    private Topozone topozone;

    private String street;

    private String houseNumber;

    private String houseSection;

    private Integer totalFloor;

    @OneToOne
    @JoinColumn(name = "building_company_id")
    private BuildingCompany buildingCompany;

    // Срок сдачи обьекта
    private DeliveryDate deliveryDate;

    private String phoneNumber;

    // Файл шахматного плана
    private String floorPlanFile;

    // Файл с условия рассрочки
    private String mortgageConditions;

    // Файл с ценами
    private String priceFile;

    @Lob
    private String description;

    private String ActionTitle;

    @Lob
    private String ActionDescription;

    private Boolean isAction;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "builderProperty")
    private List<BuilderPropertyGalleryImage> builderPropertyGalleryImages;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "builderProperty")
    private List<BuilderPropertyLayouts> builderPropertyLayouts;

}
