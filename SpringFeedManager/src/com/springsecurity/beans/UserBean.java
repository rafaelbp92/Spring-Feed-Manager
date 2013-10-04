package com.springsecurity.beans;


import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import com.springsecurity.entities.Feed;
import com.springsecurity.entities.RssEntry;
import com.springsecurity.entities.User;
import com.springsecurity.service.UserService;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;


@Controller
@Scope("request")
public class UserBean {
	
	@Autowired
	private UserService service;
	@Autowired
	private UserSession session;
	private List<RssEntry> feeds;
	
	private String urlFeed;
	

	public String assinarFeed(){
		try{
			service.assinarFeed(urlFeed, session.getUser());
			return "userPage";
		}catch(IllegalArgumentException ex){
			message(ex.getMessage());
		}
		return "";
	}
	
	
	private void message(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(message));
	}
	
	public String getUrlFeed() {
		return urlFeed;
	}

	public void setUrlFeed(String urlFeed) {
		this.urlFeed = urlFeed;
	}
	
	@SuppressWarnings("unchecked")
	private void gerarListaFeeds(){	
	        try {
	          User user = session.getUser();
	          List<Feed> feedsUrls = user.getFeeds();
	          List<SyndEntry> entriesList = new ArrayList<SyndEntry>();
	          for (Feed f : feedsUrls) {
	        	  String rssFeedUrl = f.getUrl();
			        SimpleDateFormat df =
			                new SimpleDateFormat("dd/MM/yyyy");
		            // Connect
		            URLConnection feedUrl = new URL(rssFeedUrl).openConnection();
		            SyndFeedInput input = new SyndFeedInput();
		            // Build the feed list
		            SyndFeed feed = input.build(new XmlReader(feedUrl));
		            feed.setEncoding("UTF-8");
		            List<SyndEntry> entries = feed.getEntries();
		            entriesList.addAll(entries);
		 
		            feeds= new ArrayList<RssEntry>();
		 
		            for (SyndEntry entry : entriesList) {

		 
		                RssEntry rss = new RssEntry();
		                 
		                // Format based on your requirements
		                String title = entry.getTitle(); 
		                rss.setTitle(title);
		                 
		                rss.setAuthor(entry.getAuthor());
		                rss.setPublishedDate(df.format(entry.getPublishedDate()));
		                 
		                // Do some formatting you may require;
		                String description = entry.getDescription().getValue();
		                 
		                rss.setDescription(description);
		 
		                //  Update
		                feeds.add(rss);
		            }
	         
	          }
	        } catch (Exception e) {
	             
	        	message(e.getMessage());
	           
	        }
	         

	}
	public List<RssEntry> getFeeds() {
		gerarListaFeeds();
		return feeds;
	}


	public void setFeeds(List<RssEntry> feeds) {
		this.feeds = feeds;
	}
	
}
