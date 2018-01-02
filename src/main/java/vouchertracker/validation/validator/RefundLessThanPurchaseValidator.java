package vouchertracker.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import vouchertracker.domain.dto.VoucherDto;
import vouchertracker.validation.constraint.RefundLessThanPurchase;

public class RefundLessThanPurchaseValidator
        implements ConstraintValidator<RefundLessThanPurchase, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        VoucherDto dto = (VoucherDto) o;

        try {
            Double p = Double.parseDouble(dto.getPurchaseAmount().trim().replace(",", "."));
            Double r = Double.parseDouble(dto.getRefundAmount().trim().replace(",", "."));

            if (r < 0 || p < 0) return true;

            return r <= p;
        } catch (NumberFormatException e) {
        }

        return true;
    }

}