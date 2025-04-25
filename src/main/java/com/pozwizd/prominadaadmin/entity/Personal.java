package com.pozwizd.prominadaadmin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"feedBacks", "documentFeedbacks", "branches"})
@EqualsAndHashCode(of = "id")
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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DocumentFeedback> documentFeedbacks;


    @ManyToMany(mappedBy = "personals", cascade = CascadeType.REFRESH)
    private List<Branch> branches;

}
