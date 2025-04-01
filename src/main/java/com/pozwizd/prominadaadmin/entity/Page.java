package com.pozwizd.prominadaadmin.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String title;

    private String description;

}
