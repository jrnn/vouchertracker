package vouchertracker.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import vouchertracker.domain.PasswordDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        PasswordDto dto = (PasswordDto) o;

        return dto.getNewPassword().equals(dto.getConfirmPassword());
    }

}