package com.pozwizd.prominadaadmin.config;

import com.pozwizd.prominadaadmin.entity.*;
import com.pozwizd.prominadaadmin.entity.location.*;
import com.pozwizd.prominadaadmin.entity.property.BuildingCompany;
import com.pozwizd.prominadaadmin.entity.property.HousingState;
import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialProperties;
import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialPropertiesMain;
import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorProperty;
import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorPropertyFile;
import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorPropertyGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorPropertyMain;
import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLandFile;
import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLandGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLandMain;
import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty;
import com.pozwizd.prominadaadmin.entity.property.enums.*;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryProperty;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyFile;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyMain;
import com.pozwizd.prominadaadmin.repository.secondary.*;
import com.pozwizd.prominadaadmin.service.*;
import com.pozwizd.prominadaadmin.service.location.CityService;
import com.pozwizd.prominadaadmin.service.location.RegionService;
import com.pozwizd.prominadaadmin.service.location.TopozoneService;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class DataLoader {
    private final Faker faker;

    // Location
    private final RegionService regionService;
    private final CityService cityService;
    private final TopozoneService topozoneService;

    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;
    private final StreetRepository streetRepository;
    private final HouseRepository houseRepository;

    // Property
    private final BuilderPropertyService builderPropertyService;
    private final BuildingCompanyService buildingCompanyService;
    private final ImageBannerService imageBannerService;
    private final ResidentialLandService residentialLandService;

    private final HousingStateService housingStateService;
    private final CommercialPropertiesService commercialPropertiesService;

    private final RealtorService realtorService;
    private final PersonalService personalService;

    private final FeedbackService feedbackService;
    private final DocumentFeedbackService documentFeedbackService;

    private final BranchService branchServiceImp;

    private final BannerService bannerService;

    private final InvestorPropertyService investorPropertyService;
    private final SecondaryPropertyService secondaryPropertyService;


    private final PageService pageService;

    @EventListener(ApplicationReadyEvent.class)
    public void loadEntity() {
        loadPages();
        loadDataFromCsv();
        loadBranch();
        loadAdmin();
        loadPersonal();
        loadBanner();
        loadFakeTopozone();
        loadRealtor();
        loadResidentialLand();
        loadFakeBuilderProperties();
        loadFakeBuildingCompany();
        loadHouseEstate();
        loadCommercialProperty();
        loadInvestorProperty();
        loadSecondaryProperty();
    }

    private void loadSecondaryProperty() {
        List<Region> regions = regionRepository.findAll();
        List<Realtor> realtors = realtorService.findAll();
        List<HousingState> housingStates = housingStateService.findAll();
        List<Topozone> topozones = topozoneService.getAll();

        for (int i = 0; i < 20; i++) {
            final int propertyIndex = i; // Создаем final копию для использования в лямбда

            Region region = getRandomEntity(regions);
            City city = getRandomEntity(region.getCities());
            District district = getRandomEntity(city.getDistricts());
            Street street = getRandomEntity(district.getStreets());
            House house = getRandomEntity(street.getHouses());

            SecondaryProperty secondaryProperty = SecondaryProperty.builder()
                    .region(region)
                    .city(city)
                    .district(district)
                    .street(street)
                    .house(house)
                    .topozone(getRandomEntity(topozones))
                    .houseSection(faker.address().buildingNumber())
                    .flatNumber(String.valueOf(faker.number().numberBetween(1, 300)))
                    .ownerFullName(faker.name().fullName())
                    .phoneNumber(faker.phoneNumber().phoneNumber())
                    .acquisitionDate(LocalDate.now().minusDays(faker.number().numberBetween(1, 1000)))
                    .ownershipDoc(getRandomEnum(OwnershipDoc.class))
                    .importantComment(faker.lorem().sentence())
                    .adminComment(faker.lorem().sentence())
                    .dateOfCreating(LocalDate.now())
                    .build();

            // SecondaryPropertyMain - полное заполнение всех полей
            SecondaryPropertyMain main = SecondaryPropertyMain.builder()
                    .secondaryProperty(secondaryProperty)
                    .realtor(getRandomEntity(realtors))
                    .housingState(getRandomEntity(housingStates))
                    .publicationStatus(getRandomEnum(PublicationStatus.class))
                    .objectCode(faker.code().isbn10())
                    .branchCode(faker.code().asin())
                    .employeeCode(faker.code().ean8())
                    .personalName(faker.name().fullName())
                    .landmark(faker.address().streetName())
                    .floor(faker.number().numberBetween(1, 12))
                    .floors(faker.number().numberBetween(1, 25))
                    .rooms(faker.number().numberBetween(1, 5))
                    .price(faker.number().randomDouble(2, 50000, 30000000))
                    .commissioningDate(LocalDate.now().minusYears(faker.number().numberBetween(1, 50)))
                    .typeProperty(getRandomEnum(TypePropertySecondary.class))
                    .totalArea(faker.number().randomDouble(2, 30, 400))
                    .livingArea(faker.number().randomDouble(2, 20, 300))
                    .kitchenArea(faker.number().randomDouble(2, 5, 40))
                    .apartmentLayout(getRandomEnum(ApartmentLayout.class))
                    .roomSizes(faker.lorem().sentence())
                    .ceilingHeight(faker.number().randomDouble(1, (int) 2.3, (int) 3.8))
                    .projectHouse(getRandomEnum(ProjectHouse.class))
                    .wallMaterial(getRandomEnum(WallMaterial.class))
                    .conditionFlat(getRandomEnum(ConditionFlat.class))
                    .kitchen(getRandomEnum(Kitchen.class))
                    .bathroom(faker.number().numberBetween(1, 3))
                    .balcony(getRandomEnum(BalconyType.class))
                    .viewFromWindows(faker.lorem().sentence())
                    .cooker(getRandomEnum(Cooker.class))
                    .heating(getRandomEnum(Heating.class))
                    .stairs(getRandomEnum(Stairs.class))
                    .floorType(getRandomEnum(FloorType.class))
                    .typeWindows(getRandomEnum(TypeWindows.class))
                    .carpentryCondition(getRandomEnum(CarpentryCondition.class))
                    .entranceDoor(getRandomEnum(EntranceDoor.class))
                    .lastCommunication(LocalDate.now().minusDays(faker.number().numberBetween(1, 30)))
                    .isVnp(faker.bool().bool())
                    .vnpDate(faker.bool().bool() ?
                            LocalDate.now().minusDays(faker.number().numberBetween(1, 365)).toString() : null)
                    .sourceInformation(getRandomEnum(SourceInformation.class))
                    .hasTrade(faker.bool().bool())
                    .hasExclusive(faker.bool().bool())
                    .urgent(faker.bool().bool())
                    .isFree(faker.bool().bool())
                    .isOpenObject(faker.bool().bool())
                    .forOffice(faker.bool().bool())
                    .fromMediator(faker.bool().bool())
                    .withFurniture(faker.bool().bool())
                    .description(faker.lorem().paragraph())
                    .AdvertisingHeadline(faker.lorem().sentence())
                    .AdvertisingText(faker.lorem().paragraph())
                    .isAdvertising(faker.bool().bool())
                    .build();

            secondaryProperty.setSecondaryPropertyMain(main);

            // Создаем final ссылку для использования в лямбда-выражениях
            final SecondaryProperty finalSecondaryProperty = secondaryProperty;

            // Gallery Images
            List<SecondaryPropertyGalleryImage> galleryImages = IntStream.range(0, faker.number().numberBetween(2, 8))
                    .mapToObj(j -> SecondaryPropertyGalleryImage.builder()
                            .name("secondary_image_" + j + ".jpg")
                            .pathImage("/images/secondary_property_" + propertyIndex + "_image_" + j + ".jpg")
                            .secondaryProperty(finalSecondaryProperty)
                            .build()
                    ).collect(Collectors.toList());
            secondaryProperty.setSecondaryPropertyGalleryImages(galleryImages);

            // Files
            List<SecondaryPropertyFile> propertyFiles = IntStream.range(0, faker.number().numberBetween(1, 4))
                    .mapToObj(k -> SecondaryPropertyFile.builder()
                            .name("secondary_document_" + k + ".pdf")
                            .path("/files/secondary_property_" + propertyIndex + "_file_" + k + ".pdf")
                            .secondaryProperty(finalSecondaryProperty)
                            .build()
                    ).collect(Collectors.toList());
            secondaryProperty.setSecondaryPropertyFiles(propertyFiles);

            // Сохраняем объект через сервис или репозиторий
            secondaryPropertyService.create(secondaryProperty);
        }
    }


    private void loadInvestorProperty() {
        List<Region> regions = regionRepository.findAll();
        List<Realtor> realtors = realtorService.findAll();
        List<HousingState> housingStates = housingStateService.findAll();
        List<Topozone> topozones = topozoneService.getAll();

        for (int i = 0; i < 20; i++) {
            Region region = getRandomEntity(regions);
            City city = getRandomEntity(region.getCities());
            District district = getRandomEntity(city.getDistricts());
            Street street = getRandomEntity(district.getStreets());
            House house = getRandomEntity(street.getHouses());

            InvestorProperty investorProperty = InvestorProperty.builder()
                    .region(region)
                    .city(city)
                    .district(district)
                    .street(street)
                    .house(house)
                    .topozone(getRandomEntity(topozones))
                    .houseSection(faker.address().buildingNumber())
                    .flatNumber(String.valueOf(faker.number().numberBetween(1, 300)))
                    .ownerFullName(faker.name().fullName())
                    .phoneNumber(faker.phoneNumber().phoneNumber())
                    .acquisitionDate(LocalDate.now().minusDays(faker.number().numberBetween(1, 1000)))
                    .ownershipDoc(getRandomEnum(OwnershipDoc.class))
                    .importantComment(faker.lorem().sentence())
                    .adminComment(faker.lorem().sentence())
                    .dateOfCreating(LocalDate.now())
                    .realtor(getRandomEntity(realtors))
                    .build();

            // InvestorPropertyMain - полное заполнение всех полей
            InvestorPropertyMain main = InvestorPropertyMain.builder()
                    .investorProperty(investorProperty)
                    .housingState(getRandomEntity(housingStates))
                    .publicationStatus(getRandomEnum(PublicationStatus.class))
                    .objectCode(faker.code().isbn10())
                    .branchCode(faker.number().numberBetween(1000, 9999))
                    .employeeCode(faker.number().numberBetween(100, 999))
                    .landmark(faker.address().streetName())
                    .floor(faker.number().numberBetween(1, 12))
                    .floors(faker.number().numberBetween(1, 25))
                    .rooms(faker.number().numberBetween(1, 5))
                    .price(faker.number().randomDouble(2, 100000, 50000000))
                    .deliveryDate(getRandomEnum(DeliveryDate.class))
                    .commissioningDate(LocalDate.now().minusYears(faker.number().numberBetween(1, 10)))
                    .totalArea(faker.number().randomDouble(2, 40, 500))
                    .livingArea(faker.number().randomDouble(2, 30, 350))
                    .kitchenArea(faker.number().randomDouble(2, 6, 50))
                    .roomSizes(faker.lorem().sentence())
                    .ceilingHeight(faker.number().randomDouble(1, (int) 2.5, (int) 4.0))
                    .wallMaterial(getRandomEnum(WallMaterial.class))
                    .conditionFlat(getRandomEnum(ConditionFlat.class))
                    .kitchen(getRandomEnum(Kitchen.class))
                    .bathroom(faker.number().numberBetween(1, 3))
                    .balcony(getRandomEnum(BalconyType.class))
                    .viewFromWindows(faker.lorem().sentence())
                    .cooker(getRandomEnum(Cooker.class))
                    .heating(getRandomEnum(Heating.class))
                    .lastCommunication(LocalDate.now().minusDays(faker.number().numberBetween(1, 30)))
                    .isVnp(faker.bool().bool())
                    .vnpDate(faker.bool().bool() ?
                            LocalDate.now().minusDays(faker.number().numberBetween(1, 365)) : null)
                    .sourceInformation(getRandomEnum(SourceInformation.class))
                    .hasTrade(faker.bool().bool())
                    .hasExclusive(faker.bool().bool())
                    .urgent(faker.bool().bool())
                    .isFree(faker.bool().bool())
                    .isOpenObject(faker.bool().bool())
                    .fromMediator(faker.bool().bool())
                    .description(faker.lorem().paragraph())
                    .AdvertisingHeadline(faker.lorem().sentence())
                    .AdvertisingText(faker.lorem().paragraph())
                    .isAdvertising(faker.bool().bool())
                    .build();

            investorProperty.setInvestorPropertyMain(main);

            // Gallery Images
            int finalI = i;
            List<InvestorPropertyGalleryImage> galleryImages = IntStream.range(0, faker.number().numberBetween(2, 7))
                    .mapToObj(j -> InvestorPropertyGalleryImage.builder()
                            .name("image_" + j + ".jpg")
                            .pathImage("/images/property_" + finalI + "_image_" + j + ".jpg")
                            .investorProperty(investorProperty)
                            .build()
                    ).collect(Collectors.toList());
            investorProperty.setInvestorPropertyGalleryImages(galleryImages);

            // Files
            int finalI1 = i;
            List<InvestorPropertyFile> propertyFiles = IntStream.range(0, faker.number().numberBetween(1, 5))
                    .mapToObj(k -> InvestorPropertyFile.builder()
                            .name("document_" + k + ".pdf")
                            .path("/files/property_" + finalI1 + "_file_" + k + ".pdf")
                            .investorProperty(investorProperty)
                            .build()
                    ).collect(Collectors.toList());
            investorProperty.setInvestorPropertyFiles(propertyFiles);

            // Сохраняем объект через сервис или репозиторий
            investorPropertyService.create(investorProperty);
        }
    }




    public void loadPages() {
        List<Page> pages = new ArrayList<>();
        pages.add(Page.builder().name("Главная").title("Главная страница").description("Описание главной страницы").build());
        pages.add(Page.builder().name("О нас").title("Страница о нас").description("Описание страницы о нас").build());
        pages.add(Page.builder().name("Контакты").title("Страница контактов").description("Описание страницы контактов").build());
        pages.add(Page.builder().name("Услуги").title("Страница услуг").description("Описание страницы услуг").build());
        pages.add(Page.builder().name("Блог").title("Страница блога").description("Описание страницы блога").build());


        for (Page page : pages) {
            pageService.createPage(page);
        }
    }

    private void loadHouseEstate() {
        for (int i = 0; i < 10; i++) {
            HousingState housingState = new HousingState();
            housingState.setName(faker.lorem().word());
            housingState.setDescription(faker.lorem().sentence());
            housingStateService.create(housingState);
        }
    }

    private void loadCommercialProperty() {

        List<Region> regions = regionRepository.findAll();
        for (int i = 0; i < 10; i++) {

            Region region = getRandomEntity(regions);
            City city = getRandomEntity(region.getCities());
            District district = getRandomEntity(city.getDistricts());
            Street street = getRandomEntity(district.getStreets());
            House house = getRandomEntity(street.getHouses());

            CommercialProperties commercialProperty = CommercialProperties.builder()
                    .region(region)
                    .city(city)
                    .district(district)
                    .street(street)
                    .house(house)
                    .houseSection(faker.number().digits(2))
                    .flatNumber(faker.number().digits(3))
                    .ownerName(faker.name().fullName())
                    .phoneNumber(faker.phoneNumber().phoneNumber())
                    .acquisitionDate(faker.date().birthday().toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDate())
                    .ownershipDoc(getRandomEnum(OwnershipDoc.class))
                    .comment(faker.lorem().sentence())
                    .cadastralNumber(faker.number().digits(10))
                    .langPurpose(faker.commerce().department())
                    .adminComment(faker.lorem().paragraph())
                    .dateOfCreating(LocalDate.now())
                    .build();

            CommercialPropertiesMain main = CommercialPropertiesMain.builder()
                    .commercialProperties(commercialProperty)
                    .publicationStatus(getRandomEnum(PublicationStatus.class))
                    .objectCode(faker.code().isbn10())
                    .branchCode(faker.number().numberBetween(1, 100))
                    .employeeCode(faker.number().numberBetween(1000, 9999))
                    .personalName(faker.name().fullName())
                    .price(faker.number().randomDouble(2, 50000, 5000000))
                    .landmark(faker.address().streetAddress())
                    .completionDate(faker.date().past(365, TimeUnit.DAYS).toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDate())
                    .commissioningDate(faker.date().past(200, TimeUnit.DAYS).toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDate())
                    .floor(faker.number().numberBetween(1, 20))
                    .totalFloor(faker.number().numberBetween(1, 25))
                    .roomCount(faker.number().numberBetween(1, 10))
                    .typeCommBuilding(getRandomEnum(TypeCommercialBuilding.class))
                    .isVnp(faker.bool().bool())
                    .vnpDate(faker.date().past(100, TimeUnit.DAYS).toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDate())
                    .sourceInformation(getRandomEnum(SourceInformation.class))
                    .area(faker.number().randomDouble(2, 20, 500))
                    .livingArea(faker.number().randomDouble(2, 15, 400))
                    .roomSizes(faker.number().digits(2) + "x" + faker.number().digits(2))
                    .ceilingHeight(faker.number().randomDouble(1, (int) 2.5, (int) 4.0) + "м")
                    .siteArea(faker.number().digits(3) + " кв.м")
                    .livingSiteArea(faker.number().randomDouble(2, 10, 300))
                    .designatedUseOfLand(getRandomEnum(DesignatedUseOfLand.class))
                    .landOwnership(faker.bool().bool())
                    .conditionInterior(getRandomEnum(ConditionInterior.class))
                    .conditionBuilding(getRandomEnum(ConditionBuilding.class))
                    .bathroom(faker.number().numberBetween(1, 5))
                    .viewFromWindows(faker.options().option("Во двор", "На улицу", "Парк", "Река"))
                    .hasFurnishings(faker.bool().bool())
                    .hasCarPark(faker.bool().bool())
                    .hasHousingStock(faker.bool().bool())
                    .hasFacade(faker.bool().bool())
                    .hasRailwayTracks(faker.bool().bool())
                    .gas(getRandomEnum(Gas.class))
                    .waterSupply(getRandomEnum(WaterSupply.class))
                    .sewage(getRandomEnum(Sewage.class))
                    .heating(getRandomEnum(Heating.class))
                    .airConditioner(getRandomEnum(AirConditioner.class))
                    .ventilation(getRandomEnum(Ventilation.class))
                    .stairs(getRandomEnum(Stairs.class))
                    .electrification(getRandomEnum(Electrification.class))
                    .floorType(getRandomEnum(FloorType.class))
                    .typeWindows(getRandomEnum(TypeWindows.class))
                    .carpentryCondition(getRandomEnum(CarpentryCondition.class))
                    .entranceDoor(getRandomEnum(EntranceDoor.class))
                    .lastCommunication(faker.date().past(30, TimeUnit.DAYS).toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDate())
                    .hasTrade(faker.bool().bool())
                    .hasExclusive(faker.bool().bool())
                    .urgent(faker.bool().bool())
                    .isFree(faker.bool().bool())
                    .isOpenObject(faker.bool().bool())
                    .fromMediator(faker.bool().bool())
                    .description(faker.lorem().paragraph(3))
                    .advertisingHeadline(faker.commerce().productName())
                    .advertisingText(faker.lorem().paragraph(2))
                    .isAdvertising(faker.bool().bool())
                    .build();

            commercialProperty.setCommercialPropertiesMain(main);

            commercialPropertiesService.create(commercialProperty);
        }
    }

    private static List<DocumentFeedback> getDocumentFeedbacks() {
        List<DocumentFeedback> documentFeedbacks = new ArrayList<>();
        File dir = new File("uploads/");
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".pdf")) {
                        DocumentFeedback feedback = new DocumentFeedback();
                        feedback.setName(file.getName());
                        feedback.setPathImage("uploads/" + file.getName());
                        documentFeedbacks.add(feedback);
                    }
                }
            }
        }
        return documentFeedbacks;
    }

    private void loadRealtor() {

        for (int i = 0; i < 10; i++) {

            Realtor realtor = realtorService
                    .create(Realtor
                            .builder()
                            .code("RE" + i)
                            .name(faker.name().fullName())
                            .surname(faker.name().lastName())
                            .lastName(faker.name().lastName())
                            .email(faker.internet().emailAddress())
                            .birthday(LocalDate.now()
                                    .minusYears(faker.number().numberBetween(18, 30))
                                    .minusDays(faker.number().numberBetween(0, 365)))
                            .build());
            List<PhoneNumber> phoneNumbers = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                PhoneNumber phoneNumber = new PhoneNumber();
                phoneNumber.setPhoneNumber(faker.phoneNumber().phoneNumber());
                phoneNumber.setContactType(ContactType.values()[faker.random().nextInt(ContactType.values().length)]);
                phoneNumber.setRealtor(realtor);
                phoneNumbers.add(phoneNumber);
            }
            List<Feedback> feedbacks = new ArrayList<>();

            for (int j = 0; j < 5; j++) {
                Feedback feedback = new Feedback();
                feedback.setName(faker.name().firstName());
                feedback.setPhoneNumber(faker.phoneNumber().phoneNumber());
                feedback.setDescription(faker.lorem().sentence(10));
                feedbacks.add(feedback);
            }

            List<DocumentFeedback> documentFeedbacks = getDocumentFeedbacks();
            realtor.setPhoneNumbers(phoneNumbers);
            realtor.setFeedBacks(feedbacks);
            realtor.setDocumentFeedbacks(documentFeedbacks);
            realtorService.create(realtor);
        }

    }

    private void loadBranch() {
        for (int i = 0; i < 100; i++) {
            Branch branch = new Branch();
            branch.setCode("BR" + i);
            branch.setName(faker.company().name());
            branch.setPhoneNumber(faker.phoneNumber().phoneNumber());
            branch.setEmail(faker.internet().emailAddress());
            branch.setAddress(faker.address().fullAddress());
            branchServiceImp.save(branch);
        }
    }

    public void loadDataFromCsv() {
        long startTime = System.currentTimeMillis();

        Map<String, Region> regionCache = new HashMap<>();
        Map<String, City> cityCache = new HashMap<>();
        Map<String, District> districtCache = new HashMap<>();
        Map<String, Street> streetCache = new HashMap<>();

        List<House> housesToSave = new ArrayList<>();

        try (InputStream is = getClass().getResourceAsStream("/streetBDtest.csv");
             BufferedReader br = new BufferedReader(new InputStreamReader(
                     Objects.<InputStream>requireNonNull(is),
                     "Windows-1251"))) {

            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(";", -1);

                if (columns.length < 8) continue;
                String regionName = columns[0].trim();
                String districtName = columns[2].trim();
                String cityName = columns[4].trim();
                String postalCode = columns[5].trim();
                String streetName = columns[6].trim();
                String houseNumbersRaw = columns[7].trim();

                if (regionName.isEmpty() || cityName.isEmpty() || districtName.isEmpty() || streetName.isEmpty()) {
                    continue;
                }

                Region region = regionCache.computeIfAbsent(regionName, key ->
                        regionService.getOrCreate(key).join()
                );

                String cityKey = cityName + ";" + region.getId();
                City city = cityCache.computeIfAbsent(cityKey, key ->
                        cityService.getByNameAndRegionOrCreate(cityName, region, postalCode)
                );

                String districtKey = districtName + ";" + city.getId();
                District district = districtCache.computeIfAbsent(districtKey, key ->
                        districtRepository.findByNameAndCity(districtName, city)
                                .orElseGet(() -> districtRepository.save(District.builder().name(districtName).city(city).build())
                                ));

                String streetKey = streetName + ";" + district.getId();
                Street street = streetCache.computeIfAbsent(streetKey, key ->
                        streetRepository.findByNameAndDistrict(streetName, district)
                                .orElseGet(() -> streetRepository.save(Street.builder().name(streetName).district(district).build()))
                );

                if (!houseNumbersRaw.isEmpty()) {
                    String[] houseNumbers = houseNumbersRaw.split(",");
                    for (String number : houseNumbers) {
                        String cleanNumber = number.trim();
                        if (!cleanNumber.isEmpty()) {
                            housesToSave.add(House.builder().number(cleanNumber).street(street).build());
                        }
                    }
                }
            }

            if (!housesToSave.isEmpty()) {
                System.out.println("Saving " + housesToSave.size() + " houses to the database...");
                houseRepository.saveAll(housesToSave);
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Finished loading data from CSV in " + (endTime - startTime) + " ms.");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading data from CSV file. Transaction will be rolled back.");
        }
    }

    public void loadAdmin() {
        Personal personal = new Personal();
        personal.setName("Роман");
        personal.setSurname("SpaceLab");
        personal.setLastName("Батькович");
        personal.setEmail("admin@gmail.com");
        personal.setPhoneNumber("+380123456789");
        personal.setPassword("admin");
        personal.setRole(Role.ADMIN);

        File avatarFile = new File("uploads/avatar.jpg");
        if (avatarFile.exists() && avatarFile.isFile()) {
            personal.setPathAvatar("uploads/avatar.jpg");
        }

        Branch branch = branchServiceImp.getBranchById(1L);
        personal.setBranches(Set.of(branch));

        Personal savedPersonal = personalService.save(personal);
        branch.setPersonals(Set.of(savedPersonal));
        branchServiceImp.save(branch);

        for (int j = 0; j < 5; j++) {
            Feedback feedback = new Feedback();
            feedback.setName(faker.name().firstName());
            feedback.setPhoneNumber(faker.phoneNumber().phoneNumber());
            feedback.setDescription(faker.lorem().sentence(10));

            feedbackService.save(feedback);
        }
        savedPersonal = personalService.save(personal);

        List<DocumentFeedback> documentFeedbacks = new ArrayList<>();
        File dir = new File("uploads/");
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".pdf")) {
                        DocumentFeedback feedback = new DocumentFeedback();
                        feedback.setName(file.getName());
                        feedback.setPathImage("uploads/" + file.getName());
                        documentFeedbacks.add(feedback);
                    }
                }

                if (!documentFeedbacks.isEmpty()) {
                    List<DocumentFeedback> savedDocuments = documentFeedbackService.saveAllDocumentFeedback(documentFeedbacks);
                    savedPersonal.setDocumentFeedbacks(savedDocuments);
                    personalService.save(savedPersonal);
                }
            }
        }
    }

    public void loadPersonal() {
        Faker faker = new Faker(new Locale("uk"));
        for (int i = 0; i < 100; i++) {
            Personal personal = new Personal();
            personal.setName(faker.name().firstName());
            personal.setSurname(faker.name().lastName());
            personal.setEmail(faker.internet().emailAddress());
            personal.setPhoneNumber(faker.phoneNumber().phoneNumber());
            personal.setPassword(faker.internet().password());
            personal.setRole(Role.USER);

            Personal savedPersonal = personalService.save(personal);

            for (int j = 0; j < 5; j++) {
                Feedback feedback = new Feedback();
                feedback.setName(savedPersonal.getName());
                feedback.setPhoneNumber(savedPersonal.getPhoneNumber());
                feedback.setDescription(faker.lorem().sentence(10));

                savedPersonal.getFeedBacks().add(feedback);
            }

            personalService.save(savedPersonal);
        }
    }

    public void loadFakeTopozone() {
        Faker faker = new Faker(new Locale("uk"));
        for (int i = 0; i < 10; i++) {
            Topozone topozone = new Topozone();
            topozone.setName(faker.address().streetName());
            topozoneService.save(topozone);
        }
    }

    private void loadBanner() {

        String[] nameForBanners =
                {"Квартиры от строителей",
                        "Квартиры от инвесторов",
                        "Вторичная недвижимость",
                        "Дома и участки",
                        "Коммерческое"};
        for (int i = 0; i < 5; i++) {
            Banner banner = new Banner();
            banner.setName(nameForBanners[i]);
            banner.setStatus(true);

            banner = bannerService.save(banner);

            ImageBanner imageBanner = new ImageBanner();
            imageBanner.setName(faker.file().fileName());
            imageBanner.setPriority(1);
            imageBanner.setPathImage("uploads/images.jpg");
            imageBanner.setBanner(banner);

            imageBannerService.save(imageBanner);

        }
    }

    private void loadResidentialLand() {

        List<Topozone> topozones = topozoneService.getAll();

        for (int i = 0; i < 100; i++) {
            ResidentialLand residentialLand = new ResidentialLand();
            List<Region> regions = regionRepository.findAll();
            Region region = getRandomEntity(regions);
            City city = getRandomEntity(region.getCities());
            District district = getRandomEntity(city.getDistricts());
            Street street = getRandomEntity(district.getStreets());
            House house = getRandomEntity(street.getHouses());
            residentialLand.setRegion(region);
            residentialLand.setCity(city);
            residentialLand.setDistrict(district);
            residentialLand.setStreet(street);
            residentialLand.setHouse(house);


            residentialLand.setTopozone(getRandomEntity(topozones));

            residentialLand.setOwnerFullName(faker.name().fullName());
            residentialLand.setPhoneNumber(faker.phoneNumber().cellPhone());
            residentialLand.setRealtor(realtorService.readById(faker.number().numberBetween(1, 10L)));
            residentialLand.setAdminComment(faker.lorem().paragraph());

            LocalDate date = faker.date().past(365, TimeUnit.DAYS)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            residentialLand.setDateOfCreating(date);
            residentialLand.setPhoneNumber(faker.phoneNumber().cellPhone());
            residentialLand.setAcquisitionDate(LocalDate.now());
            residentialLand.setOwnershipDoc(OwnershipDoc.OWNERSHIP_CERTIFICATE);
            residentialLand.setOwnershipDoc(getRandomEnum(OwnershipDoc.class));
            residentialLand.setImportantComment(faker.lorem().paragraph());
            residentialLand.setCadastralNumber(faker.number().digits(6));
            residentialLand.setLangPurpose(faker.lorem().sentence());

            ResidentialLandMain residentialLandMain = new ResidentialLandMain();
            residentialLandMain.setPublicationStatus(PublicationStatus.PUBLICATED);
            residentialLandMain.setObjectCode(faker.number().digits(6));
            residentialLandMain.setBranchName(faker.company().name());
            residentialLandMain.setPersonalName(faker.name().fullName());
            residentialLandMain.setLandmark(faker.lorem().sentence());
            residentialLandMain.setPrice(((double) faker.number().numberBetween(100000, 1000000)));
            residentialLandMain.setTypeProperty(TypeProperty.LAND);
            residentialLandMain.setLandAreaAcres((double) faker.number().numberBetween(1, 100));
            residentialLandMain.setFreePlotAreaAcres((double) faker.number().numberBetween(1, 100));
            residentialLandMain.setLandOwnership(true);
            residentialLandMain.setDesignatedUseOfLand(getRandomEnum(DesignatedUseOfLand.class));
            residentialLandMain.setHouseCount(faker.number().numberBetween(1, 100));
            residentialLandMain.setFloors(faker.number().numberBetween(1, 100));
            residentialLandMain.setRooms(faker.number().numberBetween(1, 100));
            residentialLandMain.setBedrooms(faker.number().numberBetween(1, 100));
            residentialLandMain.setCeilingHeight((double) faker.number().numberBetween(1, 100));
            residentialLandMain.setTotalArea((double) faker.number().numberBetween(1, 100));
            residentialLandMain.setLivingArea((double) faker.number().numberBetween(1, 100));
            residentialLandMain.setKitchenArea((double) faker.number().numberBetween(1, 100));
            residentialLandMain.setWallMaterial(faker.lorem().sentence());
            residentialLandMain.setConditionInterior(getRandomEnum(ConditionInterior.class));
            residentialLandMain.setConditionBuilding(getRandomEnum(ConditionBuilding.class));
            residentialLandMain.setKitchen(getRandomEnum(Kitchen.class));
            residentialLandMain.setBathroom(faker.number().numberBetween(1, 100));
            residentialLandMain.setGas(getRandomEnum(Gas.class));
            residentialLandMain.setWaterSupply(getRandomEnum(WaterSupply.class));
            residentialLandMain.setSewage(getRandomEnum(Sewage.class));
            residentialLandMain.setHeating(getRandomEnum(Heating.class));
            residentialLandMain.setStairs(getRandomEnum(Stairs.class));
            residentialLandMain.setRoofType(getRandomEnum(RoofType.class));
            residentialLandMain.setFloorType(getRandomEnum(FloorType.class));
            residentialLandMain.setTypeWindows(getRandomEnum(TypeWindows.class));
            residentialLandMain.setCarpentryCondition(getRandomEnum(CarpentryCondition.class));
            residentialLandMain.setEntranceDoor(getRandomEnum(EntranceDoor.class));
            residentialLandMain.setLastCommunication(LocalDate.now());
            residentialLandMain.setIsVnp(true);
            residentialLandMain.setVnpDate(faker.lorem().sentence());
            residentialLandMain.setSourceInformation(getRandomEnum(SourceInformation.class));
            residentialLandMain.setHasTrade(true);
            residentialLandMain.setHasExclusive(true);
            residentialLandMain.setUrgent(true);
            residentialLandMain.setIsFree(true);
            residentialLandMain.setIsOpenObject(true);
            residentialLandMain.setFromMediator(true);
            residentialLandMain.setDescription(faker.lorem().sentence());
            residentialLandMain.setAdvertisingHeadline(faker.lorem().sentence());
            residentialLandMain.setAdvertisingText(faker.lorem().sentence());
            residentialLandMain.setIsAdvertising(true);


            residentialLandMain.setResidentialLand(residentialLand);
            residentialLand.setResidentialLandMain(residentialLandMain);


            ResidentialLandFile residentialLandFile = new ResidentialLandFile();
            residentialLandFile.setName(faker.file().fileName());
            residentialLandFile.setFilePath("uploads/images.jpg");
            residentialLandFile.setResidentialLand(residentialLand);
            residentialLand.setResidentialLandFiles(List.of(residentialLandFile));

            ResidentialLandGalleryImage residentialLandGalleryImage = new ResidentialLandGalleryImage();
            residentialLandGalleryImage.setName(faker.file().fileName());
            residentialLandGalleryImage.setPathImage("uploads/images.jpg");
            residentialLandGalleryImage.setResidentialLand(residentialLand);
            residentialLand.setResidentialLandGalleryImages(List.of(residentialLandGalleryImage));

            residentialLandService.save(residentialLand);
        }

    }

    List<String> suffixes = List.of("Буд", "Інвест", "Девелопмент", "Строй", "Будгруп", "Пром");

    public void loadFakeBuilderProperties() {
        Faker faker = new Faker(new Locale("uk"));
        Random random = new Random();
        if (builderPropertyService.getAll().isEmpty()) {
            List<Region> regions = regionRepository.findAll();

            for (int i = 0; i < 25; i++) {
                Region region = getRandomEntity(regions);
                City city = getRandomEntity(region.getCities());
                District district = getRandomEntity(city.getDistricts());
                Street street = getRandomEntity(district.getStreets());
                House house = getRandomEntity(street.getHouses());
                BuilderProperty builder = new BuilderProperty();
                builder.setName(faker.company().name());
                builder.setRegion(region);
                builder.setCity(city);
                builder.setDistrict(district);
                builder.setBuildingCompany(getRandomEntity(buildingCompanyService.getAll()));
                builder.setStreet(street);
                builder.setHouse(house);

                builder.setHouseNumber(Integer.valueOf(faker.address().buildingNumber()));
                builder.setHouseSection(Integer.valueOf(String.valueOf(faker.number().numberBetween(1, 5))));
                builder.setTotalFloor(faker.number().numberBetween(5, 25));

                builder.setDeliveryDate(DeliveryDate.values()[random.nextInt(DeliveryDate.values().length)]);
                builder.setPhoneNumber(faker.phoneNumber().phoneNumber());

                builder.setPathToChessPlanFile("floor_plan_" + faker.file().fileName());
                builder.setPathToMortgageConditionsFile("mortgage_" + faker.file().fileName());
                builder.setPathToPriceFile("prices_" + faker.file().fileName());

                builder.setDescription(faker.lorem().paragraph());
                builder.setActionTitle(faker.lorem().sentence());
                builder.setActionDescription(faker.lorem().paragraph());
                builder.setIsAction(faker.bool().bool());

                builder.setDateOfCreating(LocalDate.now().minusDays(faker.number().numberBetween(0, 365)));

                builderPropertyService.save(builder);
            }
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

    public <T> T getRandomEntity(Collection<T> collection) {
        if (collection.isEmpty()) {
            return null;
        }
        int index = new Random().nextInt(collection.size());
        List<T> list = new ArrayList<>(collection);
        return list.get(index);
    }

    private <T extends Enum<T>> T getRandomEnum(Class<T> enumClass) {
        T[] enumConstants = enumClass.getEnumConstants();
        if (enumConstants == null || enumConstants.length == 0) {
            return null;
        }
        return enumConstants[new Random().nextInt(enumConstants.length)];
    }
}