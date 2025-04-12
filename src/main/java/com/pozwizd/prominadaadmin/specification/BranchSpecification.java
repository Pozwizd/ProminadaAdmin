package com.pozwizd.prominadaadmin.specification;

import com.pozwizd.prominadaadmin.entity.Branch;
import org.springframework.data.jpa.domain.Specification;

/**
 * Спецификация для фильтрации филиалов (Branch).
 * Предоставляет методы для создания спецификаций для поиска филиалов по различным критериям.
 */
public class BranchSpecification {

    /**
     * Создает спецификацию для поиска филиалов по различным критериям.
     * 
     * @param code Код филиала для фильтрации
     * @param name Название филиала для фильтрации
     * @param address Адрес филиала для фильтрации
     * @param phoneNumber Телефон филиала для фильтрации
     * @param email Email филиала для фильтрации
     * @return Спецификация для поиска филиалов
     */
    public static Specification<Branch> search(String code, String name, String address, String phoneNumber, String email) {
        return Specification.where(codeContains(code))
                .and(nameContains(name))
                .and(addressContains(address))
                .and(phoneNumberContains(phoneNumber))
                .and(emailContains(email));
    }

    /**
     * Создает спецификацию для поиска филиалов по коду.
     * 
     * @param code Код филиала
     * @return Спецификация для поиска филиалов по коду
     */
    private static Specification<Branch> codeContains(String code) {
        return (root, query, criteriaBuilder) -> {
            if (code == null || code.isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("code")), "%" + code.toLowerCase() + "%");
        };
    }

    /**
     * Создает спецификацию для поиска филиалов по названию.
     * 
     * @param name Название филиала
     * @return Спецификация для поиска филиалов по названию
     */
    private static Specification<Branch> nameContains(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    /**
     * Создает спецификацию для поиска филиалов по адресу.
     * 
     * @param address Адрес филиала
     * @return Спецификация для поиска филиалов по адресу
     */
    private static Specification<Branch> addressContains(String address) {
        return (root, query, criteriaBuilder) -> {
            if (address == null || address.isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + address.toLowerCase() + "%");
        };
    }

    /**
     * Создает спецификацию для поиска филиалов по телефону.
     * 
     * @param phoneNumber Телефон филиала
     * @return Спецификация для поиска филиалов по телефону
     */
    private static Specification<Branch> phoneNumberContains(String phoneNumber) {
        return (root, query, criteriaBuilder) -> {
            if (phoneNumber == null || phoneNumber.isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), "%" + phoneNumber.toLowerCase() + "%");
        };
    }

    /**
     * Создает спецификацию для поиска филиалов по email.
     * 
     * @param email Email филиала
     * @return Спецификация для поиска филиалов по email
     */
    private static Specification<Branch> emailContains(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null || email.isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
        };
    }
}