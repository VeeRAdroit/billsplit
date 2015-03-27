/**
 * 
 */
package com.billsplit.hibernate4.controller.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.billsplit.helper.BalanceHelper;
import com.billsplit.hibernate4.controller.BalanceController;
import com.billsplit.hibernate4.controller.BillController;
import com.billsplit.hibernate4.controller.GroupController;
import com.billsplit.hibernate4.model.Bill;
import com.billsplit.hibernate4.model.Group;
import com.billsplit.hibernate4.model.User;

/**
 * @author veeraj
 *
 */
@Transactional
public class BalanceControllerImpl implements BalanceController {

	@Autowired
	private BillController billController;

	@Autowired
	private GroupController groupController;

	public Float getOverallBalanceForUser(User user) {

		Map<String, Float> balanceMap = getOverallBalanceSummary();
		Float overallBalanceForUser = balanceMap.containsKey(user.getUserId()) ? balanceMap
				.get(user.getUserId()) : new Float(0);

		return overallBalanceForUser;
	}

	public Map<String, Float> getGroupWiseBalanceForUser(User user) {
		Map<String, Float> groupWiseBalanceMap = new HashMap<String, Float>();
		for (Group group : groupController.getGroupsForUser(user)) {
			Map<String, Float> balanceMap = getBalanceForGroup(group);
			Float groupBalance = balanceMap.isEmpty() ? new Float(0)
					: balanceMap.get(user.getUserId());
			groupWiseBalanceMap.put(group.getGroupId(), groupBalance);
		}
		return groupWiseBalanceMap;
	}

	public Float getGroupBalanceForUser(User user, Group group) {
		Float balance = !(group.getMembers().contains(user)) ? new Float(0)
				: getGroupWiseBalanceForUser(user).get(group.getGroupId());
		return balance;
	}

	public Map<String, Float> getBalanceForGroup(Group group) {
		List<Bill> billList = billController.getBillsForGroup(group);
		Map<String, Float> balanceForGroup = billList.isEmpty() ? new HashMap<String, Float>()
				: BalanceHelper.calculateBalanceForBills(billList);
		return balanceForGroup;
	}

	public Map<String, Float> getOverallBalanceSummary() {
		Map<String, Float> overallBalanceMap = new HashMap<String, Float>();
		List<Bill> billList = billController.getAllBills();
		overallBalanceMap = BalanceHelper.calculateBalanceForBills(billList);
		return overallBalanceMap;
	}
}
