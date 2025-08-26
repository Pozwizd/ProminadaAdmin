package com.pozwizd.prominadaadmin.specification;

import com.pozwizd.prominadaadmin.entity.location.District;
import com.pozwizd.prominadaadmin.entity.location.Topozone;
import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLandMain;
import com.pozwizd.prominadaadmin.models.filter.PropertiesFilter;
import com.pozwizd.prominadaadmin.models.filter.ResidentialLandFilterRequestFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import jakarta.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.util.StringUtils;


public interface ResidentialLandSpecification {

    static Specification<ResidentialLand> search(PropertiesFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<ResidentialLand, ResidentialLandMain> mainJoin = root.join("residentialLandMain", JoinType.LEFT);

            addRegionPredicate(predicates, filter, root);
            addCityPredicate(predicates, filter, root);
            addDistrictPredicate(predicates, filter, root);
            addTopozonePredicate(predicates, filter, root);
            addStreetPredicate(predicates, filter, root, criteriaBuilder);
            addLastCommunicationPredicate(predicates, filter, mainJoin, criteriaBuilder);
            addRoomPredicate(predicates, filter, mainJoin, criteriaBuilder);
            addFloorPredicate(predicates, filter, mainJoin, criteriaBuilder);
            addPricePredicate(predicates, filter, mainJoin, criteriaBuilder);
            addTotalAreaPredicate(predicates, filter, mainJoin, criteriaBuilder);
            addLivingAreaPredicate(predicates, filter, mainJoin, criteriaBuilder);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addRegionPredicate(List<Predicate> predicates, PropertiesFilter filter,
                                           Root<ResidentialLand> root) {
        if (filter.getRegionIds() != null && !filter.getRegionIds().isEmpty()) {
            predicates.add(root.get("region").get("id").in(filter.getRegionIds()));
        }
    }

    private static void addCityPredicate(List<Predicate> predicates, PropertiesFilter filter,
                                         Root<ResidentialLand> root) {
        if (filter.getCityIds() != null && !filter.getCityIds().isEmpty()) {
            predicates.add(root.get("city").get("id").in(filter.getCityIds()));
        }
    }

    private static void addDistrictPredicate(List<Predicate> predicates, PropertiesFilter filter,
                                             Root<ResidentialLand> root) {
        if (filter.getDistrictIds() != null && !filter.getDistrictIds().isEmpty()) {
            predicates.add(root.get("district").get("id").in(filter.getDistrictIds()));
        }
    }

    private static void addTopozonePredicate(List<Predicate> predicates, PropertiesFilter filter,
                                             Root<ResidentialLand> root) {
        if (filter.getTopozoneIds() != null && !filter.getTopozoneIds().isEmpty()) {
            predicates.add(root.get("topozone").get("id").in(filter.getTopozoneIds()));
        }
    }

    private static void addStreetPredicate(List<Predicate> predicates, PropertiesFilter filter,
                                           Root<ResidentialLand> root,
                                           CriteriaBuilder criteriaBuilder) {
        if (filter.getStreet() != null && !filter.getStreet().trim().isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("street").get("name")),
                    "%" + filter.getStreet().toLowerCase() + "%"
            ));
        }
    }

    private static void addLastCommunicationPredicate(List<Predicate> predicates, PropertiesFilter filter,
                                                      Join<ResidentialLand, ResidentialLandMain> mainJoin,
                                                      CriteriaBuilder criteriaBuilder) {
        if (filter.getLastCommunication() != null) {
            LocalDate dateThreshold = LocalDate.now().minusDays(filter.getLastCommunication());
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    mainJoin.get("lastCommunication"), dateThreshold
            ));
        }
    }

    private static void addRoomPredicate(List<Predicate> predicates, PropertiesFilter filter,
                                         Join<ResidentialLand, ResidentialLandMain> mainJoin,
                                         CriteriaBuilder criteriaBuilder) {
        Integer minRooms = null;
        if (Boolean.TRUE.equals(filter.getCountRoom1())) minRooms = 1;
        if (Boolean.TRUE.equals(filter.getCountRoom2()) && (minRooms == null || minRooms > 2)) minRooms = 2;
        if (Boolean.TRUE.equals(filter.getCountRoom3()) && (minRooms == null || minRooms > 3)) minRooms = 3;
        if (Boolean.TRUE.equals(filter.getCountRoom4()) && (minRooms == null || minRooms > 4)) minRooms = 4;

        if (minRooms != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(mainJoin.get("rooms"), minRooms));
        }
    }

    private static void addFloorPredicate(List<Predicate> predicates, PropertiesFilter filter,
                                          Join<ResidentialLand, ResidentialLandMain> mainJoin,
                                          CriteriaBuilder criteriaBuilder) {
        if (filter.getFloorsFrom() != null && !filter.getFloorsFrom().trim().isEmpty()) {
            try {
                int floorsFrom = Integer.parseInt(filter.getFloorsFrom());
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(mainJoin.get("floors"), floorsFrom));
            } catch (NumberFormatException ignored) {
            }
        }
        if (filter.getFloorsTo() != null && !filter.getFloorsTo().trim().isEmpty()) {
            try {
                int floorsTo = Integer.parseInt(filter.getFloorsTo());
                predicates.add(criteriaBuilder.lessThanOrEqualTo(mainJoin.get("floors"), floorsTo));
            } catch (NumberFormatException ignored) {
            }
        }
    }

    private static void addPricePredicate(List<Predicate> predicates, PropertiesFilter filter,
                                          Join<ResidentialLand, ResidentialLandMain> mainJoin,
                                          CriteriaBuilder criteriaBuilder) {
        if (filter.getPriceFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(mainJoin.get("price"), filter.getPriceFrom()));
        }
        if (filter.getPriceTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(mainJoin.get("price"), filter.getPriceTo()));
        }
    }

    private static void addTotalAreaPredicate(List<Predicate> predicates, PropertiesFilter filter,
                                              Join<ResidentialLand, ResidentialLandMain> mainJoin,
                                              CriteriaBuilder criteriaBuilder) {
        if (filter.getTotalAreaFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(mainJoin.get("totalArea"), filter.getTotalAreaFrom()));
        }
        if (filter.getTotalAreaTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(mainJoin.get("totalArea"), filter.getTotalAreaTo()));
        }
    }

    private static void addLivingAreaPredicate(List<Predicate> predicates, PropertiesFilter filter,
                                               Join<ResidentialLand, ResidentialLandMain> mainJoin,
                                               CriteriaBuilder criteriaBuilder) {
        if (filter.getLivingAreaFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(mainJoin.get("livingArea"), filter.getLivingAreaFrom()));
        }
        if (filter.getLivingAreaTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(mainJoin.get("livingArea"), filter.getLivingAreaTo()));
        }
    }

    // Специализированные запросы

    public static Specification<ResidentialLand> hasPhotos() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNotEmpty(root.get("residentialLandGalleryImages"));
    }

    public static Specification<ResidentialLand> isAdvertising() {
        return (root, query, criteriaBuilder) -> {
            Join<ResidentialLand, ResidentialLandMain> mainJoin = root.join("residentialLandMain", JoinType.LEFT);
            return criteriaBuilder.equal(mainJoin.get("isAdvertising"), true);
        };
    }

    public static Specification<ResidentialLand> byRealtor(Long realtorId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("realtor").get("id"), realtorId);
    }

    public static Specification<ResidentialLand> createdAfter(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfCreating"), date);
    }

    static Specification<ResidentialLand> searchForDataTables(ResidentialLandFilterRequestFilter filterRequest) {

        return (Root<ResidentialLand> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<ResidentialLand, ResidentialLandMain> mainJoin = null;

            if (StringUtils.hasText(filterRequest.getStreet())) {
                predicates.add(cb.like(cb.lower(root.get("street")), "%" + filterRequest.getStreet().toLowerCase().trim() + "%"));
            }

            if (filterRequest.getDistrictIds() != null && !filterRequest.getDistrictIds().isEmpty()) {
                Join<ResidentialLand, District> districtJoin = root.join("distinct", JoinType.INNER); // "distinct" - имя поля связи в ResidentialLand
                predicates.add(districtJoin.get("id").in(filterRequest.getDistrictIds()));
            }

            if (filterRequest.getTopozoneIds() != null && !filterRequest.getTopozoneIds().isEmpty()) {
                Join<ResidentialLand, Topozone> topozoneJoin = root.join("topozone", JoinType.INNER);
                predicates.add(topozoneJoin.get("id").in(filterRequest.getTopozoneIds()));
            }

            boolean needsMainJoin =
                    (filterRequest.getLastCommunication() != null && filterRequest.getLastCommunication() > 0) ||
                            (Boolean.TRUE.equals(filterRequest.getCountRoom1())) ||
                            (Boolean.TRUE.equals(filterRequest.getCountRoom2())) ||
                            (Boolean.TRUE.equals(filterRequest.getCountRoom3())) ||
                            (Boolean.TRUE.equals(filterRequest.getCountRoom4())) ||
                            (StringUtils.hasText(filterRequest.getFloorsFrom())) ||
                            (StringUtils.hasText(filterRequest.getFloorsTo())) ||
                            (filterRequest.getPriceFrom() != null) ||
                            (filterRequest.getPriceTo() != null) ||
                            (filterRequest.getTotalAreaFrom() != null) ||
                            (filterRequest.getTotalAreaTo() != null) ||
                            (filterRequest.getLivingAreaFrom() != null) ||
                            (filterRequest.getLivingAreaTo() != null);

            if (needsMainJoin) {
                mainJoin = root.join("residentialLandMain", JoinType.INNER);
            }

            if (mainJoin != null) {

                if (filterRequest.getLastCommunication() != null && filterRequest.getLastCommunication() > 0) {
                    LocalDate dateLimit = LocalDate.now().minusDays(filterRequest.getLastCommunication());
                    predicates.add(cb.greaterThanOrEqualTo(mainJoin.get("lastCommunication"), dateLimit)); // Поле "lastCommunication" в ResidentialLandMain
                }

                List<Predicate> roomPredicates = new ArrayList<>();
                if (Boolean.TRUE.equals(filterRequest.getCountRoom1())) {
                    roomPredicates.add(cb.equal(mainJoin.get("rooms"), 1));
                }
                if (Boolean.TRUE.equals(filterRequest.getCountRoom2())) {
                    roomPredicates.add(cb.equal(mainJoin.get("rooms"), 2));
                }
                if (Boolean.TRUE.equals(filterRequest.getCountRoom3())) {
                    roomPredicates.add(cb.equal(mainJoin.get("rooms"), 3));
                }
                if (Boolean.TRUE.equals(filterRequest.getCountRoom4())) {
                    roomPredicates.add(cb.greaterThanOrEqualTo(mainJoin.get("rooms"), 4));
                }

                if (!roomPredicates.isEmpty()) {
                    predicates.add(cb.or(roomPredicates.toArray(new Predicate[0])));
                }

                if (StringUtils.hasText(filterRequest.getFloorsFrom())) {
                    try {
                        int floorsFrom = Integer.parseInt(filterRequest.getFloorsFrom().trim());
                        predicates.add(cb.greaterThanOrEqualTo(mainJoin.get("floors"), floorsFrom));
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка парсинга floorsFrom: " + filterRequest.getFloorsFrom() + " - " + e.getMessage());
                    }
                }

                if (StringUtils.hasText(filterRequest.getFloorsTo())) {
                    try {
                        int floorsTo = Integer.parseInt(filterRequest.getFloorsTo().trim());
                        predicates.add(cb.lessThanOrEqualTo(mainJoin.get("floors"), floorsTo));
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка парсинга floorsTo: " + filterRequest.getFloorsTo() + " - " + e.getMessage());
                    }
                }

                if (filterRequest.getPriceFrom() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(mainJoin.get("price"), filterRequest.getPriceFrom())); // Поле "price"
                }

                if (filterRequest.getPriceTo() != null) {
                    predicates.add(cb.lessThanOrEqualTo(mainJoin.get("price"), filterRequest.getPriceTo()));
                }

                if (filterRequest.getTotalAreaFrom() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(mainJoin.get("totalArea"), filterRequest.getTotalAreaFrom())); // Поле "totalArea"
                }

                if (filterRequest.getTotalAreaTo() != null) {
                    predicates.add(cb.lessThanOrEqualTo(mainJoin.get("totalArea"), filterRequest.getTotalAreaTo()));
                }

                if (filterRequest.getLivingAreaFrom() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(mainJoin.get("livingArea"), filterRequest.getLivingAreaFrom())); // Поле "livingArea"
                }

                if (filterRequest.getLivingAreaTo() != null) {
                    predicates.add(cb.lessThanOrEqualTo(mainJoin.get("livingArea"), filterRequest.getLivingAreaTo()));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}