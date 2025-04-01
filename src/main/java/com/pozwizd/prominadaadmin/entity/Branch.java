package com.pozwizd.prominadaadmin.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class Branch {
    @Id
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "personal_id")
    private Personal personal;

}