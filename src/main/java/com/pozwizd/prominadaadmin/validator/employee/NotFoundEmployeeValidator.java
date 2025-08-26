package com.pozwizd.prominadaadmin.validator.employee;

import com.pozwizd.prominadaadmin.entity.Realtor;
import com.pozwizd.prominadaadmin.service.RealtorService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NotFoundEmployeeValidator implements ConstraintValidator<NotFoundEmployee, Object> {

    private final RealtorService realtorService;

    private String employeeCodeField;
    private boolean nullable;
    private String message;

    @Override
    public void initialize(NotFoundEmployee ann) {
        this.employeeCodeField = ann.employeeCodeField();
        this.nullable = ann.nullable();
        this.message = ann.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Field field = ReflectionUtils.findField(value.getClass(), employeeCodeField);
        if (field == null) {
            return true;
        }
        field.setAccessible(true);

        Object codeObj;
        try {
            codeObj = field.get(value);
        } catch (IllegalAccessException e) {
            return true;
        }

        String code = codeObj == null ? null : codeObj.toString();

        if (!StringUtils.hasText(code)) {
            if (nullable) {
                return true;
            } else {
                buildViolation(context, "Код сотрудника обязателен");
                return false;
            }
        }

        Realtor found = realtorService.getByCode(code);
        if (found == null) {
            buildViolation(context, message);
            return false;
        }

        return true;
    }

    private void buildViolation(ConstraintValidatorContext context, String msg) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(msg)
                .addPropertyNode(employeeCodeField)
                .addConstraintViolation();
    }
}
