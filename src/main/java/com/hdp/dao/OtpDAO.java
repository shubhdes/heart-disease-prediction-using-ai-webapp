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
import com.hdp.vo.OtpVO;

public class OtpDAO {

	private static final Logger logger = LogManager.getLogger(OtpDAO.class);

	private static final String newOtpQuery = "INSERT INTO otp (emailid, random, status, ts) VALUES (TRIM(?), TRIM(?), TRIM(?), TRIM(?))";

	private static final String findOtpQuery = "SELECT otp_id, emailid, random, status, ts FROM otp WHERE random=TRIM(?) AND emailid=TRIM(?) AND status=TRIM(?)";

	private static final String updateOtpQuery = "UPDATE otp SET status=TRIM(?) WHERE otp_id=TRIM(?)";

	public void newOtp(final OtpVO otpVO) throws SQLException {
		// pull connection from pool
		final Connection connection = DBConnectionUtils.connection();
		PreparedStatement prepStat = null;
		try {
			prepStat = connection.prepareStatement(newOtpQuery);
			prepStat.setString(1, otpVO.getEmailId());
			prepStat.setString(2, otpVO.getRandom());
			prepStat.setString(3, otpVO.getStatus());
			prepStat.setString(4, String.valueOf(otpVO.getTs()));

			logger.info("SQL newOtpQuery:" + newOtpQuery + " parameters " + otpVO);

			prepStat.executeUpdate();
		} finally {
			// closing resources
			if (null != prepStat) {
				prepStat.close();
			}
		}
	}

	public Optional<OtpVO> findOtp(final OtpVO otpVO) throws SQLException {
		// pull connection from pool
		final Connection connection = DBConnectionUtils.connection();
		PreparedStatement prepStat = null;
		try {
			prepStat = connection.prepareStatement(findOtpQuery);
			prepStat.setString(1, otpVO.getRandom());
			prepStat.setString(2, otpVO.getEmailId());
			prepStat.setString(3, otpVO.getStatus());

			logger.info("SQL newOtpQuery:" + findOtpQuery + " parameters " + otpVO);

			ResultSet result = prepStat.executeQuery();
			if (!result.next()) {
				// user does not exist
				return Optional.empty();
			}

			// user does exist
			return Optional.of(new OtpVO(result.getLong(DBUtils.otpId), result.getString(DBUtils.emailId),
					result.getString(DBUtils.random), result.getString(DBUtils.status),
					Long.valueOf(result.getString(DBUtils.ts))));
		} finally {
			// closing resources
			if (null != prepStat) {
				prepStat.close();
			}
		}
	}

	public void updateOtp(final OtpVO otpVO) throws SQLException {
		// pull connection from pool
		final Connection connection = DBConnectionUtils.connection();
		PreparedStatement prepStat = null;
		try {
			prepStat = connection.prepareStatement(updateOtpQuery);
			prepStat.setString(1, otpVO.getStatus());
			prepStat.setLong(2, otpVO.getOtpId());

			logger.info("SQL updateOtpQuery:" + updateOtpQuery + " parameters " + otpVO);

			prepStat.executeUpdate();
		} finally {
			// closing resources
			if (null != prepStat) {
				prepStat.close();
			}
		}
	}
}
