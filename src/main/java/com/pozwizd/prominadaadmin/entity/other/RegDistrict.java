package com.pozwizd.prominadaadmin.entity.other;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class RegDistrict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String name;
}
