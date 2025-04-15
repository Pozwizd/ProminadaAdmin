package com.pozwizd.prominadaadmin.entity.property.investorProperty;

import com.pozwizd.prominadaadmin.entity.Realtor;
import com.pozwizd.prominadaadmin.entity.other.City;
import com.pozwizd.prominadaadmin.entity.other.District;
import com.pozwizd.prominadaadmin.entity.other.RegDistrict;
import com.pozwizd.prominadaadmin.entity.other.Topozone;
import com.pozwizd.prominadaadmin.entity.property.enums.OwnershipDoc;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class InvestorProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String street;

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

    private String houseNumber;

    private String houseSection;

    private String flatNumber;

    private String ownerFullName;

    private String phoneNumber;

    private LocalDate acquisitionDate;

    private OwnershipDoc ownershipDoc;

    @Lob
    private String importantComment;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "investorProperty")
    private List<InvestorPropertyFile> files;

    @Lob
    private String adminComment;

    @OneToOne
    @JoinColumn(name = "investor_property_main_id")
    private InvestorPropertyMain main;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "investorProperty")
    private List<InvestorPropertyGalleryImage> galleryImages;

    private LocalDate dateOfCreating;

    @ManyToOne
    private Realtor realtor;
}
