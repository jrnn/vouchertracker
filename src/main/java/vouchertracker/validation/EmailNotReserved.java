package vouchertracker.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = EmailNotReservedValidator.class)
@Target( {ElementType.METHOD, ElementType.FIELD} )
@Retention(RUNTIME)
public @interface EmailNotReserved {

    String message() default "This email address is already in use by another user";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}