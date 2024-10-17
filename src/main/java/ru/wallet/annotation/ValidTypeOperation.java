package ru.wallet.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = OperationTypeValidator.class)
public @interface ValidTypeOperation {
    Class<? extends Enum<?>> enumClass();

    String message() default "{jakarta.validation.constraints.ValidTypeOperation.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
