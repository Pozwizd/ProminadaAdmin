package com.pozwizd.prominadaadmin.entity.customer;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class CustomerNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String note;
    private LocalDate dateOfCreating;

    @ManyToOne
    private Customer customer;
}
