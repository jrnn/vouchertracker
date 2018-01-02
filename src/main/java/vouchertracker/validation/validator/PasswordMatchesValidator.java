package vouchertracker.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import vouchertracker.domain.dto.PasswordDto;
import vouchertracker.validation.constraint.PasswordMatches;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        PasswordDto dto = (PasswordDto) o;

        return dto.getNewPassword().equals(dto.getConfirmPassword());
    }

}