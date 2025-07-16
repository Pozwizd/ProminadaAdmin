package com.pozwizd.prominadaadmin.entity.location;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Topozone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String name;

}
