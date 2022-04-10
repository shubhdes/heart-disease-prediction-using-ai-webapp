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
import com.hdp.vo.LoginVO;

public class LoginDAO {

	private static final Logger logger = LogManager.getLogger(LoginDAO.class);

	private static final String loginQuery = "SELECT * FROM login, customers WHERE login.userid=TRIM(?) AND login.password=TRIM(?) and login.userid=customers.emailid";

	private static final String userExists = "SELECT * FROM login, customers WHERE login.userid=TRIM(?) and login.userid=customers.emailid";

	private static final String newUserQuery = "INSERT INTO login (userid, password) VALUES (TRIM(?), TRIM(?))";

	private static final String passwordChangeQuery = "UPDATE login SET login.password=TRIM(?) WHERE login.userid=TRIM(?)";

	public Optional<CustomerVO> auth(final LoginVO loginVO) throws SQLException {
		// pull connection from pool
		final Connection connection = DBConnectionUtils.connection();
		PreparedStatement prepStat = null;
		try {
			prepStat = connection.prepareStatement(loginQuery);
			prepStat.setString(1, loginVO.getUserId());
			prepStat.setString(2, loginVO.getPassword());

			logger.info("SQL loginQuery:" + loginQuery + " parameters" + loginVO);

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

	public Optional<CustomerVO> userExists(final String userId) throws SQLException {
		// pull connection from pool
		final Connection connection = DBConnectionUtils.connection();
		PreparedStatement prepStat = null;
		try {
			prepStat = connection.prepareStatement(userExists);
			prepStat.setString(1, userId);

			logger.info("SQL userExists:" + userExists + " parameters userId:" + userId);

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

	public void newUser(final LoginVO loginVO) throws SQLException {
		// pull connection from pool
		final Connection connection = DBConnectionUtils.connection();
		PreparedStatement prepStat = null;
		try {
			prepStat = connection.prepareStatement(newUserQuery);
			prepStat.setString(1, loginVO.getUserId());
			prepStat.setString(2, loginVO.getPassword());

			logger.info("SQL newUserQuery:" + newUserQuery + " parameters " + loginVO);

			prepStat.executeUpdate();
		} finally {
			// closing resources
			if (null != prepStat) {
				prepStat.close();
			}
		}
	}

	public void passwordChange(final LoginVO loginVO) throws SQLException {
		// pull connection from pool
		final Connection connection = DBConnectionUtils.connection();
		PreparedStatement prepStat = null;
		try {
			prepStat = connection.prepareStatement(passwordChangeQuery);
			prepStat.setString(1, loginVO.getPassword());
			prepStat.setString(2, loginVO.getUserId());

			logger.info("SQL passwordChangeQuery:" + passwordChangeQuery + " parameters " + loginVO);
			prepStat.executeUpdate();
		} finally {
			// closing resources
			if (null != prepStat) {
				prepStat.close();
			}
		}
	}
}
