package com.pozwizd.prominadaadmin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "image_banner")
public class ImageBanner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private Integer priority;

    private String pathImage;

    @ManyToOne
    @JoinColumn(name = "banner_id")
    private Banner banner;

}