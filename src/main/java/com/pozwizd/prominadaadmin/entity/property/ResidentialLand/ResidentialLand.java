package com.pozwizd.prominadaadmin.entity.property.ResidentialLand;

import com.pozwizd.prominadaadmin.entity.other.City;
import com.pozwizd.prominadaadmin.entity.other.Distinct;
import com.pozwizd.prominadaadmin.entity.other.RegDistrict;
import com.pozwizd.prominadaadmin.entity.other.Topozone;
import com.pozwizd.prominadaadmin.entity.property.commercial.CommercialPropertiesFile;
import com.pozwizd.prominadaadmin.entity.property.commercial.CommercialPropertiesGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.commercial.CommercialPropertiesMain;
import com.pozwizd.prominadaadmin.entity.property.enums.OwnershipDoc;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class ResidentialLand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private Integer houseNumber;

    private String street;

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

    private String ownerFullName;

    private String phoneNumber;

    private LocalDate acquisitionDate;

    private OwnershipDoc ownershipDoc;

    @Lob
    private String importantComment;

    private String cadastralNumber;

    private String langPurpose;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "residentialLand")
    private List<ResidentialLandFile> files;

    @Lob
    private String adminComment;

    @OneToOne
    @JoinColumn(name = "residential_land_main_id")
    private ResidentialLandMain ResidentialLandMain;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "residentialLand")
    private List<ResidentialLandGalleryImage> galleryImages;
}
