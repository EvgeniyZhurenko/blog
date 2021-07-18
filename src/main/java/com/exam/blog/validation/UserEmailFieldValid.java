package com.exam.blog.validation;

import com.exam.blog.models.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserEmailFieldValid implements org.springframework.validation.Validator{
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        final String regex = "@\\w+[.]\\w{2,6}$";
        final String string = user.getEmail();

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(string);

        if(!matcher.find()){
            errors.rejectValue("email", "email.wrong", "Поле email заполнено не корректно. Email должен быть : username@gmail.com");
        }
    }
}
