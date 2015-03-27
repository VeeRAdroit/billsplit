/**
 * 
 */
package com.billsplit.hibernate4.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.billsplit.hibernate4.dao.HibernateSessionFactory;

/**
 * @author veeraj
 *
 */
public class HibernateSessionFactoryImpl implements HibernateSessionFactory{

    private SessionFactory sessionFactory;

    public Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
