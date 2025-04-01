package com.pozwizd.prominadaadmin.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Realtor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String pathAvatar;

    private String name;

    private String surname;

    private String lastName;

    private String email;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "realtor")
    private List<PhoneNumber> phoneNumbers;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "realtor_id")
    private List<Feedback> feedBacks;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "realtor")
    private List<Customer> customer;
}
