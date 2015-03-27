/**
 * 
 */
package com.billsplit.hibernate4.controller;

import java.util.List;

import com.billsplit.hibernate4.model.Bill;
import com.billsplit.hibernate4.model.Group;


/**
 * @author veeraj
 *
 */
public interface BillController {

	long addBill(Bill bill);

	Bill getBill(long billNo);
	
	List<Bill> getBillsForGroup(Group group);
	
	List<Bill> getAllBills();

	boolean updateBill(Bill bill);

	boolean deleteBill(Bill bill);

}
