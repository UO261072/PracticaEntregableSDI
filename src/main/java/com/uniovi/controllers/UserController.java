package com.uniovi.controllers;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UserController {
	
	@Autowired
	private SignUpFormValidator signUpValidator;

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private RolesService rolesService;
	
	@RequestMapping("/user/list")
	public String getUserList(Model model,Pageable pageable,@RequestParam(value="",required=false) String searchText) {
		Page<User> users=new PageImpl<User>(new LinkedList<User>());
		if(searchText!=null&&!searchText.isEmpty()) {
			users=this.usersService.searchByNameSurnameAndEmail(pageable, searchText);
		}else {
			users=this.usersService.getUsers(pageable);
		}
		
		model.addAttribute("userList", users.getContent());
		model.addAttribute("page",users);
		return "user/list";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result) {
		this.signUpValidator.validate(user, result);
		if(result.hasErrors())
			return "signup";
		user.setRole(this.rolesService.getRoles()[0]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}
	
	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}
	
}
