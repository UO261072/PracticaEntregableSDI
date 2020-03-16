package com.uniovi.services;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Page<User> getUsers(Pageable pageable){
		Page<User> users=usersRepository.findAll(pageable);
		return users;
	}
	public Page<User> searchByNameSurnameAndEmail(Pageable pageable,String searchText){
		searchText= "%"+searchText+"%";
		Page<User> users=new PageImpl<User>(new ArrayList<User>());
		users=this.usersRepository.searchByNameSurnameAndEmail(pageable, searchText);
		return users;
		
	}
	public List<User> findAll(){
		List<User> users=new ArrayList<User>();
		this.usersRepository.findAll().forEach(users::add);
		return users;
	}
	
	public User getUser(Long id) {
		return this.usersRepository.findById(id).get();
	}
	
	public void addUser(User user) {
		user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
		this.usersRepository.save(user);
	}
	
	public User getUserByEmail(String email) {
		return this.usersRepository.findByEmail(email);
	}
	
	public void deleteUser(Long id) {
		this.usersRepository.deleteById(id);
	}
}
