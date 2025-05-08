package com.pozwizd.prominadaadmin.repository;

import com.pozwizd.prominadaadmin.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью Branch (Филиал).
 * Предоставляет методы для выполнения CRUD-операций и сложных запросов с использованием спецификаций.
 */
@Repository
public interface BranchRepository extends JpaRepository<Branch, Long>, JpaSpecificationExecutor<Branch> {
    
    /**
     * Поиск филиала по коду.
     * 
     * @param code Код филиала
     * @return Optional с найденным филиалом или пустой Optional
     */
    Optional<Branch> findByCode(String code);
    
    /**
     * Поиск филиала по имени.
     * 
     * @param name Название филиала
     * @return Optional с найденным филиалом или пустой Optional
     */
    Optional<Branch> findByName(String name);
    
    /**
     * Поиск филиала по email.
     * 
     * @param email Email филиала
     * @return Optional с найденным филиалом или пустой Optional
     */
    Optional<Branch> findByEmail(String email);
}