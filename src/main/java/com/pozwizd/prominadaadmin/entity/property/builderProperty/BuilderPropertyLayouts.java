package com.pozwizd.prominadaadmin.entity.property.builderProperty;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class BuilderPropertyLayouts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private double priceByM2;

    private Integer rooms;

    private Double totalArea;

    private Double livingArea;

    private Double kitchenArea;

    private Boolean visibleForSite;

    private String nameFile1;

    private String nameFile2;

    private String nameFile3;

    private String pathImage1;

    private String pathImage2;

    private String pathImage3;

    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "builder_property_id")
    private BuilderProperty builderProperty;
}