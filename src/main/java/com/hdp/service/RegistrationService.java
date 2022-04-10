package com.hdp.service;

import java.util.Collections;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hdp.dao.LoginDAO;
import com.hdp.dao.RegistrationDAO;
import com.hdp.utils.HttpUtils;
import com.hdp.utils.MailingUtils;
import com.hdp.utils.ProcessStatus;
import com.hdp.vo.CustomerVO;
import com.hdp.vo.LoginVO;
import com.hdp.vo.ProcessVO;

public class RegistrationService {

	private static final Logger logger = LogManager.getLogger(RegistrationService.class);

	private final RegistrationDAO registrationDAO;

	private final LoginDAO loginDAO;

	private final OtpService otpService;

	public RegistrationService() {
		//
		this.registrationDAO = new RegistrationDAO();
		this.loginDAO = new LoginDAO();
		this.otpService = new OtpService();
	}

	public ProcessVO userExists(final String fname, final String userId) {
		//
		try {
			// verify if user already exist
			Optional<CustomerVO> customerVOOpt = this.loginDAO.userExists(userId);
			if (customerVOOpt.isPresent()) {
				// user already exist
				logger.info("User does exist userId:" + userId);
				return new ProcessVO(ProcessStatus.USER_ALREADY_EXIST, Collections.emptyMap());
			}
			// generate otp
			logger.info("Generating OTP for registration request");
			final String otp = otpService.newUserOTP(userId);
			logger.info("Generated OTP for registration request otp:" + otp);

			// send otp for account activation
			logger.info("Sending email for registration request");
			MailingUtils.registrationEmail(new String[] { fname, otp }, userId);
			logger.info("Email sent for registration request");

			return new ProcessVO(ProcessStatus.NEW_USER_OTP_SENT, Collections.singletonMap(HttpUtils.otp0Param, otp));

		} catch (Exception ex) {
			// exception handling
			logger.error("Exception occured while processing request:" + ex.getMessage());
			return new ProcessVO(ProcessStatus.EXCEPTION,
					Collections.singletonMap(HttpUtils.exceptionMsg, ex.getMessage()));
		}
	}

	public ProcessVO newUser(final String fname, final String lname, final String userId, final String mobile,
			final String secretQuestion, final String answer, final String password, final String otp0,
			final String otp1) {
		//
		try {
			// verify otp
			final String emailId = userId;
			final ProcessStatus otpProcessStatus = otpService.findNewUserOTP(emailId, otp1);

			if (ProcessStatus.NEW_USER_OTP_MATCHED != otpProcessStatus) {
				// otp does not match
				logger.info("Registration OTP does not match otp0:" + otp0 + " otp1:" + otp1);
				return new ProcessVO(otpProcessStatus, Collections.emptyMap());
			}

			// otp does match
			logger.info("User login details saving userId:" + userId + " password:" + password);
			final LoginVO loginVO = new LoginVO(userId, password);
			loginDAO.newUser(loginVO);
			logger.info("User login details saved userId:" + userId + " password:" + password);

			logger.info("Registration details saving fname:" + fname + " lname:" + lname + " emailId:" + emailId
					+ " mobile:" + mobile + " secretQuestion:" + secretQuestion + " answer:" + answer);
			final CustomerVO customerVO = new CustomerVO(fname, lname, emailId, mobile, secretQuestion, answer);
			registrationDAO.newUser(customerVO);
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
			final CustomerVO customerVO = new CustomerVO(fname, lname, emailId, mobile, secretQuestion, answer);
			registrationDAO.upateUser(customerVO);
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

	public ProcessVO passwordChange(final String userId, final String password) {
		//
		try {
			//
			logger.info("Login updating userId:" + userId + " password:" + password);
			final LoginVO loginVO = new LoginVO(userId, password);
			this.loginDAO.passwordChange(loginVO);
			logger.info("Login updated userId:" + userId + " password:" + password);

			return new ProcessVO(ProcessStatus.REGISTRATION_UPDATE_SUCCESS, Collections.emptyMap());

		} catch (Exception ex) {
			// exception handling
			logger.error("Exception occured while processing request:" + ex.getMessage());
			return new ProcessVO(ProcessStatus.EXCEPTION,
					Collections.singletonMap(HttpUtils.exceptionMsg, ex.getMessage()));
		}
	}

	public ProcessVO existingUser(final String emailId) {
		//
		try {
			//
			Optional<CustomerVO> customerVOOpt = this.registrationDAO.existingUser(emailId);
			if (customerVOOpt.isPresent()) {
				// user does exist
				logger.info("User does exist emailId:" + emailId);
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

				logger.info("User details retrieved for emailId:" + emailId + " response:" + response);

				return new ProcessVO(ProcessStatus.USER_ALREADY_EXIST,
						Collections.singletonMap(HttpUtils.miscParam, response.toString()));
			}
			// user does not exist
			logger.info("User does not exist emailId:" + emailId);
			return new ProcessVO(ProcessStatus.USER_DOES_NOT_EXIST, Collections.emptyMap());

		} catch (Exception ex) {
			// exception handling
			logger.error("Exception occured while processing request:" + ex.getMessage());
			return new ProcessVO(ProcessStatus.EXCEPTION,
					Collections.singletonMap(HttpUtils.exceptionMsg, ex.getMessage()));
		}
	}
}
