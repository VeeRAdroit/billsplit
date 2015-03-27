/**
 * 
 */
package com.billsplit.hibernate4.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.billsplit.hibernate4.dao.HibernateSessionFactory;
import com.billsplit.hibernate4.dao.UserDao;
import com.billsplit.hibernate4.model.User;

/**
 * @author veeraj
 *
 */
@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	HibernateSessionFactory hibernateSessionFactory;

	public String addUser(User user) {
		Session session = hibernateSessionFactory.getSession();
		return (String) session.save(user);
	}

	public User getUser(String userId) {
		Session session = hibernateSessionFactory.getSession();
		User user = (User) session.createCriteria(User.class)
				.add(Restrictions.eq("userId", userId)).uniqueResult();

		return user;
	}

	public boolean updateUser(User user) {
		Session session = hibernateSessionFactory.getSession();
		session.update(user);
		return true;
	}

	public boolean deleteUser(User user) {
		Session session = hibernateSessionFactory.getSession();
		session.delete(user);
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		Session session = hibernateSessionFactory.getSession();
		List<User> userList = (List<User>) session
				.createCriteria(User.class)
				.setResultTransformer(
						DistinctRootEntityResultTransformer.INSTANCE).list();

		return userList;
	}

}
