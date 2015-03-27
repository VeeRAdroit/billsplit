/**
 * 
 */
package com.billsplit.hibernate4.controller;

import java.util.List;

import com.billsplit.hibernate4.model.Group;
import com.billsplit.hibernate4.model.User;

/**
 * @author veeraj
 *
 */
public interface GroupController {

	String addGroup(Group group);

	Group getGroup(String groupId);
	
	List<Group> getGroupsForUser(User user);
	
	List<Group> getAllGroups();

	boolean updateGroup(Group group);

	boolean deleteGroup(Group group);

}
