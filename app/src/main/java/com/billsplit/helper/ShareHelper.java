/**
 * 
 */
package com.billsplit.helper;

import java.util.ArrayList;
import java.util.List;

import com.billsplit.constant.Currency;
import com.billsplit.constant.ShareType;
import com.billsplit.hibernate4.model.Contribution;

/**
 * @author veeraj
 *
 */
public class ShareHelper {

	public static float calculateShare(float totalAmount, ShareType shareType,
			float shareValue) {

		if (ShareType.AMOUNT.equals(shareType)) {
			return shareValue;
		}
		if (ShareType.RATIO.equals(shareType)) {
			return totalAmount * shareValue;
		}
		if (ShareType.PERCENTAGE.equals(shareType)) {
			return totalAmount * (shareValue/100);
		}
		return 0;
	}
	
	public static List<Float> getShareAmounts(List<Contribution> contributions, Currency shareCurrency) {

		List<Float> shareAmounts = new ArrayList<Float>();
		for (Contribution contribution : contributions) {
			Float share = contribution.getShare();
			Currency userCurrency = contribution.getUser().getCurrency();
			Float shareAmount = CurrencyHelper.convertCurrencyValue(share,userCurrency,shareCurrency);
			
			shareAmounts.add(shareAmount);
		}
		
		return shareAmounts;
	}

}
