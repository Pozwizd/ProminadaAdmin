package com.pozwizd.prominadaadmin.entity.property.residentialLand;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "residential_land_gallery_image")
public class ResidentialLandGalleryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String pathImage;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residential_land_id")
    @ToString.Exclude
    private ResidentialLand residentialLand;


}