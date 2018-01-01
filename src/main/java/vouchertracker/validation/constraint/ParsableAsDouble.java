package vouchertracker.validation.constraint;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import vouchertracker.validation.validator.ParsableAsDoubleValidator;

@Constraint(validatedBy = ParsableAsDoubleValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ParsableAsDouble {

    String message() default "Must be a positive decimal value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}