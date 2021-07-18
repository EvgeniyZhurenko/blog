package com.exam.blog.config.anotation;

import com.exam.blog.validation.UserValidBornAnnotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserValidBornAnnotation.class)
public @interface UserBorn {
    String message() default "Дата рождения не может быть в будущем времени или не заполнена";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
