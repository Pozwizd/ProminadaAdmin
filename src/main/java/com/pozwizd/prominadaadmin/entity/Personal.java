package com.pozwizd.prominadaadmin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.*;

@Entity
@Getter
@Setter
@ToString(exclude = {"feedBacks", "documentFeedbacks", "branches"})
public class Personal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Version
    @Column(name = "version")
    private Long version;

    private String surname;

    private String name;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String password;

    private String pathAvatar;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "personal_feedback",
            joinColumns = @JoinColumn(name = "personal_id"),
            inverseJoinColumns = @JoinColumn(name = "feedback_id")
    )
    private List<Feedback> feedBacks = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "personal_document_feedback",
            joinColumns = @JoinColumn(name = "personal_id"),
            inverseJoinColumns = @JoinColumn(name = "document_feedback_id")
    )
    private List<DocumentFeedback> documentFeedbacks = new ArrayList<>();


    @ManyToMany(mappedBy = "personals", cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private Set<Branch> branches = new HashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Personal personal = (Personal) o;
        return getId() != null && Objects.equals(getId(), personal.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public void removeBranch(Branch branch) {
        branches.remove(branch);
        branch.getPersonals().remove(this);
    }

    public void removeAllBranches() {
        for (Branch branch : new HashSet<>(branches)) {
            removeBranch(branch);
        }
    }
}
