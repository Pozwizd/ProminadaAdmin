package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.Branch;
import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.entity.Role;
import com.pozwizd.prominadaadmin.models.personal.PersonalRequest;
import com.pozwizd.prominadaadmin.models.personal.PersonalResponse;
import com.pozwizd.prominadaadmin.models.personal.PersonalTableResponse;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {FeedbackMapper.class, DocumentFeedbackMapper.class, BranchMapper.class})
public interface PersonalMapper {
    Personal toEntity(PersonalTableResponse personalTableResponse);

    PersonalTableResponse toPersonalTableResponse(Personal personal);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Personal partialUpdate(PersonalTableResponse personalTableResponse, @MappingTarget Personal personal);

    default Page<PersonalTableResponse> toPersonalTableResponse(Page<Personal> personalPage) {
        return personalPage.map(this::toPersonalTableResponse);
    }

    /**
     * Преобразует сущность Personal в PersonalResponse,
     * преобразуя поле 'role' из перечисления в строковое представление.
     *
     * @param personal Сущность Personal для преобразования.
     * @return Объект PersonalResponse с преобразованными данными.
     */
    @Mapping(target = "role", expression = "java(personal.getRole().toString())")
    @Mapping(source = "documentFeedbacks", target = "documentFeedbackResponses")
    @Mapping(source = "feedBacks", target = "feedbackResponses")
    @Mapping(source = "branches", target = "idsBranches")
    PersonalResponse toPersonalProfileResponse(Personal personal);

    /**
     * Вспомогательный метод, который MapStruct будет использовать для преобразования
     * каждого объекта Branch из списка personal.getBranches() в его ID.
     *
     * @param branch объект сущности Branch
     * @return ID этого бранча
     */
    default Long branchToLong(Branch branch) {
        return branch != null ? branch.getId() : null;
    }
    /**
     * Преобразует объект PersonalResponse в сущность Personal.
     * Поля 'role' и 'password' игнорируются при маппинге,
     * так как они могут быть установлены отдельно или не требоваться для создания сущности.
     *
     * @param personalResponse Объект PersonalResponse для преобразования.
     * @return Сущность Personal с данными из PersonalResponse.
     */
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    Personal toEntity(PersonalResponse personalResponse);

    /**
     * Обновляет существующую сущность Personal данными из PersonalRequest.
     * Копирует ID из старой сущности и обновляет остальные поля из запроса.
     *
     * @param oldPersonal Существующая сущность Personal, которая будет обновлена.
     * @param personalRequest Объект PersonalRequest с новыми данными.
     */
    default Personal toUpdateEntityFromPersonalRequest(Personal oldPersonal,
                                                       PersonalRequest personalRequest) {
        Personal personal = new Personal();

        personal.setId(oldPersonal.getId());
        personal.setVersion(oldPersonal.getVersion());
        personal.setName(personalRequest.getName());
        personal.setSurname(personalRequest.getSurname());
        personal.setLastName(personalRequest.getLastName());
        personal.setPhoneNumber(personalRequest.getPhoneNumber());
        personal.setEmail(personalRequest.getEmail());
        personal.setPassword(personalRequest.getPassword());
        personal.setRole(Role.valueOf(personalRequest.getRole()));

        return personal;
    }

    /**
     * Преобразует объект PersonalRequest в новую сущность Personal.
     * Создает новую сущность и заполняет ее данными из запроса.
     *
     * @param personalRequest Объект PersonalRequest с данными для создания новой сущности.
     */
    default Personal toEntityFromPersonalRequest(PersonalRequest personalRequest) {

        Personal personal = new Personal();
        personal.setName(personalRequest.getName());
        personal.setSurname(personalRequest.getSurname());
        personal.setLastName(personalRequest.getLastName());
        personal.setPhoneNumber(personalRequest.getPhoneNumber());
        personal.setEmail(personalRequest.getEmail());
        personal.setRole(Role.valueOf(personalRequest.getRole()));
        return personal;
    }


    /**
     * Преобразует страницу сущностей Personal в страницу PersonalResponse.
     * Применяет метод toPersonalProfileResponse к каждой сущности на странице.
     *
     * @param personalPage Страница сущностей Personal для преобразования.
     */
    default Page<PersonalResponse> toPersonalProfileResponse(Page<Personal> personalPage) {
        return personalPage.map(this::toPersonalProfileResponse);
    }
}