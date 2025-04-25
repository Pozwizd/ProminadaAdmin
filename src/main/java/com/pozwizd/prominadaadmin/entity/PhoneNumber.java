package com.pozwizd.prominadaadmin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@ToString(exclude = {"realtor"})
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "phone_number")
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String phoneNumber;

    private ContactType contactType;

    @ManyToOne
    @JoinColumn(name = "realtor_id")
    private Realtor realtor;

}