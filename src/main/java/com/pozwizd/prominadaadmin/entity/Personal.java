package com.pozwizd.prominadaadmin.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Data
public class Personal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String surname;

    private String name;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String password;

    private String pathAvatar;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "personal", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Feedback> feedBacks;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "personal_id")
    private List<DocumentFeedback> documentFeedbacks;

    @OneToMany(mappedBy = "personal", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Branch> branches;

}
