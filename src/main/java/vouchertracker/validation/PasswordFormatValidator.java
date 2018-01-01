package vouchertracker.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordFormatValidator implements ConstraintValidator<PasswordFormat, String> {

    private static final String ALLOWED_CHARACTERS =
            "[A-Za-z0-9!#$%&'*+.:,;/=?@^_~-]+";

    private static final String MINIMUM_REQUIREMENTS =
            "((?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,})";

    @Override
    public boolean isValid(String pw, ConstraintValidatorContext context) {
        return (pw.matches(ALLOWED_CHARACTERS) &&
                pw.matches(MINIMUM_REQUIREMENTS));
    }

}