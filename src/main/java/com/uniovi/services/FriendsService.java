package com.uniovi.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friends;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendsRepository;

@Service
public class FriendsService {

	@Autowired
	private FriendsRepository friendsRepository;
	
	@Autowired
	private HttpSession httpSession;
	
	public List<Friends> getAllFriends(){
		List<Friends> friendss=new ArrayList<Friends>();
		this.friendsRepository.findAll().forEach(friendss::add);
		return friendss;
	}
	public List<Friends> getAllUserFriends(User u){
		List<Friends> friendss=new ArrayList<Friends>();
		this.friendsRepository.findByUser1(u).forEach(friendss::add);
		this.friendsRepository.findByUser2(u).forEach(friendss::add);
		return friendss;
	}
	
	
	public void addFriends(Friends friends) {
		this.friendsRepository.save(friends);
	}
	
	public void deleteFriends(Long id) {
		this.friendsRepository.deleteById(id);
	}
	
	@SuppressWarnings("unchecked")
	public Friends getFriends(Long id) {
		Set<Friends> consultedList=(Set<Friends>)httpSession.getAttribute("consultedList");
		if(consultedList==null) {
			consultedList=new HashSet<Friends>();
		}
		Friends obtainedfriends=this.friendsRepository.findById(id).get();
		consultedList.add(obtainedfriends);
		httpSession.setAttribute("consultedList", consultedList);
		return obtainedfriends;
	}
	
}
