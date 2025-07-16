package com.pozwizd.prominadaadmin.config;

import com.pozwizd.prominadaadmin.entity.*;
import com.pozwizd.prominadaadmin.entity.location.*;
import com.pozwizd.prominadaadmin.entity.property.BuildingCompany;
import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLandFile;
import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLandGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLandMain;
import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty;
import com.pozwizd.prominadaadmin.entity.property.enums.*;
import com.pozwizd.prominadaadmin.repository.secondary.*;
import com.pozwizd.prominadaadmin.service.*;
import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.entity.Role; // Assuming Role enum exists here
import com.pozwizd.prominadaadmin.service.location.CityService;
import com.pozwizd.prominadaadmin.service.location.DistrictService;
import com.pozwizd.prominadaadmin.service.location.RegionService;
import com.pozwizd.prominadaadmin.service.location.TopozoneService;
import com.pozwizd.prominadaadmin.service.serviceImp.*;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;
import java.io.File;

@Component
@RequiredArgsConstructor
public class DataLoader {
    private final Faker faker;

    private final PersonalService personalService;
    private final BuilderPropertyService builderPropertyService;
    private final BuildingCompanyService buildingCompanyService;
    private final FeedbackService feedbackService;
    private final BranchServiceImp branchServiceImp;
    private final DocumentFeedbackService documentFeedbackService;
    private final BannerService bannerService;
    private final ImageBannerService imageBannerService;
    private final ResidentialLandService residentialLandService;
    private final RealtorService realtorService;

    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final StreetRepository streetRepository;
    private final HouseRepository houseRepository;


    private final RegionService regionService;
    private final CityService cityService;


    private final DistrictService districtService;
    private final TopozoneService topozoneService;


    private final FileService fileService;

    @EventListener(ApplicationReadyEvent.class)
    public void loadEntity() {
        loadDataFromCsv();
        loadBranch();
        loadAdmin();
        loadPersonal();
        loadBanner();
        loadFakeTopozone();
        loadResidentialLand();
        loadFakeBuilderProperties();
        loadFakeBuildingCompany();
        loadRealtor();
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


    private void loadRealtor() {

        for (int i = 0; i < 10; i++) {

            Realtor realtor = realtorService
                    .create(Realtor
                            .builder()
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
            branch.setCode(faker.code().asin());
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

                if (columns.length < 8) {
                    continue;
                }
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
            residentialLand.setHouseNumber(faker.number().numberBetween(1, 100));
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


            residentialLand.setPhoneNumber(faker.phoneNumber().cellPhone());
            residentialLand.setAcquisitionDate(LocalDate.now());
            residentialLand.setOwnershipDoc(OwnershipDoc.OWNERSHIP_CERTIFICATE);
            residentialLand.setOwnershipDoc(getRandomEnum(OwnershipDoc.class));
            residentialLand.setImportantComment(faker.lorem().sentence());
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
            for (int i = 0; i < 25; i++) {
                BuilderProperty builder = new BuilderProperty();
                builder.setName(faker.company().name());
                Region region = getRandomEntity(regionService.getRegions().join());
                builder.setRegion(region);
                City city = getRandomEntity(cityService.getAllByRegDistrictId(region.getId()));


                builder.setBuildingCompany(getRandomEntity(buildingCompanyService.getAll()));

                builder.setStreet(faker.address().streetName());
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

}