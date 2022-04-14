package com.hdp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hdp.service.LoginService;
import com.hdp.utils.ApplicationErrorUtils;
import com.hdp.utils.HttpUtils;
import com.hdp.utils.ProcessStatus;
import com.hdp.vo.ProcessVO;

public class ForgotPasswordFirstStepController extends CommonController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -698215734928972960L;

	private static final Logger logger = LogManager.getLogger(ForgotPasswordFirstStepController.class);

	private final LoginService loginService;

	public ForgotPasswordFirstStepController() {
		//
		this.loginService = new LoginService();
	}

	@Override
	public void doService(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// read request parameters
		final String userId = request.getParameter(HttpUtils.userIdParam).trim();
		final String option = request.getParameter(HttpUtils.optionParam).trim();

		logger.info("Request parameters userId:" + userId + " option:" + option);

		final ProcessVO processVO = loginService.forgotPassword1(userId, option);

		logger.info("Result returned from service:" + processVO);

		if (ProcessStatus.USER_DOES_NOT_EXIST == processVO.getProcessStatus()) {
			// user does not exist
			request.setAttribute("errormsg",
					"<div class='alert alert-danger' style='height:70px'><a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>The specified email id/userid is not registered with us.</div>");
			request.setAttribute("autofocus", "autofocus");

			RequestDispatcher rqsDispatcher = request.getRequestDispatcher("jsp/forgot_password.jsp");
			// redirect to forgot password page
			rqsDispatcher.forward(request, response);

		} else if (ProcessStatus.FORGOT_PASSWORD_OTP_SENT == processVO.getProcessStatus()) {
			// auth using otp
			request.setAttribute(HttpUtils.otp0Param,
					processVO.getProcessAttributes().get(HttpUtils.otp0Param).toString());
			request.setAttribute(HttpUtils.userIdParam, userId);
			request.setAttribute(HttpUtils.optionParam, "otp");
			request.setAttribute("div2", "hidden");
			request.setAttribute("m", "Enter the OTP sent to your registered emailid");

			RequestDispatcher rqsDispatcher = request.getRequestDispatcher("jsp/forgot_password_option.jsp");
			// redirect to forgot password page
			rqsDispatcher.forward(request, response);

		} else if (ProcessStatus.ASK_SECRET_QUESTION == processVO.getProcessStatus()) {
			// auth using secret question
			request.setAttribute(HttpUtils.secretQuestionParam,
					processVO.getProcessAttributes().get(HttpUtils.secretQuestionParam).toString());
			request.setAttribute(HttpUtils.userIdParam, userId);
			request.setAttribute(HttpUtils.optionParam, "secret");
			request.setAttribute("div1", "hidden");

			RequestDispatcher rqsDispatcher = request.getRequestDispatcher("jsp/forgot_password_option.jsp");
			// redirect to forgot password page
			rqsDispatcher.forward(request, response);

		} else if (ProcessStatus.EXCEPTION == processVO.getProcessStatus()) {
			// exception handling
			ApplicationErrorUtils.addError(processVO.getProcessAttributes().get(HttpUtils.exceptionMsg).toString());
		}
	}
}