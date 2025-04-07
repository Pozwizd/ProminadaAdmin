package com.pozwizd.prominadaadmin.config;

import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.entity.Role; // Assuming Role enum exists here
import com.pozwizd.prominadaadmin.repository.PersonalRepository;
import com.pozwizd.prominadaadmin.service.PersonalService;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class DataLoader {
    private final PersonalService personalService;
    private final PersonalRepository personalRepository;


    @EventListener(ApplicationReadyEvent.class)
    public void loadEntity() {
        loadAdmin();
        loadFakeUsers();
    }

    public void loadAdmin() {
        Personal personal = new Personal();
        personal.setName("Roman");
        personal.setSurname("SpaceLab");
        personal.setEmail("admin@gmail.com");
        personal.setPhoneNumber("+380123456789");
        personal.setPassword("admin");
        personal.setRole(Role.ADMIN);
        personalService.save(personal);
    }

    public void loadFakeUsers() {
        Faker faker = new Faker(new Locale("uk"));
        for (int i = 0; i < 100; i++) {
            Personal personal = new Personal();
            personal.setName(faker.name().firstName());
            personal.setSurname(faker.name().lastName());
            personal.setEmail(faker.internet().emailAddress());
            personal.setPhoneNumber(faker.phoneNumber().phoneNumber());
            personal.setPassword(faker.internet().password());
            personal.setRole(Role.USER);
            personalService.save(personal);
        }
    }
}