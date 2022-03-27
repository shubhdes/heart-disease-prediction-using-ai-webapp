package com.hdp.service;

import java.util.Collections;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hdp.dao.LoginDAO;
import com.hdp.utils.CommonUtils;
import com.hdp.utils.HttpUtils;
import com.hdp.utils.MailingUtils;
import com.hdp.utils.ProcessStatus;
import com.hdp.vo.CustomerVO;
import com.hdp.vo.ProcessVO;

public class LoginService {

	private static final Logger logger = LogManager.getLogger(LoginService.class);

	private final LoginDAO loginDAO;

	public LoginService() {
		//
		this.loginDAO = new LoginDAO();
	}

	public ProcessVO auth(final String username, final String password) {
		//
		try {
			//
			Optional<CustomerVO> customerVOOpt = this.loginDAO.auth(username, password);
			if (customerVOOpt.isPresent()) {
				// user does exist
				logger.info("User does exist username:" + username + " password:" + password);
				return new ProcessVO(ProcessStatus.AUTH_SUCCESS,
						Collections.singletonMap(HttpUtils.fnameParam, customerVOOpt.get().getFname()));
			}

			logger.info("User does not exist username:" + username + " password:" + password);
			// user does not exist
			return new ProcessVO(ProcessStatus.AUTH_FAILED, Collections.emptyMap());

		} catch (Exception ex) {
			// exception handling
			logger.error("Exception occured while processing request:" + ex.getMessage());
			return new ProcessVO(ProcessStatus.EXCEPTION,
					Collections.singletonMap(HttpUtils.exceptionMsg, ex.getMessage()));
		}
	}

	public ProcessVO passwordChange(final String username, final String password0, final String password1) {
		//
		try {
			//
			Optional<CustomerVO> customerVOOpt = this.loginDAO.auth(username, password0);
			if (!customerVOOpt.isPresent()) {
				// user does not exist
				logger.info("User does not exist username:" + username + " password:" + password0);
				return new ProcessVO(ProcessStatus.OLD_PASSWORD_INCORRECT, Collections.emptyMap());
			}

			logger.info("User does exist username:" + username + " password:" + password0);
			// user does exist
			this.loginDAO.passwordChange(username, password1);

			logger.info("User password changed:" + username + " password0:" + password0 + " password1:" + password1);

			return new ProcessVO(ProcessStatus.PASSWORD_UPDATE_SUCCESS, Collections.emptyMap());

		} catch (Exception ex) {
			// exception handling
			logger.error("Exception occured while processing request:" + ex.getMessage());
			return new ProcessVO(ProcessStatus.EXCEPTION,
					Collections.singletonMap(HttpUtils.exceptionMsg, ex.getMessage()));
		}
	}

	public ProcessVO forgotPassword1(final String username, final String option) {
		//
		try {
			//
			Optional<CustomerVO> customerVOOpt = this.loginDAO.userExists(username);
			if (!customerVOOpt.isPresent()) {
				// user does not exist
				logger.info("User does not exist username:" + username);
				return new ProcessVO(ProcessStatus.USER_DOES_NOT_EXIST, Collections.emptyMap());
			}

			// user does exist
			logger.info("User does exist username:" + username);

			if ("otp".equals(option)) {
				// auth using otp
				logger.info("Forgot password request authentication using otp");

				// generate otp
				logger.info("Generating OTP for forgot password request");
				final String otp = CommonUtils.otp();
				logger.info("Generated OTP for forgot password request otp:" + otp);

				// send otp for password reset
				logger.info("Sending email for forgot password request");
				MailingUtils.forgotPasswordEmail(new String[] { username, otp }, username);
				logger.info("Email sent for forgot password request");

				return new ProcessVO(ProcessStatus.OTP_SENT, Collections.singletonMap(HttpUtils.otp0Param, otp));
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

	public ProcessVO forgotPassword2(final String username, final String option, String param0, String param1) {
		//
		try {
			//
			Optional<CustomerVO> customerVOOpt = this.loginDAO.userExists(username);
			if (!customerVOOpt.isPresent()) {
				// user does not exist
				logger.info("User does not exist username:" + username);
				return new ProcessVO(ProcessStatus.USER_DOES_NOT_EXIST, Collections.emptyMap());
			}

			// user does exist
			if ("otp".equals(option)) {
				// auth using otp
				logger.info("Forgot password request authentication using otp");

				if (!param0.equalsIgnoreCase(param1)) {
					logger.info("Forgot password request authentication otp mismatched");
					return new ProcessVO(ProcessStatus.OTP_MISMATCHED, Collections.emptyMap());
				}

				logger.info("Forgot password request authentication otp matched");
				return new ProcessVO(ProcessStatus.OTP_MATCHED, Collections.emptyMap());
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

	public ProcessVO forgotPassword3(final String username, final String password0, String password1) {
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
			this.loginDAO.passwordChange(username, password0);
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
