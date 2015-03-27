/**
 * 
 */
package com.billsplit.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billsplit.hibernate4.model.Bill;
import com.billsplit.hibernate4.model.Contribution;

/**
 * @author veeraj
 *
 */
public class BalanceHelper {

	public static Map<String, Float> calculateBalanceForBills(
			List<Bill> billList) {
		Map<String, Float> balanceMap = new HashMap<String, Float>();
		for (Bill bill : billList) {
			List<Contribution> payableContribution = bill
					.getPayableContribution();
			for (Contribution payable : payableContribution) {
				
				String userId = payable.getUser().getUserId();
				Float share = payable.getShare();
				if (balanceMap.containsKey(userId)) {
					balanceMap.put(userId, balanceMap.get(userId) + share);
				} else {
					balanceMap.put(userId, share);
				}
			}
			List<Contribution> paidContribution = bill.getPaidContribution();
			for (Contribution paid : paidContribution) {
				String userId = paid.getUser().getUserId();
				Float share = paid.getShare();
				if (balanceMap.containsKey(userId)) {
					balanceMap.put(userId, balanceMap.get(userId) - share);
				}
			}
		}
		
		return balanceMap;
	}
}
