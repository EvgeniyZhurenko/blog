package com.exam.blog.validation;

import com.exam.blog.anotation.UserBorn;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class UserValidBornAnnotation implements ConstraintValidator<UserBorn, Date> {

    @Override
    public void initialize(UserBorn constraintAnnotation) {

    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if(date != null)
            return date.compareTo(new Date()) < 0;
        else
            return false;
    }
}
