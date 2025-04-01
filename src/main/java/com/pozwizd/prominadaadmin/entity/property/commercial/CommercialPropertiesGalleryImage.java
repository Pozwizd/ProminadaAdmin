package com.pozwizd.prominadaadmin.entity.property.commercial;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CommercialPropertiesGalleryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String pathImage;

    @ManyToOne
    @JoinColumn(name = "commercial_properties_id")
    private CommercialProperties commercialProperties;

}
