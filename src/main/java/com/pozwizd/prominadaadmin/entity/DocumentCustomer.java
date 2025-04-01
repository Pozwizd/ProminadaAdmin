package com.pozwizd.prominadaadmin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "document_customer")
public class DocumentCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String pathImage;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}