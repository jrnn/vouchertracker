package vouchertracker.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import vouchertracker.validation.constraint.ParsableAsDouble;

public class ParsableAsDoubleValidator implements ConstraintValidator<ParsableAsDouble, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        s = s.trim().replace(",", ".");

        try {
            Double d = Double.parseDouble(s);

            return d >= 0;
        } catch (NumberFormatException e) {
        }

        return false;
    }

}