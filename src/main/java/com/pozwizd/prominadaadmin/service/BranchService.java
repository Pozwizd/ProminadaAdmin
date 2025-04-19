package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.Branch;
import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.mapper.BranchMapper;
import com.pozwizd.prominadaadmin.models.branch.BranchResponse;
import com.pozwizd.prominadaadmin.repository.BranchRepository;
import com.pozwizd.prominadaadmin.repository.PersonalRepository;
import com.pozwizd.prominadaadmin.specification.BranchSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Сервис для управления филиалами (Branch).
 * Предоставляет методы для создания, чтения, обновления и удаления информации о филиалах,
 * а также для поиска и фильтрации филиалов по различным критериям.
 */
@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    /**
     * Получает список всех филиалов.
     * 
     * @return Список всех филиалов в системе
     */
    public List<Branch> findAll() {
        return branchRepository.findAll();
    }

        /**
     * Получает список всех филиалов.
     *
     * @return Список всех филиалов в системе
     */
    public List<BranchResponse> findAllResponse() {
        return branchMapper.toBranchResponse(branchRepository.findAll());
    }

    /**
     * Поиск филиала по ID.
     * 
     * @param id ID филиала
     * @return Optional с найденным филиалом или пустой Optional
     */
    public Optional<Branch> findById(Long id) {
        return branchRepository.findById(id);
    }



    /**
     * Поиск филиала по коду.
     * 
     * @param code Код филиала
     * @return Optional с найденным филиалом или пустой Optional
     */
    public Optional<Branch> findByCode(String code) {
        return branchRepository.findByCode(code);
    }

    /**
     * Поиск филиала по названию.
     * 
     * @param name Название филиала
     * @return Optional с найденным филиалом или пустой Optional
     */
    public Optional<Branch> findByName(String name) {
        return branchRepository.findByName(name);
    }

    /**
     * Поиск филиала по email.
     * 
     * @param email Email филиала
     * @return Optional с найденным филиалом или пустой Optional
     */
    public Optional<Branch> findByEmail(String email) {
        return branchRepository.findByEmail(email);
    }

    /**
     * Сохраняет информацию о филиале.
     * 
     * @param branch Данные филиала для сохранения
     * @return Сохраненный филиал с обновленными данными
     */
    @Transactional
    public Branch save(Branch branch) {
        return branchRepository.save(branch);
    }

    /**
     * Удаляет филиал по ID.
     * 
     * @param id ID филиала для удаления
     */
    @Transactional
    public void deleteById(Long id) {
        branchRepository.deleteById(id);
    }

    /**
     * Получает постраничный список филиалов с возможностью фильтрации.
     * 
     * @param page Номер страницы
     * @param size Размер страницы
     * @param code Код филиала для фильтрации
     * @param name Название филиала для фильтрации
     * @param address Адрес филиала для фильтрации
     * @param phoneNumber Телефон филиала для фильтрации
     * @param email Email филиала для фильтрации
     * @return Страница с данными филиалов, соответствующих критериям фильтрации
     */
    public Page<BranchResponse> getPageableBranch(int page, Integer size,
                                                 String code,
                                                 String name,
                                                 String address,
                                                 String phoneNumber,
                                                 String email) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return branchMapper.toBranchResponse(branchRepository.findAll(BranchSpecification.search(code,
                name,
                address,
                phoneNumber,
                email),
                pageRequest));
    }

    /**
     * Получает филиал по ID.
     * 
     * @param id ID филиала
     * @return Данные филиала
     * @throws NoSuchElementException если филиал не найден
     */
    public Branch getBranchById(Long id) {
        return branchRepository.findById(id).orElseThrow();
    }
}