package com.pozwizd.prominadaadmin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.*;

@Entity
@Getter
@Setter
@ToString(exclude = {"personals"})
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
    @JsonIgnore
    private Set<Personal> personals = new HashSet<>();

    public void addPersonal(Personal personal) {
        personals.add(personal);
        personal.getBranches().add(this);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Branch branch = (Branch) o;
        return getId() != null && Objects.equals(getId(), branch.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public void removePersonal(Personal personal) {
        personals.remove(personal);
        personal.getBranches().remove(this);
    }

    public void removeAllPersonals() {
        for (Personal personal : new HashSet<>(personals)) {
            removePersonal(personal);
        }
    }
}