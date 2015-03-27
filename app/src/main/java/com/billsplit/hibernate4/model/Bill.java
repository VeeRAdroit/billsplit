/**
 * 
 */
package com.billsplit.hibernate4.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.billsplit.helper.ContributionHelper;

/**
 * @author veeraj
 *
 */
@Entity
@Proxy(lazy = false)
@Table(name = "bills")
public class Bill implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3802552880347741911L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "bill_no")
	private long billNo;

	@Column(name = "total_amount")
	private float totalAmount;

	@Column(name = "description")
	private String description;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Group.class)
	private Group group;

	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@CollectionTable(name = "payable_contributions", joinColumns = @JoinColumn(name = "bill_no"))
	private List<Contribution> payableContribution;

	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@CollectionTable(name = "paid_contributions", joinColumns = @JoinColumn(name = "bill_no"))
	private List<Contribution> paidContribution;

	public Bill() {
	}

	public Bill(String description, float totalAmount, Group group,
			List<Contribution> payableContribution,
			List<Contribution> paidContribution) {
		ContributionHelper.validateContribution(totalAmount,
				payableContribution, paidContribution, group);
		this.description = description;
		this.totalAmount = totalAmount;
		this.group = group;
		this.payableContribution = payableContribution;
		this.paidContribution = paidContribution;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<Contribution> getPayableContribution() {
		return payableContribution;
	}

	public void setPayableContribution(List<Contribution> payableContribution) {
		this.payableContribution = payableContribution;
	}

	public List<Contribution> getPaidContribution() {
		return paidContribution;
	}

	public void setPaidContribution(List<Contribution> paidContribution) {
		this.paidContribution = paidContribution;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {

		return String
				.format("Bill{ billNo:%d, totalAmount:%f, desc:%s, groupId:%s, contribution:[%s], paidBy:[%s] }",
						billNo, totalAmount, description, group.getGroupId(),
						StringUtils.join(payableContribution, ","),
						StringUtils.join(paidContribution, ","));

	}
}
