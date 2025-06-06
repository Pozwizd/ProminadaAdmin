package com.pozwizd.prominadaadmin.validator;

import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDto;
import com.pozwizd.prominadaadmin.validation.ConditionalActionValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ActionFieldsValidator implements ConstraintValidator<ConditionalActionValidation, BuilderPropertyDto> {

    @Override
    public boolean isValid(BuilderPropertyDto dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getIsAction() == null || !dto.getIsAction()) {
            return true;
        }

        boolean valid = true;
        context.disableDefaultConstraintViolation();

        if (dto.getActionTitle() == null || dto.getActionTitle().isBlank()) {
            context.buildConstraintViolationWithTemplate("title.required")
                    .addPropertyNode("actionTitle")
                    .addConstraintViolation();
            valid = false;
        } else if (dto.getActionTitle().length() > 50) {
            context.buildConstraintViolationWithTemplate("title.length")
                    .addPropertyNode("actionTitle")
                    .addConstraintViolation();
            valid = false;
        }

        if (!dto.getActionDescription().isEmpty() && dto.getActionDescription().length() > 300) {
            context.buildConstraintViolationWithTemplate("description.length")
                    .addPropertyNode("actionDescription")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
