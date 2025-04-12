package com.pozwizd.prominadaadmin.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String phoneNumber;

    private String name;

    private String email;

    private String address;

    private String imagePath;

    @ManyToMany
    @JoinTable(
            name = "branch_personal",
            joinColumns = @JoinColumn(name = "branch_id"),
            inverseJoinColumns = @JoinColumn(name = "personal_id")
    )
    private List<Personal> personals;

}