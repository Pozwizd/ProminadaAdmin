package com.pozwizd.prominadaadmin.entity.property.secondaryProperty;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SecondaryPropertyFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String path;

    @ManyToOne
    @JoinColumn(name = "secondary_property_id")
    private SecondaryProperty secondaryProperty;

}
