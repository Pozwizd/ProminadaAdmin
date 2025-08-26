package com.pozwizd.prominadaadmin.validator.employee;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotFoundEmployeeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotFoundEmployee {

    String message() default "Сотрудник (риелтор) по employeeCode не найден";

    String employeeCodeField() default "employeeCode";

    boolean nullable() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
