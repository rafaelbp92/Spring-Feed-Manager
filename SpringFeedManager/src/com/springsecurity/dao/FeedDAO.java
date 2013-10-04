package com.springsecurity.dao;

import com.springsecurity.entities.Feed;
import com.springsecurity.entities.User;

public interface FeedDAO {
	void save(Feed feed);
	Feed getByUrlAndUser(String url, User user);
}
