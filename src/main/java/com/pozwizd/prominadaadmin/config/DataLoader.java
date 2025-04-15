package com.pozwizd.prominadaadmin.config;

import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.entity.Role; // Assuming Role enum exists here
import com.pozwizd.prominadaadmin.entity.other.City;
import com.pozwizd.prominadaadmin.entity.other.District;
import com.pozwizd.prominadaadmin.entity.other.RegDistrict;
import com.pozwizd.prominadaadmin.entity.other.Topozone;
import com.pozwizd.prominadaadmin.entity.property.BuildingCompany;
import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty;
import com.pozwizd.prominadaadmin.entity.property.enums.DeliveryDate;
import com.pozwizd.prominadaadmin.repository.PersonalRepository;
import com.pozwizd.prominadaadmin.service.*;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataLoader {
    private final PersonalService personalService;
    private final PersonalRepository personalRepository;
    private final BuilderPropertyService builderPropertyService;
    private final CityService cityService;
    private final RegDistrictService regDistrictService;
    private final DistrictService districtService;
    private final TopozoneService topozoneService;
    private final BuildingCompanyService buildingCompanyService;

    @EventListener(ApplicationReadyEvent.class)
    public void loadEntity() {
        loadAdmin();
        loadFakeUsers();
        loadFakeRegDistrict();
        loadFakeCities();
        loadFakeDistrict();
        loadFakeTopozone();
        loadFakeBuildingCompany();
        loadFakeBuilderProperties();
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

    public void loadFakeCities() {
        Faker faker = new Faker(new Locale("uk"));
        List<RegDistrict> regDistricts = regDistrictService.getAll();
        for (int i = 0; i < 10; i++) {
            City city = new City();
            city.setName(faker.address().cityName());
            city.setRegDistrict(getRandomEntity(regDistricts));
            cityService.save(city);
        }
    }

    public void loadFakeRegDistrict() {
        Faker faker = new Faker(new Locale("uk"));
        for (int i = 0; i < 10; i++) {
            RegDistrict regDistrict = new RegDistrict();
            regDistrict.setName(faker.address().state());
            regDistrictService.save(regDistrict);
        }
    }

    public void loadFakeDistrict() {
        Faker faker = new Faker(new Locale("uk"));
        List<City> cities = cityService.getAll();
        for (int i = 0; i < 10; i++) {
            District district = new District();
            district.setName(faker.address().streetName());
            district.setCity(getRandomEntity(cities));
            districtService.save(district);
        }
    }

    public void loadFakeTopozone() {
        Faker faker = new Faker(new Locale("uk"));
        List<District> districts = districtService.getAll();
        for (int i = 0; i < 10; i++) {
            Topozone topozone = new Topozone();
            topozone.setName(faker.address().buildingNumber());
            topozone.setDistrict(getRandomEntity(districts));
            topozoneService.save(topozone);
        }
    }

    public void loadFakeBuildingCompany() {
        Faker faker = new Faker(new Locale("uk"));
        for (int i = 0; i < 10; i++) {
            BuildingCompany buildingCompany = new BuildingCompany();
            buildingCompany.setName(faker.name().lastName() + " " + suffixes.get(faker.random().nextInt(suffixes.size())));
            buildingCompanyService.save(buildingCompany);
        }
    }


    List<String> suffixes = List.of("Буд", "Інвест", "Девелопмент", "Строй", "Будгруп", "Пром");

    public void loadFakeBuilderProperties() {
        Faker faker = new Faker(new Locale("uk"));
        Random random = new Random();
        for (int i = 0; i < 25; i++) {
            BuilderProperty builder = new BuilderProperty();
            builder.setName(faker.company().name());

            builder.setCity(getRandomEntity(cityService.getAll()));
            builder.setRegDistrict(getRandomEntity(regDistrictService.getAll()));
            builder.setDistinct(getRandomEntity(districtService.getAll()));
            builder.setTopozone(getRandomEntity(topozoneService.getAll()));
            builder.setBuildingCompany(getRandomEntity(buildingCompanyService.getAll()));

            builder.setStreet(faker.address().streetName());
            builder.setHouseNumber(faker.address().buildingNumber());
            builder.setHouseSection(String.valueOf(faker.number().numberBetween(1, 5)));
            builder.setTotalFloor(faker.number().numberBetween(5, 25));

            builder.setDeliveryDate(DeliveryDate.values()[random.nextInt(DeliveryDate.values().length)]);
            builder.setPhoneNumber(faker.phoneNumber().phoneNumber());

            builder.setFloorPlanFile("floor_plan_" + faker.file().fileName());
            builder.setMortgageConditions("mortgage_" + faker.file().fileName());
            builder.setPriceFile("prices_" + faker.file().fileName());

            builder.setDescription(faker.lorem().paragraph());
            builder.setActionTitle(faker.lorem().sentence());
            builder.setActionDescription(faker.lorem().paragraph());
            builder.setIsAction(faker.bool().bool());

            builder.setDateOfCreating(LocalDate.now().minusDays(faker.number().numberBetween(0, 365)));

            builderPropertyService.save(builder);
        }
    }

    public <T> T getRandomEntity(List<T> list) {
        if (!list.isEmpty()) {
            return list.get(new Random().nextInt(list.size()));
        }
        return null;
    }
}