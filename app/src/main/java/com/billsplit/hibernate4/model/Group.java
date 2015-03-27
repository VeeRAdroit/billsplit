/**
 * 
 */
package com.billsplit.hibernate4.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

/**
 * @author veeraj
 *
 */
@Entity
@Proxy(lazy=false)
@Table(name = "groups")
public class Group  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1650028182957173389L;

	@Id
	@Column(name = "group_id")
	@GenericGenerator(name = "sequence_group_id", strategy = "com.billsplit.helper.GroupIdGenerator")
	@GeneratedValue(generator = "sequence_group_id")
	private String groupId;

	@Column(name = "name")
	private String name;

	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<User> members;

	public Group(){}
	
	public Group(String name, List<User> members) {
		this.name = name;
		this.members = members;
	}


	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	@Override
	public String toString() {
		return String.format("Group{ id=%s , name=%s, members=%s }", groupId,
				name, StringUtils.join(members, ","));
	}
}
