package com.pozwizd.prominadaadmin.entity.property.secondaryProperty;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class SecondaryPropertyGalleryImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String pathImage;

    @ManyToOne
    @JoinColumn(name = "secondary_property_id")
    private SecondaryProperty secondaryProperty;
}
