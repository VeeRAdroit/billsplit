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
				Float convertedShare = CurrencyHelper.convertCurrencyValue(
						share, payable.getCurrency(), payable.getUser()
								.getCurrency());
				if (balanceMap.containsKey(userId)) {
					balanceMap.put(userId, balanceMap.get(userId)
							+ convertedShare);
				} else {
					balanceMap.put(userId, convertedShare);
				}
			}
			List<Contribution> paidContribution = bill.getPaidContribution();
			for (Contribution paid : paidContribution) {
				String userId = paid.getUser().getUserId();
				Float share = paid.getShare();
				Float convertedShare = CurrencyHelper
						.convertCurrencyValue(share, paid.getCurrency(), paid
								.getUser().getCurrency());
				if (balanceMap.containsKey(userId)) {
					balanceMap.put(userId, balanceMap.get(userId)
							- convertedShare);
				}
			}
		}

		return balanceMap;
	}
}
