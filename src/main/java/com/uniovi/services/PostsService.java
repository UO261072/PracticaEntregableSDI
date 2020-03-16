package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;
import com.uniovi.repositories.PostsRepository;

@Service
public class PostsService {

	 @Autowired
	 private PostsRepository postsRepository;
	 
	 public Post getPost(Long id) {
		 return this.postsRepository.findById(id).get();
	 }
	 public List<Post> getPosts(){
		 List<Post> posts=new ArrayList<Post>();
		 this.postsRepository.findAll().forEach(posts::add);
		 return posts;
	 }
	 public void deletePost(Long id) {
		 this.postsRepository.deleteById(id);
	 }
	 public void addPost(Post post) {
		 this.postsRepository.save(post);
	 }
	 public List<Post> getPosts(User user){
		 List<Post> posts=new ArrayList<Post>();
		 this.postsRepository.findAll().forEach(posts::add);
		 return posts;
	 }
	 
}
