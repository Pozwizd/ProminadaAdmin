package com.pozwizd.prominadaadmin.specification;

import com.pozwizd.prominadaadmin.entity.location.District;
import com.pozwizd.prominadaadmin.entity.location.Topozone;
import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLandMain;
import com.pozwizd.prominadaadmin.filter.ResidentialLandFilterRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import jakarta.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.util.StringUtils;


public interface ResidentialLandSpecification {

    static Specification<ResidentialLand> search(ResidentialLandFilterRequest filterRequest) {

        return (Root<ResidentialLand> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<ResidentialLand, ResidentialLandMain> mainJoin = null; // Инициализируем как null

            // --- Фильтры по полям самой сущности ResidentialLand ---

            // Фильтр по улице (поиск по подстроке без учета регистра)
            if (StringUtils.hasText(filterRequest.getStreet())) { // Более современная проверка строки
                predicates.add(cb.like(cb.lower(root.get("street")), "%" + filterRequest.getStreet().toLowerCase().trim() + "%"));
            }

            // Фильтр по ID районов
            if (filterRequest.getDistrictIds() != null && !filterRequest.getDistrictIds().isEmpty()) {
                Join<ResidentialLand, District> districtJoin = root.join("distinct", JoinType.INNER); // "distinct" - имя поля связи в ResidentialLand
                predicates.add(districtJoin.get("id").in(filterRequest.getDistrictIds()));
            }

            // Фильтр по ID топозон
            if (filterRequest.getTopozoneIds() != null && !filterRequest.getTopozoneIds().isEmpty()) {
                Join<ResidentialLand, Topozone> topozoneJoin = root.join("topozone", JoinType.INNER);
                predicates.add(topozoneJoin.get("id").in(filterRequest.getTopozoneIds()));
            }

            // --- Проверка необходимости присоединения ResidentialLandMain ---
            // Join будет выполнен, только если хотя бы один из фильтров по полям ResidentialLandMain активен
            boolean needsMainJoin =
                    (filterRequest.getLastCommunication() != null && filterRequest.getLastCommunication() > 0) ||
                            (Boolean.TRUE.equals(filterRequest.getCountRoom1())) || // Используем Boolean.TRUE.equals для безопасности с null
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
                mainJoin = root.join("residentialLandMain", JoinType.INNER); // Предполагаем, что поле называется "residentialLandMain"
            }

            // --- Фильтры по полям сущности ResidentialLandMain (если mainJoin был создан) ---
            if (mainJoin != null) {

                // Фильтр по дате последней коммуникации (за последние N дней)
                if (filterRequest.getLastCommunication() != null && filterRequest.getLastCommunication() > 0) {
                    LocalDate dateLimit = LocalDate.now().minusDays(filterRequest.getLastCommunication());
                    predicates.add(cb.greaterThanOrEqualTo(mainJoin.get("lastCommunication"), dateLimit)); // Поле "lastCommunication" в ResidentialLandMain
                }

                // Фильтр по количеству комнат
                List<Predicate> roomPredicates = new ArrayList<>();
                if (Boolean.TRUE.equals(filterRequest.getCountRoom1())) {
                    roomPredicates.add(cb.equal(mainJoin.get("rooms"), 1)); // Поле "rooms" в ResidentialLandMain
                }
                if (Boolean.TRUE.equals(filterRequest.getCountRoom2())) {
                    roomPredicates.add(cb.equal(mainJoin.get("rooms"), 2));
                }
                if (Boolean.TRUE.equals(filterRequest.getCountRoom3())) {
                    roomPredicates.add(cb.equal(mainJoin.get("rooms"), 3));
                }
                if (Boolean.TRUE.equals(filterRequest.getCountRoom4())) {
                    // Предполагается, что countRoom4 означает "4 или более комнат"
                    roomPredicates.add(cb.greaterThanOrEqualTo(mainJoin.get("rooms"), 4));
                }

                if (!roomPredicates.isEmpty()) {
                    predicates.add(cb.or(roomPredicates.toArray(new Predicate[0])));
                }

                // Фильтр по этажности (от)
                if (StringUtils.hasText(filterRequest.getFloorsFrom())) {
                    try {
                        int floorsFrom = Integer.parseInt(filterRequest.getFloorsFrom().trim());
                        predicates.add(cb.greaterThanOrEqualTo(mainJoin.get("floors"), floorsFrom)); // Поле "floors" в ResidentialLandMain
                    } catch (NumberFormatException e) {
                        // Логирование или обработка ошибки, если строка не является числом
                        System.err.println("Ошибка парсинга floorsFrom: " + filterRequest.getFloorsFrom() + " - " + e.getMessage());
                    }
                }

                // Фильтр по этажности (до)
                if (StringUtils.hasText(filterRequest.getFloorsTo())) {
                    try {
                        int floorsTo = Integer.parseInt(filterRequest.getFloorsTo().trim());
                        predicates.add(cb.lessThanOrEqualTo(mainJoin.get("floors"), floorsTo));
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка парсинга floorsTo: " + filterRequest.getFloorsTo() + " - " + e.getMessage());
                    }
                }

                // Фильтр по цене (от)
                if (filterRequest.getPriceFrom() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(mainJoin.get("price"), filterRequest.getPriceFrom())); // Поле "price"
                }
                // Фильтр по цене (до)
                if (filterRequest.getPriceTo() != null) {
                    predicates.add(cb.lessThanOrEqualTo(mainJoin.get("price"), filterRequest.getPriceTo()));
                }

                // Фильтр по общей площади (от)
                if (filterRequest.getTotalAreaFrom() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(mainJoin.get("totalArea"), filterRequest.getTotalAreaFrom())); // Поле "totalArea"
                }
                // Фильтр по общей площади (до)
                if (filterRequest.getTotalAreaTo() != null) {
                    predicates.add(cb.lessThanOrEqualTo(mainJoin.get("totalArea"), filterRequest.getTotalAreaTo()));
                }

                // Фильтр по жилой площади (от)
                if (filterRequest.getLivingAreaFrom() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(mainJoin.get("livingArea"), filterRequest.getLivingAreaFrom())); // Поле "livingArea"
                }
                // Фильтр по жилой площади (до)
                if (filterRequest.getLivingAreaTo() != null) {
                    predicates.add(cb.lessThanOrEqualTo(mainJoin.get("livingArea"), filterRequest.getLivingAreaTo()));
                }
            }

            // Предотвращение дубликатов, если есть связи @OneToMany, которые могут их вызывать.
            // Для данных связей (District, Topozone, ResidentialLandMain - обычно OneToOne или ManyToOne к корневой сущности)
            // distinct обычно не требуется, если только нет сложных сценариев или других ManyToMany/OneToMany связей.
            // query.distinct(true); // Раскомментируйте, если наблюдаются дубликаты результатов.

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}