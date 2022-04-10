package com.hdp.service;

import java.sql.SQLException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hdp.dao.OtpDAO;
import com.hdp.utils.CommonUtils;
import com.hdp.utils.ProcessStatus;
import com.hdp.vo.OtpVO;

public class OtpService {

	private static final Logger logger = LogManager.getLogger(OtpService.class);

	private final OtpDAO otpDAO;

	public OtpService() {
		//
		this.otpDAO = new OtpDAO();
	}

	private String newOTP(final String userId, final ProcessStatus processStatus) throws SQLException {
		// generate otp

		final String otp = CommonUtils.otp();

		final OtpVO otpVO = new OtpVO(userId, otp, processStatus.name(), System.currentTimeMillis());
		otpDAO.newOtp(otpVO);
		return otp;
	}

	public String newUserOTP(final String emailId) throws SQLException {
		// generate otp for registration
		logger.info("Generating OTP for registration request");
		final String otp = newOTP(emailId, ProcessStatus.NEW_USER_OTP_SENT);
		logger.info("Generated OTP for registration request otp:" + otp);
		return otp;
	}

	public String forgotPasswordOTP(final String userId) throws SQLException {
		// generate otp for password reset
		logger.info("Generating OTP for forgot password request");
		final String otp = newOTP(userId, ProcessStatus.FORGOT_PASSWORD_OTP_SENT);
		logger.info("Generated OTP for forgot password request otp:" + otp);
		return otp;
	}

	private Optional<OtpVO> findOtp(final String userId, final ProcessStatus processStatus) throws SQLException {
		// find otp
		final OtpVO otpVO = new OtpVO(userId, null, processStatus.name(), null);
		return otpDAO.findOtp(userId, processStatus.name());
	}

	public Optional<OtpVO> findNewUserOTP(final String emailId) throws SQLException {
		// find otp for registration
		logger.info("Finding OTP for registration request");
		final Optional<OtpVO> otpVOOpt = findOtp(emailId, ProcessStatus.NEW_USER_OTP_SENT);
		logger.info("Finding OTP for registration request otp:" + otpVOOpt);
		return otpVOOpt;
	}

	public Optional<OtpVO> findForgotPasswordOTP(final String userId) throws SQLException {
		// find otp for password reset
		logger.info("Finding OTP for forgot password request");
		final Optional<OtpVO> otpVOOpt = findOtp(userId, ProcessStatus.FORGOT_PASSWORD_OTP_SENT);
		logger.info("Finding OTP for forgot password request otp:" + otpVOOpt);
		return otpVOOpt;
	}
}
