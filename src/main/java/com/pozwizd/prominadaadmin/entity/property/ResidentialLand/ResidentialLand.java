package com.pozwizd.prominadaadmin.entity.property.ResidentialLand;

import com.pozwizd.prominadaadmin.entity.Realtor;
import com.pozwizd.prominadaadmin.entity.location.*;
import com.pozwizd.prominadaadmin.entity.property.enums.OwnershipDoc;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ResidentialLand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private Integer houseNumber;



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


    private String ownerFullName;

    private String phoneNumber;

    private LocalDate acquisitionDate;

    private OwnershipDoc ownershipDoc;

    @Lob
    private String importantComment;

    private String cadastralNumber;

    private String langPurpose;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "residentialLand")
    private List<ResidentialLandFile> residentialLandFiles = new ArrayList<>();

    @Lob
    private String adminComment;

    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "residential_land_main_id")
    private ResidentialLandMain residentialLandMain;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true, mappedBy = "residentialLand")
    private List<ResidentialLandGalleryImage> residentialLandGalleryImages = new ArrayList<>();

    private LocalDate dateOfCreating;

    @ManyToOne
    private Realtor realtor;
}
