package com.pozwizd.prominadaadmin.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private Boolean status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "banner")
    private List<ImageBanner> imageBanners;


}
