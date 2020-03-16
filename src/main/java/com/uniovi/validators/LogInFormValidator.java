package com.uniovi.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;

public class LogInFormValidator implements Validator{

	@Autowired
	private UsersService usersService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user=(User)target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Error.signup.email.empty");
		
		if(this.usersService.getUserByEmail(user.getEmail())==null) {
			errors.rejectValue("email", "Error.login.email.notExist");
		}
		else if(!this.usersService.getUserByEmail(user.getEmail()).getPassword().contentEquals(user.getPassword())){
			errors.rejectValue("email", "Error.login.pass.incorrect");
		}
		
		
		
	}

}
