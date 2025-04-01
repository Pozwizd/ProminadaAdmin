package com.pozwizd.prominadaadmin.entity.property.developerProperty;

import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class DeveloperPropertyGalleryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String pathImage;

    @ManyToOne
    @JoinColumn(name = "developer_property_id")
    private DeveloperProperty developerProperty;
}