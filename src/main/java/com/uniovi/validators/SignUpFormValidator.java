package com.uniovi.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;

@Component
public class SignUpFormValidator implements Validator{

	@Autowired
	private UsersService usersService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user=(User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Error.signup.email.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Error.signup.name.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "Error.signup.surname.empty");
		
		if(this.usersService.getUserByEmail(user.getEmail())!=null) {
			errors.rejectValue("email", "Error.signup.email.duplicate");
		}
		if(!user.getPassword().contentEquals(user.getPasswordConfirm())) {
			errors.rejectValue("passwordConfirm", "Error.signup.password.notequal");
		}
		
	}

}
