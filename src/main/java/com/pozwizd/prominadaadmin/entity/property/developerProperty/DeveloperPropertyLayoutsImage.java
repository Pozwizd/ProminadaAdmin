package com.pozwizd.prominadaadmin.entity.property.developerProperty;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "developer_property_layouts_image")
public class DeveloperPropertyLayoutsImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String pathImage;

    @ManyToOne
    @JoinColumn(name = "developer_property_layouts_id")
    private DeveloperPropertyLayouts developerPropertyLayouts;

}