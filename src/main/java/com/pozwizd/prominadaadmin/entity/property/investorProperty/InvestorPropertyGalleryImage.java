package com.pozwizd.prominadaadmin.entity.property.investorProperty;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "investor_property_gallery_image")
public class InvestorPropertyGalleryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String pathImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investor_property_id")
    @ToString.Exclude
    private InvestorProperty investorProperty;

}