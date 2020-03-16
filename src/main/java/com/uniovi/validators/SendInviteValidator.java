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
		return Invite.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Invite invite=(Invite) target;
		User reciever=invite.getReciever();
		User sender=invite.getSender();
		List<Friends> friends=this.friendsService.getAllUserFriends(sender);
		for(int i=0;i<friends.size();i++) {
			if(friends.get(i).getUser1().equals(reciever)||friends.get(i).getUser2().equals(reciever))
				errors.rejectValue("recieveString", "Error.friend");
		}
		List<Invite> invites=this.invitesService.getInvites();
		for(int i=0;i<invites.size();i++) {
			if(invites.get(i).getReciever().getEmail().contentEquals(reciever.getEmail())||invites.get(i).getSender().getEmail().contentEquals(reciever.getEmail()))
				errors.rejectValue("recieveString", "Error.friend");
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
		List<Invite> invites=this.invitesService.getInvites();
		for(int i=0;i<invites.size();i++) {
			if(invites.get(i).getReciever().getEmail().contentEquals(reciever.getEmail())||invites.get(i).getSender().getEmail().contentEquals(reciever.getEmail()))
				return false;
		}
		return true;
	}
}
