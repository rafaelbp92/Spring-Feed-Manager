package com.springsecurity.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springsecurity.dao.FeedDAO;
import com.springsecurity.dao.UserDAO;
import com.springsecurity.entities.Feed;
import com.springsecurity.entities.Role;
import com.springsecurity.entities.User;
import com.springsecurity.service.UserService;

@Service
public class UsuarioServiceImpl implements UserService{
	@Autowired
	private UserDAO dao;
	
	@Autowired
	private FeedDAO daoFeed;
	
	@Override
	public User cadastrarUsuario(String username, String password) throws IllegalArgumentException{
		if (isEmptyOrNull(username) || isEmptyOrNull(password)) {
			throw new IllegalArgumentException(
					"Atenção, username ou password vazios!");
		}
		
		User teste = dao.getByUsername(username);
		if(teste != null){
			throw new IllegalArgumentException(
					"Atenção, usuário já existe");
		}
		
		
		Role userRole = dao.getRoleUser();
		List<Role> roles = new ArrayList<Role>();
		List<Feed> feeds = new ArrayList<Feed>();
		roles.add(userRole);
		User user = new User();
		user.setId(null);
		user.setUsername(username);
		user.setPassword(password);
		user.setRoles(roles);
		user.setFeeds(feeds);
		dao.save(user);
		return user;
	}
	
	private boolean isEmptyOrNull(String s) {
		return s == null || s.equals("");
	}

	@Override
	public void assinarFeed(String url, User user) {
		if (user==null || isEmptyOrNull(url)) {
			throw new IllegalArgumentException(
					"Atenção, url inválida!");
		}
		Feed teste = daoFeed.getByUrlAndUser(url,user);
		if(teste != null){
			throw new IllegalArgumentException(
					"Atenção, url já cadastrada");
		}
		
		Feed feed = new Feed();
		feed.setId(null);
		feed.setUrl(url);
		feed.setUser(user);
		daoFeed.save(feed);
		
	}

	@Override
	public User bindUser(User user) {
		User u = dao.getById(user.getId());
		return u;
	}
}
