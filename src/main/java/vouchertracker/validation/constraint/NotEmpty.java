package vouchertracker.validation.constraint;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import vouchertracker.validation.validator.NotEmptyValidator;

@Constraint(validatedBy = NotEmptyValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface NotEmpty {

    String message() default "Field must not be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}