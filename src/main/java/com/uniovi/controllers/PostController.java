package com.uniovi.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Post;
import com.uniovi.services.PostsService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.PostAddValidator;

@Controller
public class PostController {
	
	
	@Autowired
	private PostsService postService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private PostAddValidator postAddValidator;
	
	@RequestMapping(value="/post/add",method=RequestMethod.POST)
	public String addPost(@Validated Post post,BindingResult result,Principal principal) {
		this.postAddValidator.validate(post, result);
		if(result.hasErrors()) {
			return "/post/add";
		}
		this.postService.addPost(new Post(
				this.usersService.getUserByEmail(principal.getName()),new Date(),post.getTitle(),post.getText()));
		return "redirect:/post/add";
	}
	@RequestMapping(value="/post/add",method=RequestMethod.GET)
	public String addPost(Model model) {
		model.addAttribute("post",new Post());
		return "/post/add";
	}
	@RequestMapping("/post/list")
	public String listPost(Model model,Principal principal) {
		List<Post> posts=this.postService.getPosts(this.usersService.getUserByEmail(principal.getName()));
		model.addAttribute("postList", posts);
		return "/post/list";
	}
	
}
