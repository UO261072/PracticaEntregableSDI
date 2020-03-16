package com.uniovi.repositories;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Invite;
import com.uniovi.entities.User;

public interface InvitesRepository extends CrudRepository<Invite,Long>{

	/*@Query("SELECT r FROM invite r")
	List<Invite> findUserInvites(String id);*/
	
	List<Invite> findByReciever(User reciever);

}
