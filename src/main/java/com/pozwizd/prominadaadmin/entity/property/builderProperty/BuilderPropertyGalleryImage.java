package com.pozwizd.prominadaadmin.entity.property.builderProperty;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class BuilderPropertyGalleryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String pathImage;

    @ManyToOne
    @JoinColumn(name = "builder_property_id")
    private BuilderProperty builderProperty;
}