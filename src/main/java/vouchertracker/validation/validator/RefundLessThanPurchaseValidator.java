package vouchertracker.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import vouchertracker.domain.dto.VoucherDto;
import vouchertracker.utility.CustomParser;
import vouchertracker.validation.constraint.RefundLessThanPurchase;

public class RefundLessThanPurchaseValidator
        implements ConstraintValidator<RefundLessThanPurchase, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        VoucherDto dto = (VoucherDto) o;

        try {
            Double p = CustomParser.parseDouble(dto.getPurchaseAmount());
            Double r = CustomParser.parseDouble(dto.getRefundAmount());

            return (r < 0 || p < 0 ? true : r <= p);
        } catch (Exception e) {
        }

        return true;
    }

}