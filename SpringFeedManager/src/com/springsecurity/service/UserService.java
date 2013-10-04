package com.springsecurity.service;


import com.springsecurity.entities.User;

public interface UserService {
	User cadastrarUsuario(String username, String password);
	void assinarFeed(String url,User user );
	User bindUser(User user);
}
