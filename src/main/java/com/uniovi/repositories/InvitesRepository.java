package com.uniovi.repositories;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Invite;
import com.uniovi.entities.User;

public interface InvitesRepository extends CrudRepository<Invite,Long>{

	
	
	Page<Invite> findByReciever(Pageable pageable,User reciever);
	List<Invite> findByReciever(User reciever);
	Page<Invite> findAll(Pageable pageable);

}
