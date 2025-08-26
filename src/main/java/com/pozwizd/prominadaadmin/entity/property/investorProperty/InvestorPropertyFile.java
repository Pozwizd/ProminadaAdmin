package com.pozwizd.prominadaadmin.entity.property.investorProperty;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "investor_property_file")
public class InvestorPropertyFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investor_property_id")
    @ToString.Exclude
    private InvestorProperty investorProperty;

}