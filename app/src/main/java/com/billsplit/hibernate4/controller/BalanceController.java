/**
 * 
 */
package com.billsplit.hibernate4.controller;

import java.util.Map;

import com.billsplit.hibernate4.model.Group;
import com.billsplit.hibernate4.model.User;

/**
 * @author veeraj
 *
 */
public interface BalanceController {
	
	public Float getGroupBalanceForUser(User user,Group group);
	
	public Float getOverallBalanceForUser(User user);	
	
	public Map<String,Float> getGroupWiseBalanceForUser(User user);
	
	public Map<String,Float> getBalanceForGroup(Group group);
	
	public Map<String,Float> getOverallBalanceSummary();

}
