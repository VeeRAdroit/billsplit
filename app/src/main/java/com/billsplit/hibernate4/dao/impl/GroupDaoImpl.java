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

import com.billsplit.hibernate4.dao.GroupDao;
import com.billsplit.hibernate4.dao.HibernateSessionFactory;
import com.billsplit.hibernate4.model.Group;

/**
 * @author veeraj
 *
 */
@Repository
public class GroupDaoImpl implements GroupDao {

	@Autowired
	HibernateSessionFactory hibernateSessionFactory;

	public String addGroup(Group group) {
		Session session = hibernateSessionFactory.getSession();
		return (String) session.save(group);
	}

	public Group getGroup(String groupId) {
		Session session = hibernateSessionFactory.getSession();
		Group group = (Group) session.createCriteria(Group.class)
				.add(Restrictions.eq("groupId", groupId)).uniqueResult();

		return group;
	}

	public boolean updateGroup(Group group) {
		Session session = hibernateSessionFactory.getSession();
		session.update(group);
		return true;
	}

	public boolean deleteGroup(Group group) {
		Session session = hibernateSessionFactory.getSession();
		session.delete(group);
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Group> getAllGroups() {
		Session session = hibernateSessionFactory.getSession();
		List<Group> groups = (List<Group>) session
				.createCriteria(Group.class)
				.setResultTransformer(
						DistinctRootEntityResultTransformer.INSTANCE).list();
		return groups;
	}

}
