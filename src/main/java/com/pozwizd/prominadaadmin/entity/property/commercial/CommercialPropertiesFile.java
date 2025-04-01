package com.pozwizd.prominadaadmin.entity.property.commercial;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "commercial_properties_file")
public class CommercialPropertiesFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String path;

    @ManyToOne
    @JoinColumn(name = "commercial_properties_id")
    private CommercialProperties commercialProperties;



}