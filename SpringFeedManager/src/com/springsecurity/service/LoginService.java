package com.springsecurity.service;

import com.springsecurity.entities.User;

public interface LoginService {
	User login(String username, String password) throws IllegalArgumentException;
}
