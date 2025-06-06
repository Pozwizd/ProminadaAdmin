package com.pozwizd.prominadaadmin.validation;

import com.pozwizd.prominadaadmin.validator.ActionFieldsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ActionFieldsValidator.class)
@Documented
public @interface ConditionalActionValidation {
    String message() default "Invalid action fields";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

