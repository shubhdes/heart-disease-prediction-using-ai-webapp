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

public class CreateRegistrationSecondStepController extends CommonController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7222392907857362166L;

	private static final Logger logger = LogManager.getLogger(CreateRegistrationSecondStepController.class);

	private RegistrationService registrationService;

	public CreateRegistrationSecondStepController() {
		//
		this.registrationService = new RegistrationService();
	}

	@Override
	public void doService(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final String fname = request.getParameter(HttpUtils.fnameParam).trim();
		final String lname = request.getParameter(HttpUtils.lnameParam).trim();
		final String emailId = request.getParameter(HttpUtils.emailIdParam).trim();
		final String mobile = request.getParameter(HttpUtils.mobileParam).trim();
		final String secretQuestion = request.getParameter(HttpUtils.secretQuestionParam).trim();
		final String answer = request.getParameter(HttpUtils.answer0Param).trim();
		final String password = request.getParameter(HttpUtils.passwordParam).trim();
		final String otp0 = request.getParameter(HttpUtils.otp0Param).trim();
		final String otp1 = request.getParameter(HttpUtils.otp1Param).trim();

		logger.info("Request parameters fname:" + fname + " lname:" + lname + " emailId:" + emailId + " mobile:"
				+ mobile + " secretQuestion:" + secretQuestion + " answer:" + answer + " password:" + password
				+ " otp0:" + otp0 + " otp1:" + otp1);

		final ProcessVO processVO = this.registrationService.newUser(fname, lname, emailId, mobile, secretQuestion,
				answer, password, otp0, otp1);

		logger.info("Result returned from service:" + processVO);

		if (ProcessStatus.OTP_MISMATCHED == processVO.getProcessStatus()) {
			// otp mismatch
			request.setAttribute(HttpUtils.fnameParam, fname);
			request.setAttribute(HttpUtils.lnameParam, lname);
			request.setAttribute(HttpUtils.mobileParam, mobile);
			request.setAttribute(HttpUtils.emailIdParam, emailId);
			request.setAttribute(HttpUtils.secretQuestionParam, secretQuestion);
			request.setAttribute(HttpUtils.answer0Param, answer);
			request.setAttribute(HttpUtils.passwordParam, password);
			request.setAttribute(HttpUtils.otp0Param, otp0);
			request.setAttribute("errormsg",
					"<div class='alert alert-danger' style='height:70px'><a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>Invalid OTP. Please enter again</div>");
			request.setAttribute("autofocus", "autofocus");

			// redirect to login page
			RequestDispatcher rd = request.getRequestDispatcher("jsp/newuser1.jsp");
			rd.forward(request, response);

		} else if (ProcessStatus.REGISTRATION_SUCCESS == processVO.getProcessStatus()) {
			// user registered
			request.setAttribute("errormsg",
					"<div class='alert alert-success' style='text-align: center;'>Registration complete.<a href='jsp/newuser.jsp'> Login here</a> <br/></div>");
			request.setAttribute("disabled", "disabled");

			request.setAttribute(HttpUtils.fnameParam, fname);
			request.setAttribute(HttpUtils.lnameParam, lname);
			request.setAttribute(HttpUtils.mobileParam, mobile);
			request.setAttribute(HttpUtils.emailIdParam, emailId);
			request.setAttribute(HttpUtils.secretQuestionParam, secretQuestion);
			request.setAttribute(HttpUtils.answer0Param, answer);
			request.setAttribute(HttpUtils.passwordParam, password);

			RequestDispatcher rqstDispatcher = request.getRequestDispatcher("jsp/newuser1.jsp");
			// redirect to login page
			rqstDispatcher.forward(request, response);

		} else if (ProcessStatus.EXCEPTION == processVO.getProcessStatus()) {
			// exception handling
		}
	}
}