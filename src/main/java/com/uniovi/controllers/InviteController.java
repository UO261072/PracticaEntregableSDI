package com.uniovi.controllers;

import java.security.Principal;

import java.util.LinkedList;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



import com.uniovi.entities.Invite;
import com.uniovi.entities.User;
import com.uniovi.services.InvitesService;
import com.uniovi.services.RolesService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SendInviteValidator;


@Controller
public class InviteController {

	@Autowired
	private InvitesService inviteService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private RolesService rolesService;
	

	@Autowired
	private SendInviteValidator sendInviteValidator;
	
	@RequestMapping(value="/invite/send",method=RequestMethod.POST)
	public String sendInvite(@Validated Invite invite,BindingResult result,Principal principal) {
	
		String email=principal.getName();
		invite.setSender(this.usersService.getUserByEmail(email));
		String reciever=invite.getRecieveString();
		String[] a=reciever.split(" ");
		reciever=a[2];
		User u=this.usersService.getUserByEmail(reciever);
		invite.setReciever(u);
		this.sendInviteValidator.validate(invite, result);
		if(result.hasErrors())
			return "/invite/send";
		this.inviteService.addInvite(invite);
		return "redirect:/invite/send";
	}
	@RequestMapping(value="/invite/send",method=RequestMethod.GET)
	public String sendInvite(Model model) {
		model.addAttribute("userList", this.usersService.findAll());
		return "/invite/send";
	}
	@RequestMapping("/invite/send/{id}")
	public String sendInvite(@PathVariable Long id,Principal principal) {
		
		User user1=this.usersService.getUserByEmail(principal.getName());
		User user2=this.usersService.getUser(id);
		Invite invite=new Invite(user1,user2);
		
		if(!this.sendInviteValidator.comprobar(invite))
			return "redirect:/user/list";
		this.inviteService.addInvite(invite);
		return "redirect:/user/list";
	}
	@RequestMapping("/invite/recieve")
	public String recievedInvites(Model model,Pageable pageable,Principal principal){
		Page<Invite> invites=new PageImpl<Invite>(new LinkedList<Invite>());
		String email=principal.getName();
		User u=this.usersService.getUserByEmail(email);
		if(u.getRole().contentEquals(this.rolesService.getRoles()[1]))
			invites=this.inviteService.getInvites(pageable);
		else {
			invites= this.inviteService.getUserInvites(pageable,u);
		}
		model.addAttribute("inviteList", invites.getContent());
		model.addAttribute("page", invites);
		return "/invite/recieve";
	}
	
	@RequestMapping("/invite/delete/{id}")
	public String removeInvite(@PathVariable Long id) {
		this.inviteService.deleteInvite(id);
		return "redirect:/invite/recieve";
		
	}
	

	
}
