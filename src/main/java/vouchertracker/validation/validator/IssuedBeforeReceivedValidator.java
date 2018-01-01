package vouchertracker.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import vouchertracker.domain.VoucherDto;
import vouchertracker.validation.constraint.IssuedBeforeReceived;

public class IssuedBeforeReceivedValidator
        implements ConstraintValidator<IssuedBeforeReceived, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        VoucherDto dto = (VoucherDto) o;

        try {
            return !dto.getReceivedOn().isBefore(dto.getIssuedOn());
        } catch (Exception e) {
        }

        return true;
    }

}