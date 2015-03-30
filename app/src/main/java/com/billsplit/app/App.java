package com.billsplit.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.billsplit.constant.Currency;
import com.billsplit.constant.ShareType;
import com.billsplit.helper.ContributionHelper;
import com.billsplit.hibernate4.controller.BalanceController;
import com.billsplit.hibernate4.controller.BillController;
import com.billsplit.hibernate4.controller.GroupController;
import com.billsplit.hibernate4.controller.UserController;
import com.billsplit.hibernate4.model.Bill;
import com.billsplit.hibernate4.model.Contribution;
import com.billsplit.hibernate4.model.Group;
import com.billsplit.hibernate4.model.User;

/**
 * Hello world!
 *
 */
@Component
public class App {

	public static final String UNDERLINE = "-----------------------------------";

	Currency preferredCurrency;

	@Autowired
	UserController userController;

	@Autowired
	GroupController groupController;

	@Autowired
	BillController billController;

	@Autowired
	BalanceController balanceController;

	public Currency getPreferredCurrency() {
		return preferredCurrency;
	}

	public void setPreferredCurrency(Currency preferredCurrency) {
		this.preferredCurrency = preferredCurrency;
	}

	public UserController getUserController() {
		return userController;
	}

	public void setUserController(UserController userController) {
		this.userController = userController;
	}

	public GroupController getGroupController() {
		return groupController;
	}

	public void setGroupController(GroupController groupController) {
		this.groupController = groupController;
	}

	public BillController getBillController() {
		return billController;
	}

	public void setBillController(BillController billController) {
		this.billController = billController;
	}

	public BalanceController getBalanceController() {
		return balanceController;
	}

	public void setBalanceController(BalanceController balanceController) {
		this.balanceController = balanceController;
	}

	public List<User> createUsers() {

		List<User> createdUsers = new ArrayList<User>();
		List<Currency> supportedCurrencies = Arrays.asList(Currency.values());
		Random random = new Random();
		for (int i = 1; i < 4; i++) {
			String userId = "user" + i + "@xyz.com";
			String name = "User " + i;
			Currency currency = supportedCurrencies.get(random
					.nextInt(supportedCurrencies.size()));
			User user = addUser(userId, name, currency);
			if (user != null) {
				createdUsers.add(user);
			}
		}
		return createdUsers;

	}

	public void createGroups() {

		List<User> users = createUsers();
		Random random = new Random();
		//for (int i = 1; i < 5; i++) {
		int i=1;
			String name = "User Group " + i;
			addGroup(name, users);
		//	addGroup(name, users.subList(random.nextInt(3), i + 3));
		//}

	}

	public User addUser(String userId, String name, Currency currency) {

		User user = new User(userId, name, currency);
		System.out.println("<<<<<<<<<Added User>>>>>>>>");
		System.out.println(UNDERLINE);
		String addedUserId = userController.addUser(user);
		if (addedUserId != null) {
			user = userController.getUser(addedUserId);
			System.out.println(user);
			System.out.println(UNDERLINE);
			return user;
		}
		return null;

	}

	public Group addGroup(String name, List<User> members) {

		Group group = new Group(name, members);
		System.out.println("<<<<<<<<<Added Group>>>>>>>>");
		System.out.println(UNDERLINE);
		String addedGroupId = groupController.addGroup(group);
		if (addedGroupId != null) {
			group = groupController.getGroup(addedGroupId);
			System.out.println(group);
			System.out.println(UNDERLINE);

			return group;
		}
		return null;
	}

	public Bill addBill(String description, float totalAmount, Group group,
			List<Contribution> payableContribution,
			List<Contribution> paidContribution) {
		
		Validate.notNull(getPreferredCurrency());
		Bill bill = new Bill(description, totalAmount, getPreferredCurrency(), group,
				payableContribution, paidContribution);
		System.out.println("<<<<<<<<<Added Bill>>>>>>>>");
		System.out.println(UNDERLINE);
		long addedBillNo = billController.addBill(bill);
		if (addedBillNo != 0) {
			bill = billController.getBill(addedBillNo);
			System.out.println(bill);
			System.out.println(UNDERLINE);
			return bill;
		}
		return null;

	}
	
	public void createSingleBill()
	{
		String description = "Bill 1 description";
		Group group = groupController.getAllGroups().get(0);
		Float totalAmount = 1000f;
		List<Contribution> payableContribution = ContributionHelper
				.splitEqually(getPreferredCurrency(),totalAmount, group.getMembers());
		List<Contribution> paidContribution = new ArrayList<Contribution>();
		int memberCount = group.getMembers().size();
		List<Float> shares = new ArrayList<Float>(memberCount);
		shares.add(new Float(50));
		shares.add(new Float(30));
		shares.add(new Float(20));

		
		paidContribution = ContributionHelper.splitVariably(getPreferredCurrency(),
				totalAmount, ShareType.PERCENTAGE, group.getMembers(),
				shares);
		
		addBill(description, totalAmount, group, payableContribution, paidContribution);

	}

	public void createBills() {
		Validate.notNull(getPreferredCurrency());
		Random random = new Random();

		for (int i = 1; i < 5; i++) {

			String groupId = "Group" + (random.nextInt(3) + 1);

			String description = "Bill " + i + " Description";
			float totalAmount = i * 100;
			Group group = getGroupController().getGroup(groupId);
			int memberCount = group.getMembers().size();

			List<Contribution> payableContribution = ContributionHelper
					.splitEqually(getPreferredCurrency(),totalAmount, group.getMembers());
			List<Contribution> paidContribution = new ArrayList<Contribution>();

			if (memberCount < 4) {
				paidContribution = ContributionHelper.splitEqually(getPreferredCurrency(),totalAmount,
						group.getMembers());
			} else {
				List<Float> shares = new ArrayList<Float>(memberCount);

				for (int j = 0; j < memberCount; j++) {
					shares.add(j, new Float(0));
				}

				shares.set(0, new Float(50));
				shares.set(1, new Float(30));
				shares.set(2, new Float(20));

				
				paidContribution = ContributionHelper.splitVariably(getPreferredCurrency(),
						totalAmount, ShareType.PERCENTAGE, group.getMembers(),
						shares);
			}

			addBill(description, totalAmount, group, payableContribution,
					paidContribution);
		}
		for (Group group : groupController.getAllGroups()) {
			System.out.println("<<<<<<<<<Bills For " + group.getGroupId()
					+ ">>>>>>>>");
			System.out.println(UNDERLINE);
			List<Bill> billsForGroup = billController.getBillsForGroup(group);
			if (billsForGroup.isEmpty()) {
				System.out.println("No Bills added for this group ! ");
			} else {
				System.out.println(StringUtils.join(billsForGroup, "\n---\n"));
			}
			System.out.println(UNDERLINE);
		}

	}

	private void showBalanceForGroup(Group group) {
		System.out.println("\nBalance For Group : " + group.getGroupId());
		Map<String, Float> balanceForGroup = balanceController
				.getBalanceForGroup(group);
		if (balanceForGroup.isEmpty()) {
			System.out.println("No Bills added for this group ! ");
		} else {
			for (Entry<String, Float> entry : balanceForGroup.entrySet()) {
				User user = userController.getUser(entry.getKey());
				System.out.println(String.format("User : %s , Balance : %.2f, Currency : %s ",
						user.getUserId(), entry.getValue(), user.getCurrency()));
			}
		}

	}

	private void showGroupBalanceForUser(User user, Group group) {
		Float groupBalanceForUser = balanceController.getGroupBalanceForUser(
				user, group);
		System.out.println(String.format(
				"User : %s, Group : %s, Balance : %.2f, Currency : %s", user.getUserId(),
				group.getGroupId(), groupBalanceForUser, user.getCurrency()));

	}

	private void showGroupWiseBalanceForUser(User user) {
		System.out.println("\nGroup Wise Balance for User : "
				+ user.getUserId());
		Map<String, Float> groupWiseBalanceForUser = balanceController
				.getGroupWiseBalanceForUser(user);
		if (groupWiseBalanceForUser.isEmpty()) {
			System.out.println("No Bills for User : "
				+ user.getUserId());
		} else {
			for (Entry<String, Float> entry : groupWiseBalanceForUser
					.entrySet()) {
				System.out.println(String.format(
						"Group : %s , Balance : %.2f , Currency : %s ", entry.getKey(),
						entry.getValue(), user.getCurrency().name()));
			}
		}
	}

	private void showOverallBalanceForUser(User user) {
		System.out.println(String.format("User : %s , Overall Balance : %.2f, Currency : %s ",
				user.getUserId(),
				balanceController.getOverallBalanceForUser(user), user.getCurrency().name()));

	}

	private void showOverallBalanceSummary() {
		for (Entry<String, Float> entry : balanceController
				.getOverallBalanceSummary().entrySet()) {
			User user = userController.getUser(entry.getKey());
			System.out.println(String.format(
					"User : %s , Overall Balance : %.2f, Currency : %s ", user.getUserId(),
					entry.getValue(),user.getCurrency()));
		}

	}

	void showBalanceSummary() {

		List<User> userList = userController.getAllUsers();

		List<Group> groupList = groupController.getAllGroups();

		System.out.println("<<<<<<<<<Testing GroupBalanceForUser >>>>>>>>");
		System.out.println(UNDERLINE);
		for (User user : userList) {
			for (Group group : groupList) {
				showGroupBalanceForUser(user, group);
			}
		}
		System.out.println(UNDERLINE);

		System.out.println("<<<<<<<<<Testing GroupWiseBalanceForUser >>>>>>>>");
		System.out.println(UNDERLINE);
		for (User user : userList) {
			showGroupWiseBalanceForUser(user);
		}
		System.out.println(UNDERLINE);

		System.out.println("<<<<<<<<<Testing BalanceForGroup >>>>>>>>");
		System.out.println(UNDERLINE);
		for (Group group : groupList) {
			showBalanceForGroup(group);
		}
		System.out.println(UNDERLINE);

		System.out.println("<<<<<<<<<Testing OverallBalanceForUser >>>>>>>>");
		System.out.println(UNDERLINE);
		for (User user : userList) {
			showOverallBalanceForUser(user);
		}
		System.out.println(UNDERLINE);

		System.out.println("<<<<<<<<<Testing OverallBalanceSummary >>>>>>>>");
		System.out.println(UNDERLINE);
		showOverallBalanceSummary();
		System.out.println(UNDERLINE);

	}

	public static void main(String[] args) {

		@SuppressWarnings({ "resource" })
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring-beans.xml");

		App app = (App) context.getBean(App.class);


		// Create Groups with Members
		app.createGroups();

		// set preferred currency before adding bills
		app.setPreferredCurrency(Currency.INR);
		
		// Create Bills with group and contributions
		//app.createBills();
		app.createSingleBill();
		// Check Balance for Users
		app.showBalanceSummary();
		
		User user = app.getUserController().getUser("user1@xyz.com");
		user.setCurrency(Currency.INR);
		if(app.getUserController().updateUser(user))
		{
			System.out.println("<<<<<< Changed User1 currency to INR >>>>>");
			// Check Balance for Users
			app.showBalanceSummary();
		}
		
		
		

	}

}
