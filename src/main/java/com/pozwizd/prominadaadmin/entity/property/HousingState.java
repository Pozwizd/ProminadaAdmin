package com.pozwizd.prominadaadmin.entity.property;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class HousingState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String description;

}
