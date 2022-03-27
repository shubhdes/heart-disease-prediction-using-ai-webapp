package com.hdp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hdp.utils.DBConnectionUtils;
import com.hdp.utils.DBUtils;
import com.hdp.vo.CustomerVO;

public class RegistrationDAO {

	private static final Logger logger = LogManager.getLogger(RegistrationDAO.class);

	private static final String newUserQuery = "INSERT INTO customers VALUES (TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?))";

	private static final String updateUserQuery = "UPDATE customers SET fname=TRIM(?), lname=TRIM(?), mobile=TRIM(?), secret_question=TRIM(?), answer=TRIM(?) WHERE emailid=TRIM(?)";

	private static final String existingUserQuery = "SELECT * FROM customers WHERE customers.emailid=TRIM(?)";

	public void newUser(final String fname, final String lname, final String emailId, final String mobile,
			final String secretQuestion, final String answer) throws SQLException {
		// pull connection from pool
		final Connection connection = DBConnectionUtils.connection();
		PreparedStatement prepStat = null;
		try {
			prepStat = connection.prepareStatement(newUserQuery);
			prepStat.setString(1, fname);
			prepStat.setString(2, lname);
			prepStat.setString(3, emailId);
			prepStat.setString(4, mobile);
			prepStat.setString(5, secretQuestion);
			prepStat.setString(6, answer);

			logger.info("SQL newUserQuery:" + newUserQuery + " parameters fname:" + fname + " lname:" + lname
					+ " emailId:" + emailId + " mobile:" + mobile + " secretQuestion:" + secretQuestion + " answer:"
					+ answer);

			prepStat.executeUpdate();
		} finally {
			// closing resources
			if (null != prepStat) {
				prepStat.close();
			}
		}
	}

	public void upateUser(final String fname, final String lname, final String emailId, final String mobile,
			final String secretQuestion, final String answer) throws SQLException {
		// pull connection from pool
		final Connection connection = DBConnectionUtils.connection();
		PreparedStatement prepStat = null;
		try {
			prepStat = connection.prepareStatement(updateUserQuery);
			prepStat.setString(1, fname);
			prepStat.setString(2, lname);
			prepStat.setString(3, mobile);
			prepStat.setString(4, secretQuestion);
			prepStat.setString(5, answer);
			prepStat.setString(6, emailId);

			logger.info("SQL newUserQuery:" + updateUserQuery + " parameters fname:" + fname + " lname:" + lname
					+ " mobile:" + mobile + " secretQuestion:" + secretQuestion + " answer:" + answer + " emailId:"
					+ emailId);

			prepStat.executeUpdate();
		} finally {
			// closing resources
			if (null != prepStat) {
				prepStat.close();
			}
		}
	}

	public Optional<CustomerVO> existingUser(final String username) throws SQLException {
		// pull connection from pool
		final Connection connection = DBConnectionUtils.connection();
		PreparedStatement prepStat = null;
		try {
			prepStat = connection.prepareStatement(existingUserQuery);
			prepStat.setString(1, username);

			logger.info("SQL existingUserQuery:" + existingUserQuery + " parameters username:" + username);

			ResultSet result = prepStat.executeQuery();
			if (!result.next()) {
				// user does not exist
				return Optional.empty();
			}

			// user does exist
			return Optional.of(new CustomerVO(result.getString(DBUtils.fname), result.getString(DBUtils.lname),
					result.getString(DBUtils.emailId), result.getString(DBUtils.mobile),
					result.getString(DBUtils.secretQuestion), result.getString(DBUtils.answer)));
		} finally {
			// closing resources
			if (null != prepStat) {
				prepStat.close();
			}
		}
	}
}
