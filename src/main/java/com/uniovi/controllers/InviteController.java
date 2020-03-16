package com.uniovi.controllers;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



import com.uniovi.entities.Invite;
import com.uniovi.entities.User;
import com.uniovi.services.InvitesService;
import com.uniovi.services.RolesService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SendInviteValidator;
import com.uniovi.validators.SignUpFormValidator;

import net.bytebuddy.asm.Advice.This;

@Controller
public class InviteController {

	@Autowired
	private InvitesService inviteService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private HttpSession httpSession;

	@Autowired
	private SendInviteValidator sendInviteValidator;
	
	@RequestMapping(value="/invite/send",method=RequestMethod.POST)
	public String sendInvite(@ModelAttribute Invite invite,Principal principal) {
	
		String email=principal.getName();
		invite.setSender(this.usersService.getUserByEmail(email));
		String reciever=invite.getRecieveString();
		String[] a=reciever.split(" ");
		reciever=a[2];
		User u=this.usersService.getUserByEmail(reciever);
		invite.setReciever(u);
		this.inviteService.addInvite(invite);
		return "redirect:/invite/send";
	}
	@RequestMapping(value="/invite/send",method=RequestMethod.GET)
	public String sendInvite(Model model,Pageable pageable) {
		model.addAttribute("userList", this.usersService.findAll());
		return "/invite/send";
	}
	@RequestMapping("/invite/send/{id}")
	public String sendInvite(@PathVariable Long id,Principal principal) {
		
		User user1=this.usersService.getUserByEmail(principal.getName());
		User user2=this.usersService.getUser(id);
		Invite invite=new Invite(user1,user2);
		
		if(!this.sendInviteValidator.comprobar(invite))
			return "/invite/send";
		this.inviteService.addInvite(invite);
		return "redirect:/user/list";
	}
	@RequestMapping("/invite/recieve")
	public String recievedInvites(Model model,Principal principal){
		String email=principal.getName();
		User u=this.usersService.getUserByEmail(email);
		if(u.getRole().contentEquals(this.rolesService.getRoles()[1]))
			model.addAttribute("inviteList", this.inviteService.getInvites());
		else {
			model.addAttribute("inviteList", this.inviteService.getUserInvites(u));
		}
		return "/invite/recieve";
	}
	
	@RequestMapping("/invite/delete/{id}")
	public String removeInvite(@PathVariable Long id) {
		this.inviteService.deleteInvite(id);
		return "redirect:/invite/recieve";
		
	}
	

	
}
