package com.pozwizd.prominadaadmin.validation;

import com.pozwizd.prominadaadmin.models.personal.PersonalRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;

/**
 * Валидатор для аннотации {@link PasswordMatch}
 */
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

    private String passwordField;
    private String confirmPasswordField;
    private String message;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        this.passwordField = constraintAnnotation.password();
        this.confirmPasswordField = constraintAnnotation.confirmPassword();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object passwordValue = new BeanWrapperImpl(value).getPropertyValue(passwordField);
        Object confirmPasswordValue = new BeanWrapperImpl(value).getPropertyValue(confirmPasswordField);
        
        // Если пароль не указан, то валидация не требуется
        if (passwordValue == null || passwordValue.toString().isEmpty()) {
            return true;
        }
        
        boolean isValid = Objects.equals(passwordValue, confirmPasswordValue);
        
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(confirmPasswordField)
                    .addConstraintViolation();
        }
        
        return isValid;
    }
}