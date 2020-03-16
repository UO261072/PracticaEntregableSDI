package com.uniovi.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.uniovi.entities.Invite;
import com.uniovi.entities.User;
import com.uniovi.repositories.InvitesRepository;

@Service
public class InvitesService {

	@Autowired
	private InvitesRepository invitesRepository;
	
	@Autowired
	private HttpSession httpSession;
	
	public List<Invite> getInvites(){
		List<Invite> invites=new ArrayList<Invite>();
		this.invitesRepository.findAll().forEach(invites::add);
		return invites;
	}
	public Page<Invite> getInvites(Pageable pageable){
		Page<Invite> invites=invitesRepository.findAll(pageable);
		return invites;
	}
	
	public void addInvite(Invite invite) {
		this.invitesRepository.save(invite);
	}
	
	public void deleteInvite(Long id) {
		this.invitesRepository.deleteById(id);
	}
	
	public Invite getInvite(Long id) {
		Set<Invite> consultedList=(Set<Invite>)httpSession.getAttribute("consultedList");
		if(consultedList==null) {
			consultedList=new HashSet<Invite>();
		}
		Invite obtainedinvite=this.invitesRepository.findById(id).get();
		consultedList.add(obtainedinvite);
		httpSession.setAttribute("consultedList", consultedList);
		return obtainedinvite;
	}
	
	public List<Invite> getUserInvites(User user){
		List<Invite> invites=new ArrayList<Invite>();
		this.invitesRepository.findByReciever(user).forEach(invites::add);
		return invites;
	}
	public Page<Invite> getUserInvites(Pageable pageable,User user){
		Page<Invite> invites=this.invitesRepository.findByReciever(pageable,user);
		return invites;
	}
}
