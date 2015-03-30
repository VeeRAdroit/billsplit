/**
 * 
 */
package com.billsplit.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.billsplit.constant.Currency;
import com.billsplit.constant.ShareType;
import com.billsplit.hibernate4.model.Contribution;
import com.billsplit.hibernate4.model.Group;
import com.billsplit.hibernate4.model.User;

/**
 * @author veeraj
 *
 */
public class ContributionHelper {

	public static List<Contribution> splitEqually(Currency billCurrency, float totalAmount,
			List<User> members) {
		List<Contribution> contributionList = new ArrayList<Contribution>();
		float share = 0;
		for (User user : members) {
			share = (float) totalAmount / members.size();
			float shareInMemberCurrency = CurrencyHelper.convertCurrencyValue(
					share, billCurrency, user.getCurrency());
			Contribution contribution = new Contribution(user, shareInMemberCurrency);
			contributionList.add(contribution);
		}
		return contributionList;
	}

	public static List<Contribution> splitVariably(Currency billCurrency,
			float totalAmount, ShareType shareType, List<User> members,
			List<Float> shares) {

		Validate.isTrue(shares.size() == members.size(),
				"No of Shares : %d is not equal to No of Members = %d",
				shares.size(), members.size());

		List<Float> shareAmounts = new ArrayList<Float>();

		Float factor = new Float(1);

		if (!ShareType.AMOUNT.equals(shareType)) {
			factor = ShareType.RATIO.equals(shareType) ? totalAmount
					: totalAmount / 100;
		}
		for (Float share : shares) {
			shareAmounts.add((share == null) ? new Float(0) : share * factor);
		}

		Validate.isTrue(validateShareAmounts(shareAmounts, totalAmount));

		List<Contribution> contributionList = new ArrayList<Contribution>();

		for (int i = 0; i < members.size(); i++) {
			float share = ShareHelper.calculateShare(totalAmount, shareType,
					shares.get(i));
			float shareInMemberCurrency = CurrencyHelper.convertCurrencyValue(
					share, billCurrency, members.get(i).getCurrency());
			Contribution contribution = new Contribution(members.get(i), shareInMemberCurrency);
			contributionList.add(contribution);
		}
		return contributionList;
	}

	public static boolean validateContribution(Currency billCurrency,Float totalAmount,
			List<Contribution> payableContributions,
			List<Contribution> paidContributions, Group group) {

		Validate.isTrue(validateContributedMembers(payableContributions, group));
		Validate.isTrue(validateContributedMembers(paidContributions, group));

		Validate.isTrue(validateShareAmounts(
				ShareHelper.getShareAmounts(payableContributions,billCurrency), totalAmount));
		Validate.isTrue(validateShareAmounts(
				ShareHelper.getShareAmounts(paidContributions,billCurrency), totalAmount));

		return true;
	}

	public static boolean validateContributedMembers(
			List<Contribution> contributions, Group group) {

		for (Contribution contribution : contributions) {
			if (!group.getMembers().contains(contribution.getUser())) {
				System.out.println(String.format(
						"Contributed User : %s is not a member of group : %s ",
						contribution.getUser(), group));
				return false;
			}
		}
		return true;
	}

	public static boolean validateShareAmounts(List<Float> shareAmounts,
			float totalAmount) {

		Float sumOfShareAmounts = new Float(0);
		for (Float shareAmount : shareAmounts) {
			sumOfShareAmounts += shareAmount;
		}

		if (Math.abs(totalAmount - sumOfShareAmounts) > 0.001f) {
			System.out.println(String.format(
					"Total Amount = %f not equal to Sum of Share Amounts : %f",
					totalAmount, sumOfShareAmounts));
			return false;
		}

		return true;
	}

}
