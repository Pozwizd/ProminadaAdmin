package com.pozwizd.prominadaadmin.validator.branch;

import com.pozwizd.prominadaadmin.entity.Branch;
import com.pozwizd.prominadaadmin.service.BranchService;
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
public class NotFoundBranchValidator implements ConstraintValidator<NotFoundBranch, Object> {

    private final BranchService branchService;

    private String branchCodeField;
    private boolean nullable;
    private String message;

    @Override
    public void initialize(NotFoundBranch constraintAnnotation) {
        this.branchCodeField = constraintAnnotation.branchCodeField();
        this.nullable = constraintAnnotation.nullable();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Field field = ReflectionUtils.findField(value.getClass(), branchCodeField);
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
                buildViolation(context, "Код филиала обязателен");
                return false;
            }
        }

        Optional<Branch> found = branchService.findByCode(code);
        if (found.isEmpty()) {
            buildViolation(context, message);
            return false;
        }

        return true;
    }

    private void buildViolation(ConstraintValidatorContext context, String msg) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(msg)
                .addPropertyNode(branchCodeField)
                .addConstraintViolation();
    }
}
