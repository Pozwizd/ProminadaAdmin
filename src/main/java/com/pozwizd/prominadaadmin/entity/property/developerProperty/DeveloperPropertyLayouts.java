package com.pozwizd.prominadaadmin.entity.property.developerProperty;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class DeveloperPropertyLayouts {
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

    @Lob
    private String description;

    @OneToMany(mappedBy = "developerPropertyLayouts", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeveloperPropertyLayoutsImage> developerPropertyLayoutsImages;


    @ManyToOne
    @JoinColumn(name = "developer_property_id")
    private DeveloperProperty developerProperty;

}