package com.pozwizd.prominadaadmin.specification;


import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryProperty;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyMain;
import com.pozwizd.prominadaadmin.models.filter.PropertiesFilter;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface SecondaryPropertySpecification {

    static Specification<SecondaryProperty> search(PropertiesFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<SecondaryProperty, SecondaryPropertyMain> mainJoin = root.join("secondaryPropertyMain", JoinType.LEFT);

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
                                           Root<SecondaryProperty> root) {
        if (filter.getRegionIds() != null && !filter.getRegionIds().isEmpty()) {
            predicates.add(root.get("region").get("id").in(filter.getRegionIds()));
        }
    }

    private static void addCityPredicate(List<Predicate> predicates, PropertiesFilter filter,
                                         Root<SecondaryProperty> root) {
        if (filter.getCityIds() != null && !filter.getCityIds().isEmpty()) {
            predicates.add(root.get("city").get("id").in(filter.getCityIds()));
        }
    }

    private static void addDistrictPredicate(List<Predicate> predicates, PropertiesFilter filter,
                                             Root<SecondaryProperty> root) {
        if (filter.getDistrictIds() != null && !filter.getDistrictIds().isEmpty()) {
            predicates.add(root.get("district").get("id").in(filter.getDistrictIds()));
        }
    }

    private static void addTopozonePredicate(List<Predicate> predicates, PropertiesFilter filter,
                                             Root<SecondaryProperty> root) {
        if (filter.getTopozoneIds() != null && !filter.getTopozoneIds().isEmpty()) {
            predicates.add(root.get("topozone").get("id").in(filter.getTopozoneIds()));
        }
    }

    private static void addStreetPredicate(List<Predicate> predicates, PropertiesFilter filter,
                                           Root<SecondaryProperty> root,
                                           CriteriaBuilder criteriaBuilder) {
        if (filter.getStreet() != null && !filter.getStreet().trim().isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("street").get("name")),
                    "%" + filter.getStreet().toLowerCase() + "%"
            ));
        }
    }

    private static void addLastCommunicationPredicate(List<Predicate> predicates, PropertiesFilter filter,
                                                      Join<SecondaryProperty, SecondaryPropertyMain> mainJoin,
                                                      CriteriaBuilder criteriaBuilder) {
        if (filter.getLastCommunication() != null) {
            LocalDate dateThreshold = LocalDate.now().minusDays(filter.getLastCommunication());
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    mainJoin.get("lastCommunication"), dateThreshold
            ));
        }
    }

    private static void addRoomPredicate(List<Predicate> predicates, PropertiesFilter filter,
                                         Join<SecondaryProperty, SecondaryPropertyMain> mainJoin,
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
                                          Join<SecondaryProperty, SecondaryPropertyMain> mainJoin,
                                          CriteriaBuilder criteriaBuilder) {
        if (filter.getFloorsFrom() != null && !filter.getFloorsFrom().trim().isEmpty()) {
            try {
                int floorsFrom = Integer.parseInt(filter.getFloorsFrom());
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(mainJoin.get("floor"), floorsFrom));
            } catch (NumberFormatException ignored) {
            }
        }
        if (filter.getFloorsTo() != null && !filter.getFloorsTo().trim().isEmpty()) {
            try {
                int floorsTo = Integer.parseInt(filter.getFloorsTo());
                predicates.add(criteriaBuilder.lessThanOrEqualTo(mainJoin.get("floor"), floorsTo));
            } catch (NumberFormatException ignored) {
            }
        }
    }

    private static void addPricePredicate(List<Predicate> predicates, PropertiesFilter filter,
                                          Join<SecondaryProperty, SecondaryPropertyMain> mainJoin,
                                          CriteriaBuilder criteriaBuilder) {
        if (filter.getPriceFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(mainJoin.get("price"), filter.getPriceFrom()));
        }
        if (filter.getPriceTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(mainJoin.get("price"), filter.getPriceTo()));
        }
    }

    private static void addTotalAreaPredicate(List<Predicate> predicates, PropertiesFilter filter,
                                              Join<SecondaryProperty, SecondaryPropertyMain> mainJoin,
                                              CriteriaBuilder criteriaBuilder) {
        if (filter.getTotalAreaFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(mainJoin.get("totalArea"), filter.getTotalAreaFrom()));
        }
        if (filter.getTotalAreaTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(mainJoin.get("totalArea"), filter.getTotalAreaTo()));
        }
    }

    private static void addLivingAreaPredicate(List<Predicate> predicates, PropertiesFilter filter,
                                               Join<SecondaryProperty, SecondaryPropertyMain> mainJoin,
                                               CriteriaBuilder criteriaBuilder) {
        if (filter.getLivingAreaFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(mainJoin.get("livingArea"), filter.getLivingAreaFrom()));
        }
        if (filter.getLivingAreaTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(mainJoin.get("livingArea"), filter.getLivingAreaTo()));
        }
    }

    // Специализированные запросы

    public static Specification<SecondaryProperty> hasPhotos() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNotEmpty(root.get("secondaryPropertyGalleryImages"));
    }

    public static Specification<SecondaryProperty> isAdvertising() {
        return (root, query, criteriaBuilder) -> {
            Join<SecondaryProperty, SecondaryPropertyMain> mainJoin = root.join("secondaryPropertyMain", JoinType.LEFT);
            return criteriaBuilder.equal(mainJoin.get("isAdvertising"), true);
        };
    }

    public static Specification<SecondaryProperty> byRealtor(Long realtorId) {
        return (root, query, criteriaBuilder) -> {
            Join<SecondaryProperty, SecondaryPropertyMain> mainJoin = root.join("secondaryPropertyMain", JoinType.LEFT);
            return criteriaBuilder.equal(mainJoin.get("realtor").get("id"), realtorId);
        };
    }

    public static Specification<SecondaryProperty> createdAfter(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfCreating"), date);
    }
}
