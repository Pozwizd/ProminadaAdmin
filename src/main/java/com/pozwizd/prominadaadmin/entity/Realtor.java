package com.pozwizd.prominadaadmin.entity;

import com.pozwizd.prominadaadmin.entity.customer.Customer;
import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.entity.property.commercial.CommercialProperties;
import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorProperty;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString(exclude = {"phoneNumbers"})
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Realtor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private Long code;

    private String pathAvatar;

    private String name;

    private String surname;

    private String lastName;

    private String email;

    private LocalDate birthday;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "realtor", cascade = CascadeType.ALL)
    private List<PhoneNumber> phoneNumbers;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "realtor_document_feedback",
            joinColumns = @JoinColumn(name = "realtor_id"),
            inverseJoinColumns = @JoinColumn(name = "document_feedback_id")
    )
    private List<DocumentFeedback> documentFeedbacks = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "realtor_feedback",
            joinColumns = @JoinColumn(name = "realtor_id"),
            inverseJoinColumns = @JoinColumn(name = "feedback_id")
    )
    private List<Feedback> feedBacks = new ArrayList<>();


//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "realtor")
//    private List<Customer> customer;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "realtor")
//    private List<InvestorProperty> investorProperties;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "realtor")
//    private List<SecondaryProperty> secondaryProperties;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "realtor")
//    private List<ResidentialLand> residentialLands;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "realtor")
//    private List<CommercialProperties> commercialProperties;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Realtor realtor = (Realtor) o;
        return getId() != null && Objects.equals(getId(), realtor.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
