package com.pozwizd.prominadaadmin.models.filter;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class RealtorFilter {
    private Integer page = 0;
    private Integer size = 10;
    private Long code;
    private String name;
    private String surname;
    private String lastName;
    private String email;
    private LocalDate birthday;
}
