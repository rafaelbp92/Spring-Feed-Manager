package com.springsecurity.service;


import java.io.IOException;
import java.net.MalformedURLException;

import com.springsecurity.entities.User;
import com.sun.syndication.io.FeedException;

public interface UserService {
	User cadastrarUsuario(String username, String password);
	void assinarFeed(String url,User user ) throws MalformedURLException, IOException, IllegalArgumentException, FeedException;
	void cancelarFeed(String url, User user);
	User bindUser(User user);
}
