package vouchertracker.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import vouchertracker.utility.CustomParser;
import vouchertracker.validation.constraint.ParsableAsDouble;

public class ParsableAsDoubleValidator implements ConstraintValidator<ParsableAsDouble, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        try {
            return CustomParser.parseDouble(s) >= 0;
        } catch (NullPointerException e) {
        }

        return false;
    }

}