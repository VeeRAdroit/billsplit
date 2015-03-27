/**
 * 
 */
package com.billsplit.hibernate4.dao;

import org.hibernate.Session;

/**
 * @author veeraj
 *
 */
public interface HibernateSessionFactory {

	Session getSession();
	
}
