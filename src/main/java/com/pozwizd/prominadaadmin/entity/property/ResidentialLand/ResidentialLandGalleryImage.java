package com.pozwizd.prominadaadmin.entity.property.ResidentialLand;

import com.pozwizd.prominadaadmin.entity.property.commercial.CommercialProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "residential_land_gallery_image")
public class ResidentialLandGalleryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String pathImage;


    @ManyToOne
    @JoinColumn(name = "residential_land_id")
    private ResidentialLand residentialLand;


}