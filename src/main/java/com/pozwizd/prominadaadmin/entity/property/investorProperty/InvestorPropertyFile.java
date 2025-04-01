package com.pozwizd.prominadaadmin.entity.property.investorProperty;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class InvestorPropertyFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String pathImage;

    @ManyToOne
    @JoinColumn(name = "investor_property_id")
    private InvestorProperty investorProperty;

}