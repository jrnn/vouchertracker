package vouchertracker.validation.constraint;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import vouchertracker.validation.validator.RefundLessThanPurchaseValidator;

@Constraint(validatedBy = RefundLessThanPurchaseValidator.class)
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface RefundLessThanPurchase {

    String message() default "Refund cannot be greater than purchase amount";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}