/**
 * 
 */
package com.billsplit.hibernate4.controller;

import java.util.List;

import com.billsplit.hibernate4.model.User;

/**
 * @author veeraj
 *
 */
public interface UserController {

	String addUser(User user);

	User getUser(String userId);

	List<User> getAllUsers();

	boolean updateUser(User user);

	boolean deleteUser(User user);
}
