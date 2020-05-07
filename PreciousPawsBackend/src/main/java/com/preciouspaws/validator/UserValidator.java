/**
 * 
 */
package com.preciouspaws.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.preciouspaws.pojo.User;

/**
 * @author pavanrao
 *
 */
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class type) {
        return type.equals(User.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
            
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "error.invalid.userName", "userName Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.invalid.password", "password Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "error.invalid.fullName", "fullName Required");
        
    }

}

