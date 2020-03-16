package com.uniovi.validators;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.uniovi.entities.Friends;
import com.uniovi.entities.Invite;
import com.uniovi.entities.User;
import com.uniovi.services.FriendsService;
import com.uniovi.services.InvitesService;

@Component
public class SendInviteValidator implements Validator{

	@Autowired
	public InvitesService invitesService;
	
	@Autowired
	public FriendsService friendsService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Invite invite=(Invite) target;
		
		User reciever=invite.getReciever();
		User sender=invite.getSender();
		List<Friends> friends=this.friendsService.getAllUserFriends(sender);
		for(int i=0;i<friends.size();i++) {
			if(friends.get(i).getUser1().equals(reciever)||friends.get(i).getUser2().equals(reciever))
				errors.rejectValue("invite", "Error.friend");
		}
		List<Invite> invites=this.invitesService.getUserInvites(sender);
		for(int i=0;i<invites.size();i++) {
			if(invites.get(i).getReciever().equals(reciever)||invites.get(i).getSender().equals(reciever))
				errors.rejectValue("invite", "Error.friend");
		}
		
	}
	
	public boolean comprobar(Invite invite) {
		User reciever=invite.getReciever();
		User sender=invite.getSender();
		List<Friends> friends=this.friendsService.getAllUserFriends(sender);
		for(int i=0;i<friends.size();i++) {
			if(friends.get(i).getUser1().equals(reciever)||friends.get(i).getUser2().equals(reciever))
				return false;
		}
		List<Invite> invites=this.invitesService.getUserInvites(sender);
		for(int i=0;i<invites.size();i++) {
			if(invites.get(i).getReciever().equals(reciever)||invites.get(i).getSender().equals(reciever))
				return false;
		}
		return true;
	}
}
