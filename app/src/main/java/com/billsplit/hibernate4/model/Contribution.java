/**
 * 
 */
package com.billsplit.hibernate4.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.billsplit.constant.Currency;

/**
 * @author veeraj
 *
 */
@Embeddable
public class Contribution implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8533169591295806373L;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "share")
	private float share;

	@Column(name = "currency")
	@Enumerated(EnumType.STRING)
	private Currency currency;
	
	public Contribution() {
	}

	public Contribution(User user, float share) {
		this.user = user;
		this.share = share;
		this.currency = user.getCurrency();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public float getShare() {
		return share;
	}

	public void setShare(float share) {
		this.share = share;
	}
	

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {

		return String
				.format("{ person:%s, share:%f, currency:%s }", user.getUserId(), share, currency.name());

	}

}
