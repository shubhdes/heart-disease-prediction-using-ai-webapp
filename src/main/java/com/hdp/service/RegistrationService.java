package com.hdp.service;

import java.util.Collections;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hdp.dao.LoginDAO;
import com.hdp.dao.RegistrationDAO;
import com.hdp.utils.CommonUtils;
import com.hdp.utils.HttpUtils;
import com.hdp.utils.MailingUtils;
import com.hdp.utils.ProcessStatus;
import com.hdp.vo.CustomerVO;
import com.hdp.vo.ProcessVO;

public class RegistrationService {

	private static final Logger logger = LogManager.getLogger(RegistrationService.class);

	private final RegistrationDAO registrationDAO;

	private final LoginDAO loginDAO;

	public RegistrationService() {
		//
		this.registrationDAO = new RegistrationDAO();
		this.loginDAO = new LoginDAO();
	}

	public ProcessVO userExists(final String username) {
		//
		try {
			// verify if user already exist
			Optional<CustomerVO> customerVOOpt = this.loginDAO.userExists(username);
			if (customerVOOpt.isPresent()) {
				// user already exist
				logger.info("User does exist username:" + username);
				return new ProcessVO(ProcessStatus.USER_ALREADY_EXIST, Collections.emptyMap());
			}
			// generate otp
			logger.info("Generating OTP for registration request");
			final String otp = CommonUtils.otp();
			logger.info("Generated OTP for registration request otp:" + otp);

			// send otp for account activation
			logger.info("Sending email for registration request");
			MailingUtils.registrationEmail(new String[] { username, otp }, username);
			logger.info("Email sent for registration request");

			return new ProcessVO(ProcessStatus.OTP_SENT, Collections.singletonMap(HttpUtils.otp0Param, otp));

		} catch (Exception ex) {
			// exception handling
			logger.error("Exception occured while processing request:" + ex.getMessage());
			return new ProcessVO(ProcessStatus.EXCEPTION,
					Collections.singletonMap(HttpUtils.exceptionMsg, ex.getMessage()));
		}
	}

	public ProcessVO newUser(final String fname, final String lname, final String username, final String mobile,
			final String secretQuestion, final String answer, final String password, final String otp0,
			final String otp1) {
		//
		try {
			// verify otp
			if (!otp0.equalsIgnoreCase(otp1)) {
				// otp does not match
				logger.info("Registration OTP does not match otp0:" + otp0 + " otp1:" + otp1);
				return new ProcessVO(ProcessStatus.OTP_MISMATCHED, Collections.emptyMap());
			}

			// otp does match
			logger.info("User login details saving username:" + username + " password:" + password);
			loginDAO.newUser(username, password);
			logger.info("User login details saved username:" + username + " password:" + password);

			String emailId = username;

			logger.info("Registration details saving fname:" + fname + " lname:" + lname + " emailId:" + emailId
					+ " mobile:" + mobile + " secretQuestion:" + secretQuestion + " answer:" + answer);
			registrationDAO.newUser(fname, lname, emailId, mobile, secretQuestion, answer);
			logger.info("Registration details saved fname:" + fname + " lname:" + lname + " emailId:" + emailId
					+ " mobile:" + mobile + " secretQuestion:" + secretQuestion + " answer:" + answer);

			return new ProcessVO(ProcessStatus.REGISTRATION_SUCCESS, Collections.emptyMap());

		} catch (Exception ex) {
			// exception handling
			logger.error("Exception occured while processing request:" + ex.getMessage());
			return new ProcessVO(ProcessStatus.EXCEPTION,
					Collections.singletonMap(HttpUtils.exceptionMsg, ex.getMessage()));
		}
	}

	public ProcessVO upateUser(final String fname, final String lname, final String emailId, final String mobile,
			final String secretQuestion, final String answer) {
		//
		try {
			// update registration
			logger.info("Registration details updating fname:" + fname + " lname:" + lname + " emailId:" + emailId
					+ " mobile:" + mobile + " secretQuestion:" + secretQuestion + " answer:" + answer);
			registrationDAO.upateUser(fname, lname, emailId, mobile, secretQuestion, answer);
			logger.info("Registration details updated fname:" + fname + " lname:" + lname + " emailId:" + emailId
					+ " mobile:" + mobile + " secretQuestion:" + secretQuestion + " answer:" + answer);

			return new ProcessVO(ProcessStatus.REGISTRATION_UPDATE_SUCCESS, Collections.emptyMap());

		} catch (Exception ex) {
			// exception handling
			logger.error("Exception occured while processing request:" + ex.getMessage());
			return new ProcessVO(ProcessStatus.EXCEPTION,
					Collections.singletonMap(HttpUtils.exceptionMsg, ex.getMessage()));
		}
	}

	public ProcessVO passwordChange(final String username, final String password) {
		//
		try {
			//
			logger.info("Login updating username:" + username + " password:" + password);
			this.loginDAO.passwordChange(username, password);
			logger.info("Login updated username:" + username + " password:" + password);

			return new ProcessVO(ProcessStatus.REGISTRATION_UPDATE_SUCCESS, Collections.emptyMap());

		} catch (Exception ex) {
			// exception handling
			logger.error("Exception occured while processing request:" + ex.getMessage());
			return new ProcessVO(ProcessStatus.EXCEPTION,
					Collections.singletonMap(HttpUtils.exceptionMsg, ex.getMessage()));
		}
	}

	public ProcessVO existingUser(final String username) {
		//
		try {
			//
			Optional<CustomerVO> customerVOOpt = this.registrationDAO.existingUser(username);
			if (customerVOOpt.isPresent()) {
				// user does exist
				logger.info("User does exist username:" + username);
				final StringBuilder response = new StringBuilder();
				response.append(customerVOOpt.get().getFname());
				response.append("-");
				response.append(customerVOOpt.get().getLname());
				response.append("-");
				response.append(customerVOOpt.get().getMobile());
				response.append("-");
				response.append(customerVOOpt.get().getSecretQuestion());
				response.append("-");
				response.append(customerVOOpt.get().getAnswer());

				logger.info("User details retrieved for username:" + username + " response:" + response);

				return new ProcessVO(ProcessStatus.USER_ALREADY_EXIST,
						Collections.singletonMap(HttpUtils.miscParam, response.toString()));
			}
			// user does not exist
			logger.info("User does not exist username:" + username);
			return new ProcessVO(ProcessStatus.USER_DOES_NOT_EXIST, Collections.emptyMap());

		} catch (Exception ex) {
			// exception handling
			logger.error("Exception occured while processing request:" + ex.getMessage());
			return new ProcessVO(ProcessStatus.EXCEPTION,
					Collections.singletonMap(HttpUtils.exceptionMsg, ex.getMessage()));
		}
	}
}
