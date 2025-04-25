package com.pozwizd.prominadaadmin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@ToString(exclude = {"personal"})
@EqualsAndHashCode(of = "id")
@Entity
public class DocumentFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;
    private String path;

    @ManyToOne
    @JoinColumn(name = "personal_id")
    private Personal personal;


}