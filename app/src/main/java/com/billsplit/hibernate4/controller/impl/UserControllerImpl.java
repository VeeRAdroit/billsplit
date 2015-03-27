/**
 * 
 */
package com.billsplit.hibernate4.controller.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.billsplit.hibernate4.controller.UserController;
import com.billsplit.hibernate4.dao.UserDao;
import com.billsplit.hibernate4.model.User;

/**
 * @author veeraj
 *
 */
@Transactional
public class UserControllerImpl implements UserController {

	@Autowired
	private UserDao userDao;

	public String addUser(User user) {
		return userDao.addUser(user);
	}

	public User getUser(String userId) {
		return userDao.getUser(userId);
	}

	public boolean updateUser(User user) {
		return userDao.updateUser(user);
	}

	public boolean deleteUser(User user) {
		return userDao.deleteUser(user);
	}

	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

}
