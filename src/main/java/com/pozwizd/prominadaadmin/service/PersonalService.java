package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.models.personal.PersonalRequest;
import com.pozwizd.prominadaadmin.models.personal.PersonalResponse;
import com.pozwizd.prominadaadmin.models.personal.PersonalTableResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PersonalService {

    List<Personal> findAll();

    Optional<Personal> findById(Long id);

    Optional<Personal> findByEmail(String email);

    @Transactional
    Personal save(Personal personal);

    @Transactional
    void deleteById(Long id);

    Page<PersonalTableResponse> getPageablePersonal(int page, Integer size,
                                                    String surname,
                                                    String name,
                                                    String lastName,
                                                    String phoneNumber,
                                                    String email,
                                                    String role);

    @Transactional
    PersonalResponse saveFromRequest(PersonalRequest personalRequest);


    @Transactional
    PersonalResponse updatePersonal(PersonalRequest personalRequest);
}