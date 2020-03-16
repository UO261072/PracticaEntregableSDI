package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Friends;
import com.uniovi.entities.Invite;
import com.uniovi.entities.User;

public interface FriendsRepository extends CrudRepository<Friends,Long>{

	List<Friends> findByUser1(User user1);
	List<Friends> findByUser2(User user1);
}
