/**
 * 
 */
package com.billsplit.hibernate4.controller.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.billsplit.hibernate4.controller.BillController;
import com.billsplit.hibernate4.dao.BillDao;
import com.billsplit.hibernate4.model.Bill;
import com.billsplit.hibernate4.model.Group;

/**
 * @author veeraj
 *
 */
@Transactional
public class BillControllerImpl implements BillController {

	@Autowired
	private BillDao billDao;

	public long addBill(Bill bill) {
		return billDao.addBill(bill);
	}

	public Bill getBill(long billNo) {
		return billDao.getBill(billNo);
	}

	public boolean updateBill(Bill bill) {
		return billDao.updateBill(bill);
	}

	public boolean deleteBill(Bill bill) {
		return billDao.deleteBill(bill);
	}

	public List<Bill> getBillsForGroup(Group group) {
		return billDao.getBillsForGroup(group);
	}

	public List<Bill> getAllBills() {
		return billDao.getAllBills();
	}

}
