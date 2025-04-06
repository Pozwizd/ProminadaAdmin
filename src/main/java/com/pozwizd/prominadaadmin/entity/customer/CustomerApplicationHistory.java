package com.pozwizd.prominadaadmin.entity.customer;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;

@Entity
@Data
public class CustomerApplicationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateOfCreating;
    private String Data;
    @ManyToOne
    private CustomerApplication customerApplication;
}
