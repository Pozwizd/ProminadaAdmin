package com.pozwizd.prominadaadmin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phoneNumber;

    private String description;

    @ManyToOne
    @JoinColumn(name = "personal_id")
    private Personal personal;

    @ManyToOne
    @JoinColumn(name = "realtor_id")
    private Realtor realtor;


    public Feedback() {

    }
}