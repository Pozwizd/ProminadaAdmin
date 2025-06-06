package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.entity.Realtor;
import com.pozwizd.prominadaadmin.models.realtor.RealtorRequest;
import com.pozwizd.prominadaadmin.models.realtor.RealtorTableResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface RealtorService {
    List<Realtor> findAll();

    Optional<Realtor> findById(Long id);

    Optional<Realtor> findByEmail(String email);

    Realtor save(Realtor personal);

    void deleteById(Long id);

    Page<RealtorTableResponse> getPageableRealtor(int page, Integer size,
                                                  String id,
                                                  String code,
                                                  String fullName,
                                                  String email,
                                                  String dateOfBirthday);

    void deleteRealtor(Long id);

    Realtor getRealtorById(Long id);

    void saveFromRequest(RealtorRequest request);

    void updateRealtor(@Valid RealtorRequest personalRequest);
}
