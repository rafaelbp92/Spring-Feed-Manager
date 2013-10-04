package com.springsecurity.beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;


import com.springsecurity.entities.Feed;
import com.springsecurity.entities.RssEntry;
import com.springsecurity.entities.User;
import com.springsecurity.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

@Controller
@Scope("request")
public class CadastroUsuarioBean {
	@Autowired
	private UserService service;
	@Autowired
	private UserSession session;
	
	private String nome;
	private String senha;
	

	public String addUsuario(){
		try{
			User user = service.cadastrarUsuario(nome, senha);
			loginSpringSecurity(user);
			session.setUser(user);		
			return "userPage";
		}catch(IllegalArgumentException ex){
			message(ex.getMessage());
		}
		return "";
	}
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	private void message(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(message));
	}
	
	private void loginSpringSecurity(User user) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				user.getUsername(), null, user.getRoles());
		token.setDetails(user);

		SecurityContextHolder.createEmptyContext();
		SecurityContextHolder.getContext().setAuthentication(token);
	}
	
}