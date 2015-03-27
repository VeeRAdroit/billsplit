/**
 * 
 */
package com.billsplit.hibernate4.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.billsplit.hibernate4.dao.BillDao;
import com.billsplit.hibernate4.dao.HibernateSessionFactory;
import com.billsplit.hibernate4.model.Bill;
import com.billsplit.hibernate4.model.Group;

/**
 * @author veeraj
 *
 */
@Repository
public class BillDaoImpl implements BillDao {

	@Autowired
	HibernateSessionFactory hibernateSessionFactory;

	public long addBill(Bill bill) {
		Session session = hibernateSessionFactory.getSession();
		session.save(bill);

		return (Long) session.save(bill);
	}

	public Bill getBill(long billNo) {
		Session session = hibernateSessionFactory.getSession();
		Bill bill = (Bill) session.createCriteria(Bill.class)
				.add(Restrictions.eq("billNo", billNo)).uniqueResult();

		return bill;
	}

	public boolean updateBill(Bill bill) {
		Session session = hibernateSessionFactory.getSession();
		session.update(bill);
		return true;
	}

	public boolean deleteBill(Bill bill) {
		Session session = hibernateSessionFactory.getSession();
		session.delete(bill);
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Bill> getBillsForGroup(Group group) {
		Session session = hibernateSessionFactory.getSession();
		List<Bill> bills = (List<Bill>) session
				.createCriteria(Bill.class)
				.add(Restrictions.eq("group", group))
				.setResultTransformer(
						DistinctRootEntityResultTransformer.INSTANCE).list();
		return bills;
	}

	@SuppressWarnings("unchecked")
	public List<Bill> getAllBills() {
		Session session = hibernateSessionFactory.getSession();
		List<Bill> bills = (List<Bill>) session
				.createCriteria(Bill.class)
				.setResultTransformer(
						DistinctRootEntityResultTransformer.INSTANCE).list();
		return bills;
	}

}
