package com.pozwizd.prominadaadmin.entity.property.secondaryProperty;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "secondary_property_gallery_image")
public class SecondaryPropertyGalleryImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String pathImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secondary_property_id")
    @ToString.Exclude
    private SecondaryProperty secondaryProperty;
}
