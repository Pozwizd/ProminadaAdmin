package com.pozwizd.prominadaadmin.security;

import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.repository.PersonalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class PersonalDetailsService implements UserDetailsService {

    private final PersonalRepository personalRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Personal personal = personalRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new User(
                personal.getEmail(),
                personal.getPassword(),
                mapRolesToAuthorities(personal)
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Personal personal) {
        // Spring Security ожидает, что роли будут префикс с помощью "ROLE_"
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + personal.getRole().name()));
    }
}