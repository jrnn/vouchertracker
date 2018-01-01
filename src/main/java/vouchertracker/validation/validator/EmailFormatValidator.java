package vouchertracker.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import vouchertracker.validation.constraint.EmailFormat;

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