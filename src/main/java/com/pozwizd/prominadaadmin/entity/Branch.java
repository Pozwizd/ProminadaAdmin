package com.pozwizd.prominadaadmin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"personals"})
@EqualsAndHashCode(of = "id")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String phoneNumber;

    private String name;

    private String email;

    private String address;

    private String imagePath;

    @ManyToMany
    @JoinTable(
            name = "branch_personal",
            joinColumns = @JoinColumn(name = "branch_id"),
            inverseJoinColumns = @JoinColumn(name = "personal_id")
    )
    private List<Personal> personals;

    @ManyToMany
    @JoinTable(
            name = "branch_realtor",
            joinColumns = @JoinColumn(name = "branch_id"),
            inverseJoinColumns = @JoinColumn(name = "realtor_id")
    )
    private List<Realtor> realtors;

    public void addPersonal(Personal personal) {
        personals.add(personal);
        personal.getBranches().add(this);
    }

    public void addRealtor(Realtor realtor) {
        realtors.add(realtor);
        realtor.getBranches().add(this);
    }
}