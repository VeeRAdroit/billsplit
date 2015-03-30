/**
 * 
 */
package com.billsplit.hibernate4.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.billsplit.constant.Currency;

/**
 * @author veeraj
 *
 */
@Entity
@Proxy(lazy=false)
@Table(name = "users")
public class User  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -255635319607729810L;

	@Id
	@Column(name = "user_id", nullable=false)
	private String userId;

	@Column(name = "name", nullable=false)
	private String name;

	@Column(name = "currency", nullable=false)
	@Enumerated(EnumType.STRING)
	private Currency currency;

	public User(){}
	
	public User(String userId, String name, Currency currency) {
		this.userId = userId;
		this.name = name;
		this.currency = currency;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}


	@Override
	public String toString() {
		return String.format("User{ id=%s , name=%s, currency=%s }", userId, name, currency.name());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	
}
