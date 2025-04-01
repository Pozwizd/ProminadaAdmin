package com.pozwizd.prominadaadmin.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Data
public class Feedback {

    @Id
    private Long id;

    private String code;

    private String phoneNumber;

    private String name;

    private String email;

    private String address;

    private String pathImage;
    
    @ManyToOne
    @JoinColumn(name = "personal_id")
    private Personal personal;
}