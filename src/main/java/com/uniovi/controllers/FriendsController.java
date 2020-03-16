package com.uniovi.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.Friends;
import com.uniovi.entities.Invite;
import com.uniovi.entities.User;
import com.uniovi.services.FriendsService;
import com.uniovi.services.InvitesService;
import com.uniovi.services.RolesService;
import com.uniovi.services.UsersService;

@Controller
public class FriendsController {

	@Autowired
	private FriendsService friendsService;
	
	@Autowired
	private InvitesService invitesService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private RolesService rolesService;
	
	@RequestMapping("/friends/list")
	public String listFriends(Model model,Principal principal){
		String email=principal.getName();
		User u=this.usersService.getUserByEmail(email);
		if(u.getRole().contentEquals(this.rolesService.getRoles()[1]))
			model.addAttribute("friendsList", this.friendsService.getAllFriends());
		else {
			model.addAttribute("friendsList", this.friendsService.getAllUserFriends(u));
		}
		return "/friends/list";
	}
	
	@RequestMapping("/friend/add/{id}")
	public String addFriend(@PathVariable Long id) {
		Invite i=this.invitesService.getInvite(id);
		this.friendsService.addFriends(new Friends(i.getSender(),i.getReciever()));
		this.invitesService.deleteInvite(id);
		return "redirect:/invite/recieve";
	}
}
