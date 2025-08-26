package com.pozwizd.prominadaadmin.validator.branch;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotFoundBranchValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotFoundBranch {

    String message() default "Филиал с указанным кодом не найден";

    String branchCodeField() default "branchCode";

    boolean nullable() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
