package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.entity.Role;
import com.pozwizd.prominadaadmin.mapper.PersonalMapper;
import com.pozwizd.prominadaadmin.models.personal.PersonalDto;
import com.pozwizd.prominadaadmin.repository.PersonalRepository;
import com.pozwizd.prominadaadmin.specification.PersonalSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonalService {

    private final PersonalRepository personalRepository;
    private final PersonalMapper personalMapper;
    private final PasswordEncoder passwordEncoder;

    public List<Personal> findAll() {
        return personalRepository.findAll();
    }

    public Optional<Personal> findById(Long id) {
        return personalRepository.findById(id);
    }

    public Optional<Personal> findByEmail(String email) {
        return personalRepository.findByEmail(email);
    }

    @Transactional
    public Personal save(Personal personal) {
        personal.setPassword(passwordEncoder.encode(personal.getPassword()));
        return personalRepository.save(personal);
    }

    @Transactional
    public void deleteById(Long id) {
        personalRepository.deleteById(id);
    }

    public Page<PersonalDto> getPageablePersonal(int page, Integer size,
                                                 String surname,
                                                 String name,
                                                 String lastName,
                                                 String phoneNumber,
                                                 String email,
                                                 String role) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return personalMapper.toDto(personalRepository.findAll(PersonalSpecification.search(surname,
                name,
                lastName,
                phoneNumber,
                email,
                role),
                pageRequest));

    }
}