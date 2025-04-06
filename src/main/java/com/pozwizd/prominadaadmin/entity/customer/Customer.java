package com.pozwizd.prominadaadmin.entity.customer;

import com.pozwizd.prominadaadmin.entity.Realtor;
import com.pozwizd.prominadaadmin.entity.SourceInformation;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String surname;

    private String name;

    private String lastName;

    private String phoneNumber;

    private String email;

    @ManyToOne
    @JoinColumn(name = "realtor_id")
    private Realtor realtor;

    private String passportNumber;

    private LocalDate dateOfBirth;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    private List<CustomerDocument> documentCustomers;

    @Enumerated(EnumType.STRING)
    private SourceInformation sourceInformation;

    @OneToMany(mappedBy = "customer")
    private List<CustomerNote> customerNotes;
}
