package com.hdp.service;

import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hdp.dao.OtpDAO;
import com.hdp.utils.CommonUtils;
import com.hdp.utils.OtpUtils;
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

		final OtpVO otpVO = new OtpVO(0l, userId, otp, processStatus.name(), System.currentTimeMillis());
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

	private Optional<OtpVO> findOtp(final String otp, final String userId, final ProcessStatus processStatus)
			throws SQLException {
		// find otp
		final OtpVO otpVO = new OtpVO(0l, userId, otp, processStatus.name(), 0l);
		return otpDAO.findOtp(otpVO);
	}

	public ProcessStatus findNewUserOTP(final String emailId, final String otp) throws SQLException {
		// find otp for registration
		logger.info("Finding OTP for registration request");
		final Optional<OtpVO> otpVOOpt = findOtp(otp, emailId, ProcessStatus.NEW_USER_OTP_SENT);
		logger.info("Finding OTP for registration request otp:" + otpVOOpt);

		if (!otpVOOpt.isPresent()) {
			// no such otp
			return ProcessStatus.NEW_USER_OTP_MISMATCHED;
		}
		final OtpVO otpVO = otpVOOpt.get();
		if (!otpVO.getRandom().equalsIgnoreCase(otp)) {
			// otp mismatch
			return ProcessStatus.NEW_USER_OTP_MISMATCHED;
		}
		if (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - Long.valueOf(otpVOOpt.get().getTs())) > 300) {
			// otp mismatch
			return ProcessStatus.NEW_USER_OTP_EXPIRED;
		}
		// change otp status
		otpVO.setStatus(ProcessStatus.NEW_USER_OTP_MATCHED.name());
		otpDAO.updateOtp(otpVO);

		return ProcessStatus.NEW_USER_OTP_MATCHED;
	}

	public ProcessStatus findForgotPasswordOTP(final String userId, final String otp) throws SQLException {
		// find otp for password reset
		logger.info("Finding OTP for forgot password request");
		final Optional<OtpVO> otpVOOpt = findOtp(otp, userId, ProcessStatus.FORGOT_PASSWORD_OTP_SENT);
		logger.info("Finding OTP for forgot password request otp:" + otpVOOpt);

		if (!otpVOOpt.isPresent()) {
			// no such otp
			return ProcessStatus.FORGOT_PASSWORD_OTP_MISMATCHED;
		}
		final OtpVO otpVO = otpVOOpt.get();
		if (!otpVO.getRandom().equalsIgnoreCase(otp)) {
			// otp mismatch
			return ProcessStatus.FORGOT_PASSWORD_OTP_MISMATCHED;
		}
		if (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - Long.valueOf(otpVOOpt.get().getTs())) > Long
				.valueOf(OtpUtils.config(OtpUtils.timeoutInterval))) {
			// otp mismatch
			return ProcessStatus.FORGOT_PASSWORD_OTP_EXPIRED;
		}
		// change otp status
		otpVO.setStatus(ProcessStatus.FORGOT_PASSWORD_OTP_MATCHED.name());
		otpDAO.updateOtp(otpVO);

		return ProcessStatus.FORGOT_PASSWORD_OTP_MATCHED;
	}
}
