package com.pozwizd.prominadaadmin.entity;

import com.pozwizd.prominadaadmin.entity.customer.Customer;
import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.entity.property.commercial.CommercialProperties;
import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorProperty;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"phoneNumbers", "feedBacks", "customer", "investorProperties", "secondaryProperties", "residentialLands","documentFeedbacks",
        "commercialProperties",
        "branches"})
@EqualsAndHashCode(of = "id")
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

    private String code;

    private String password;

    private LocalDate dateOfBirthday;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "realtor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhoneNumber> phoneNumbers;

    @OneToMany(mappedBy = "realtor", fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Feedback> feedBacks;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "realtor")
    private List<Customer> customer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "realtor")
    private List<InvestorProperty> investorProperties;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "realtor")
    private List<SecondaryProperty> secondaryProperties;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "realtor")
    private List<ResidentialLand> residentialLands;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "realtor")
    private List<CommercialProperties> commercialProperties;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DocumentFeedback> documentFeedbacks;

    @ManyToMany(mappedBy = "realtors", cascade = CascadeType.REFRESH)
    private List<Branch> branches;
}
