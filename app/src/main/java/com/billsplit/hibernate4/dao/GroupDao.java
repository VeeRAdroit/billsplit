/**
 * 
 */
package com.billsplit.hibernate4.dao;

import java.util.List;

import com.billsplit.hibernate4.model.Group;

/**
 * @author veeraj
 *
 */
public interface GroupDao {

	String addGroup(Group group);

	Group getGroup(String groupId);
	
	List<Group> getAllGroups();
	
	boolean updateGroup(Group group);

	boolean deleteGroup(Group group);
	
}
