package com.hdp.service;

import java.util.Collections;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hdp.dao.LoginDAO;
import com.hdp.utils.HttpUtils;
import com.hdp.utils.MailingUtils;
import com.hdp.utils.ProcessStatus;
import com.hdp.vo.CustomerVO;
import com.hdp.vo.LoginVO;
import com.hdp.vo.ProcessVO;

public class LoginService {

	private static final Logger logger = LogManager.getLogger(LoginService.class);

	private final LoginDAO loginDAO;

	private final OtpService otpService;

	public LoginService() {
		//
		this.loginDAO = new LoginDAO();
		this.otpService = new OtpService();
	}

	public ProcessVO auth(final String userId, final String password) {
		//
		try {
			//
			final LoginVO loginVO = new LoginVO(userId, password);
			Optional<CustomerVO> customerVOOpt = this.loginDAO.auth(loginVO);
			if (customerVOOpt.isPresent()) {
				// user does exist
				logger.info("User does exist userId:" + userId + " password:" + password);
				return new ProcessVO(ProcessStatus.AUTH_SUCCESS,
						Collections.singletonMap(HttpUtils.fnameParam, customerVOOpt.get().getFname()));
			}

			logger.info("User does not exist userId:" + userId + " password:" + password);
			// user does not exist
			return new ProcessVO(ProcessStatus.AUTH_FAILED, Collections.emptyMap());

		} catch (Exception ex) {
			// exception handling
			logger.error("Exception occured while processing request:" + ex.getMessage());
			return new ProcessVO(ProcessStatus.EXCEPTION,
					Collections.singletonMap(HttpUtils.exceptionMsg, ex.getMessage()));
		}
	}

	public ProcessVO passwordChange(final String userid, final String password0, final String password1) {
		//
		try {
			//
			final LoginVO loginVO = new LoginVO(userid, password0);
			Optional<CustomerVO> customerVOOpt = this.loginDAO.auth(loginVO);
			if (!customerVOOpt.isPresent()) {
				// user does not exist
				logger.info("User does not exist userid:" + userid + " password:" + password0);
				return new ProcessVO(ProcessStatus.OLD_PASSWORD_INCORRECT, Collections.emptyMap());
			}

			logger.info("User does exist userid:" + userid + " password:" + password0);
			// user does exist
			loginVO.setPassword(password1);
			this.loginDAO.passwordChange(loginVO);

			logger.info("User password changed:" + userid + " password0:" + password0 + " password1:" + password1);

			return new ProcessVO(ProcessStatus.PASSWORD_UPDATE_SUCCESS, Collections.emptyMap());

		} catch (Exception ex) {
			// exception handling
			logger.error("Exception occured while processing request:" + ex.getMessage());
			return new ProcessVO(ProcessStatus.EXCEPTION,
					Collections.singletonMap(HttpUtils.exceptionMsg, ex.getMessage()));
		}
	}

	public ProcessVO forgotPassword1(final String userId, final String option) {
		//
		try {
			//
			Optional<CustomerVO> customerVOOpt = this.loginDAO.userExists(userId);
			if (!customerVOOpt.isPresent()) {
				// user does not exist
				logger.info("User does not exist userId:" + userId);
				return new ProcessVO(ProcessStatus.USER_DOES_NOT_EXIST, Collections.emptyMap());
			}

			// user does exist
			logger.info("User does exist userId:" + userId);

			if ("otp".equals(option)) {
				// auth using otp
				logger.info("Forgot password request authentication using otp");

				// generate otp
				logger.info("Generating OTP for forgot password request");
				final String otp = otpService.forgotPasswordOTP(userId);
				logger.info("Generated OTP for forgot password request otp:" + otp);

				// send otp for password reset
				final String emailId = userId;
				logger.info("Sending email for forgot password request");
				MailingUtils.forgotPasswordEmail(new String[] { customerVOOpt.get().getFname(), otp }, emailId);
				logger.info("Email sent for forgot password request");

				return new ProcessVO(ProcessStatus.FORGOT_PASSWORD_OTP_SENT,
						Collections.singletonMap(HttpUtils.otp0Param, otp));
			}

			// auth using secret question
			logger.info("Forgot password request authentication using secret question and answer");
			return new ProcessVO(ProcessStatus.ASK_SECRET_QUESTION,
					Collections.singletonMap(HttpUtils.secretQuestionParam, customerVOOpt.get().getSecretQuestion()));

		} catch (Exception ex) {
			// exception handling
			logger.error("Exception occured while processing request:" + ex.getMessage());
			return new ProcessVO(ProcessStatus.EXCEPTION,
					Collections.singletonMap(HttpUtils.exceptionMsg, ex.getMessage()));
		}
	}

	public ProcessVO forgotPassword2(final String userId, final String option, String param0, String param1) {
		//
		try {
			//
			Optional<CustomerVO> customerVOOpt = this.loginDAO.userExists(userId);
			if (!customerVOOpt.isPresent()) {
				// user does not exist
				logger.info("User does not exist userId:" + userId);
				return new ProcessVO(ProcessStatus.USER_DOES_NOT_EXIST, Collections.emptyMap());
			}

			// user does exist
			if ("otp".equals(option)) {
				// auth using otp
				logger.info("Forgot password request authentication using otp");

				if (!param0.equalsIgnoreCase(param1)) {
					logger.info("Forgot password request authentication otp mismatched");
					return new ProcessVO(ProcessStatus.FORGOT_PASSWORD_OTP_MISMATCHED, Collections.emptyMap());
				}

				logger.info("Forgot password request authentication otp matched");
				return new ProcessVO(ProcessStatus.FORGOT_PASSWORD_OTP_MATCHED, Collections.emptyMap());
			}

			// auth using secret question
			logger.info("Forgot password request authentication using secret question and answer");

			if (!param0.equalsIgnoreCase(param1)) {
				logger.info("Forgot password request authentication answer mismatched");
				return new ProcessVO(ProcessStatus.ANSWER_MISMATCHED, Collections.emptyMap());
			}

			logger.info("Forgot password request authentication answer matched");
			return new ProcessVO(ProcessStatus.ANSWER_MATCHED, Collections.emptyMap());

		} catch (Exception ex) {
			// exception handling
			logger.error("Exception occured while processing request:" + ex.getMessage());
			return new ProcessVO(ProcessStatus.EXCEPTION,
					Collections.singletonMap(HttpUtils.exceptionMsg, ex.getMessage()));
		}
	}

	public ProcessVO forgotPassword3(final String userId, final String password0, String password1) {
		//
		try {
			//
			if (!password0.equals(password1)) {
				// password mismatch
				logger.info("Forgot password request authentication password mismatched");
				return new ProcessVO(ProcessStatus.PASSWORD_MISMATCHED, Collections.emptyMap());
			}

			// password matched
			logger.info("Forgot password request password updating");
			final LoginVO loginVO = new LoginVO(userId, password0);
			this.loginDAO.passwordChange(loginVO);
			logger.info("Forgot password request password updated");

			// password updated
			return new ProcessVO(ProcessStatus.PASSWORD_UPDATE_SUCCESS, Collections.emptyMap());

		} catch (Exception ex) {
			// exception handling
			logger.error("Exception occured while processing request:" + ex.getMessage());
			return new ProcessVO(ProcessStatus.EXCEPTION,
					Collections.singletonMap(HttpUtils.exceptionMsg, ex.getMessage()));
		}
	}

}
