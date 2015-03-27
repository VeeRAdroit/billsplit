/**
 * 
 */
package com.billsplit.helper;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * @author veeraj
 *
 */
public class GroupIdGenerator implements IdentifierGenerator {

	private static final String GROUP_ID_PREFIX = "Group";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hibernate.id.IdentifierGenerator#generate(org.hibernate.engine.spi
	 * .SessionImplementor, java.lang.Object)
	 */
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		Connection connection = session.connection();
		try {

			PreparedStatement ps = connection
					.prepareStatement("SELECT COUNT(group_id) as value from billsplit.groups");

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("value");
				String code = GROUP_ID_PREFIX + new Integer(id+1).toString();
				return code;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
