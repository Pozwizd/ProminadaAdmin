package com.pozwizd.prominadaadmin.entity.property.commercialProperty;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "commercial_properties_file")
public class CommercialPropertiesFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commercial_properties_id")
    @ToString.Exclude
    private CommercialProperties commercialProperties;



}