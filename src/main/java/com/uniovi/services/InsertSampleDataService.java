package com.uniovi.services;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friends;
import com.uniovi.entities.Invite;
import com.uniovi.entities.User;



@Service
public class InsertSampleDataService {

	@Autowired
	private InvitesService invitesService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private RolesService rolesService;
	@Autowired
	private FriendsService friendsService;
	
	@PostConstruct
	public void init() {
		User user0=new User("Izuko","Gaen","admin@email.com");
		user0.setPassword("admin");
		user0.setRole(this.rolesService.getRoles()[1]);
		
		User user1=new User("Koyomi","Araragi","koyomi@nishioishin.jp");
		user1.setPassword("123456");
		user1.setRole(this.rolesService.getRoles()[0]);
		
		User user2=new User("Tsukihi","Araragi","tsukihi@nishioishin.jp");
		user2.setPassword("123456");
		user2.setRole(this.rolesService.getRoles()[0]);
		
		User user3=new User("Karen","Araragi","karen@nishioishin.jp");
		user3.setPassword("123456");
		user3.setRole(this.rolesService.getRoles()[0]);
		
		User user4=new User("Yotsugi","Ononoki","yotsugi@nishioishin.jp");
		user4.setPassword("123456");
		user4.setRole(this.rolesService.getRoles()[0]);
		
		User user5=new User("Yozuru","Kagenui","yozuru@nishioishin.jp");
		user5.setPassword("123456");
		user5.setRole(this.rolesService.getRoles()[0]);
		
		User user6=new User("Tsubasa","Hanekawa","tsubasa@nishioishin.jp");
		user6.setPassword("123456");
		user6.setRole(this.rolesService.getRoles()[0]);
		
		
		
		this.usersService.addUser(user0);
		this.usersService.addUser(user1);
		this.usersService.addUser(user2);
		this.usersService.addUser(user3);
		this.usersService.addUser(user4);
		this.usersService.addUser(user5);
		this.usersService.addUser(user6);
		
		//Page<User> usersP=new PageImpl<User>(new LinkedList<User>());
		//List<User> users=this.usersService.getUsers(Pageable pageable);
		user1=this.usersService.getUserByEmail(user1.getEmail());
		user2=this.usersService.getUserByEmail(user2.getEmail());
		user3=this.usersService.getUserByEmail(user3.getEmail());
		Invite invite1=new Invite(user1,user2);
		Invite invite2=new Invite(user3,user1);
		Invite invite3=new Invite(user5,user1);

		this.invitesService.addInvite(invite1);
		this.invitesService.addInvite(invite2);
		this.invitesService.addInvite(invite3);
		
		this.friendsService.addFriends(new Friends(user1,user6));

	}
}


