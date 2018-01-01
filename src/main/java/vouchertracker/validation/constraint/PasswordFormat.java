package vouchertracker.validation.constraint;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import vouchertracker.validation.validator.PasswordFormatValidator;

@Constraint(validatedBy = PasswordFormatValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface PasswordFormat {

    String message() default "Does not meet requirements of password policy";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}