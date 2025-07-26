package com.pozwizd.prominadaadmin.entity.property.commercial;

import com.pozwizd.prominadaadmin.entity.Realtor;
import com.pozwizd.prominadaadmin.entity.location.*;
import com.pozwizd.prominadaadmin.entity.property.enums.OwnershipDoc;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "commercial_properties")
public class CommercialProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER,
            mappedBy = "commercialProperties")
    @ToString.Exclude
    private CommercialPropertiesMain commercialPropertiesMain;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "commercialProperties")
    @ToString.Exclude
    private List<CommercialPropertiesFile> commercialPropertiesFiles;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "commercialProperties")
    @ToString.Exclude
    private List<CommercialPropertiesGalleryImage> commercialPropertiesGalleryImages;

    @ManyToOne
    private Realtor realtor;

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

    private String houseSection;

    private String flatNumber;

    private String ownerName;

    private String phoneNumber;

    private LocalDate acquisitionDate;

    private OwnershipDoc ownershipDoc;

    @Lob
    private String comment;

    private String cadastralNumber;

    private String langPurpose;

    @Lob
    private String adminComment;

    private LocalDate dateOfCreating;
}
