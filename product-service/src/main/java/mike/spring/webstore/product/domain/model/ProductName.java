package mike.spring.webstore.product.domain.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = "[^\\<\\>\\{\\}]{5,50}")
@ReportAsSingleViolation
public @interface ProductName {

    String message() default "invalid product name pattern (size: 5 to 30)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
