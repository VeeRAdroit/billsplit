/**
 * 
 */
package com.billsplit.hibernate4.controller.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.billsplit.hibernate4.controller.GroupController;
import com.billsplit.hibernate4.dao.GroupDao;
import com.billsplit.hibernate4.model.Group;
import com.billsplit.hibernate4.model.User;

/**
 * @author veeraj
 *
 */
@Transactional
public class GroupControllerImpl implements GroupController {

	@Autowired
	private GroupDao groupDao;

	public String addGroup(Group group) {
		return groupDao.addGroup(group);
	}

	public Group getGroup(String groupId) {
		return groupDao.getGroup(groupId);
	}

	public boolean updateGroup(Group group) {
		return groupDao.updateGroup(group);
	}

	public boolean deleteGroup(Group group) {
		return groupDao.deleteGroup(group);
	}

	public List<Group> getGroupsForUser(User user) {
		List<Group> groupsForUser = new ArrayList<Group>();
		for (Group group : groupDao.getAllGroups()) {
			if (group.getMembers().contains(user)) {
				groupsForUser.add(group);
			}
		}
		return groupsForUser;
	}

	public List<Group> getAllGroups() {
		return groupDao.getAllGroups();
	}

}
