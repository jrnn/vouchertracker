package vouchertracker.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyValidator implements ConstraintValidator<NotEmpty, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        return !s.trim().isEmpty();
    }

}