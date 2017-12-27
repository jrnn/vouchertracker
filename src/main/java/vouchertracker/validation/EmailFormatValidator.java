package vouchertracker.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailFormatValidator implements ConstraintValidator<EmailFormat, String> {

    private static final String EMAIL_REGEX =
            "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@" +
            "[a-z0-9-]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email
                .trim().toLowerCase()
                .matches(EMAIL_REGEX);
    }

}