package com.pozwizd.prominadaadmin.entity.customer;

import com.pozwizd.prominadaadmin.entity.Realtor;
import com.pozwizd.prominadaadmin.entity.other.Distinct;
import com.pozwizd.prominadaadmin.entity.other.Topozone;
import com.pozwizd.prominadaadmin.entity.property.HousingState;
import com.pozwizd.prominadaadmin.entity.property.enums.ConditionInterior;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class CustomerApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "distinct_id")
    private Distinct distinct;

    @OneToOne
    @JoinColumn(name = "topozone_id")
    private Topozone topozone;

    private String roomCount;

    private Integer floor;
    private Double price;

    private Double area; //не зрозумів різниці між ploat
    private Double plot;

    @OneToOne
    @JoinColumn(name = "housing_state_id")
    private HousingState housingState;

    private Boolean isActive;

    private Boolean landOwnership;

    private ConditionInterior conditionInterior;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "realtor_id")
    private Realtor realtor;

    @Lob
    private String commentary;

    @OneToMany(mappedBy = "customerApplication")
    private List<CustomerApplicationHistory> customerApplicationHistories;
}
