package vouchertracker.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import vouchertracker.service.AccountService;

public class EmailNotReservedValidator implements ConstraintValidator<EmailNotReserved, String> {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !accountService.isEmailReserved(email);
    }

}