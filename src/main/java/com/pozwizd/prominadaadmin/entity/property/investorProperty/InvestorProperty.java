package com.pozwizd.prominadaadmin.entity.property.investorProperty;

import com.pozwizd.prominadaadmin.entity.other.City;
import com.pozwizd.prominadaadmin.entity.other.Distinct;
import com.pozwizd.prominadaadmin.entity.other.RegDistrict;
import com.pozwizd.prominadaadmin.entity.other.Topozone;
import com.pozwizd.prominadaadmin.entity.property.enums.OwnershipDoc;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyFile;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyMain;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
public class InvestorProperty {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;


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


    private String houseNumber;

    private String houseSection;

    private String flatNumber;


    private String ownerFullName;

    private String phoneNumber;

    private String acquisitionDate;

    private OwnershipDoc ownershipDoc;

    private String importantComment;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "investorProperty")
    private List<InvestorPropertyFile> files;

    private String adminComment;

    @OneToOne
    @JoinColumn(name = "investor_property_main_id")
    private InvestorPropertyMain main;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "investorProperty")
    private List<InvestorPropertyGalleryImage> galleryImages;
}
