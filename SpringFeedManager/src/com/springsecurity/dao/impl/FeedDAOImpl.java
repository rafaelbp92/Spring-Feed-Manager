package com.springsecurity.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springsecurity.dao.FeedDAO;
import com.springsecurity.entities.Feed;
import com.springsecurity.entities.User;

@Repository
@Transactional
public class FeedDAOImpl implements FeedDAO{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Feed feed) {
		entityManager.persist(feed);	
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Feed getByUrlAndUser(String url, User user) {
		TypedQuery<Feed> query = entityManager.createQuery(
				"select x from Feed x where x.user.id = ? and x.url = ?",
				Feed.class);

		query.setParameter(1, user.getId());
		query.setParameter(2,url);

		List l = query.getResultList();

		if (l == null || l.isEmpty() || l.size() > 1) {
			return null;
		}
		return (Feed) l.get(0);
	}
	
}
