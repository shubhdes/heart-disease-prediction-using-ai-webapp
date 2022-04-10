package com.hdp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hdp.service.RegistrationService;
import com.hdp.utils.HttpUtils;
import com.hdp.utils.ProcessStatus;
import com.hdp.vo.ProcessVO;

public class CreateRegistrationFirstStepController extends CommonController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8777368988238487848L;

	private static final Logger logger = LogManager.getLogger(CreateRegistrationFirstStepController.class);

	private RegistrationService registrationService;

	public CreateRegistrationFirstStepController() {
		//
		this.registrationService = new RegistrationService();
	}

	@Override
	public void doService(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// read request parameters
		final String fname = request.getParameter(HttpUtils.fnameParam).trim();
		final String lname = request.getParameter(HttpUtils.lnameParam).trim();
		final String emailId = request.getParameter(HttpUtils.emailIdParam).trim();
		final String mobile = request.getParameter(HttpUtils.mobileParam).trim();
		final String secretQuestion = request.getParameter(HttpUtils.secretQuestionParam).trim();
		final String answer = request.getParameter(HttpUtils.answer0Param).trim();
		final String password = request.getParameter(HttpUtils.passwordParam).trim();

		logger.info("Request parameters fname:" + fname + " lname:" + lname + " emailId:" + emailId + " mobile:"
				+ mobile + " secretQuestion:" + secretQuestion + " answer:" + answer + " password:" + password);

		// verify if user already exist
		final ProcessVO processVO = this.registrationService.userExists(fname, emailId);

		logger.info("Result returned from service:" + processVO);

		if (ProcessStatus.USER_ALREADY_EXIST == processVO.getProcessStatus()) {

			// user does exist
			request.setAttribute("errormsg",
					"<div class='alert alert-danger' style='height:70px;text-align:center'><a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>The specified email id is already registered with us.Try using a different one</div>");
			request.setAttribute("autofocus", "autofocus");

			final RequestDispatcher rqstDispatcher = request.getRequestDispatcher("jsp/newuser.jsp");

			// redirect to registration page
			rqstDispatcher.forward(request, response);

		} else if (ProcessStatus.NEW_USER_OTP_SENT == processVO.getProcessStatus()) {
			// user does not exist

			request.setAttribute("errormsg",
					"<div class='alert alert-success' style='text-align: center;'>An OTP value is sent to the specified email address. Please enter the same to complete the registration process</div>");

			request.setAttribute(HttpUtils.fnameParam, fname);
			request.setAttribute(HttpUtils.lnameParam, lname);
			request.setAttribute(HttpUtils.emailIdParam, emailId);
			request.setAttribute(HttpUtils.mobileParam, mobile);
			request.setAttribute(HttpUtils.secretQuestionParam, secretQuestion);
			request.setAttribute(HttpUtils.answer0Param, answer);
			request.setAttribute(HttpUtils.otp0Param, processVO.getProcessAttributes().get(HttpUtils.otp0Param));
			request.setAttribute(HttpUtils.passwordParam, password);

			final RequestDispatcher rqstDispatcher = request.getRequestDispatcher("jsp/newuser1.jsp");
			// redirect to account activation page
			rqstDispatcher.forward(request, response);

		} else if (ProcessStatus.EXCEPTION == processVO.getProcessStatus()) {
			// exception handling
		}
	}
}