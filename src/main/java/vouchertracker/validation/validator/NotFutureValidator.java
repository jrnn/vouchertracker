package vouchertracker.validation.validator;

import java.time.LocalDate;
import java.time.temporal.Temporal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import vouchertracker.validation.constraint.NotFuture;

public class NotFutureValidator implements ConstraintValidator<NotFuture, Temporal> {

    @Override
    public boolean isValid(Temporal t, ConstraintValidatorContext context) {
        if (t == null) return false;

        return !LocalDate.from(t).isAfter(LocalDate.now());
    }

}