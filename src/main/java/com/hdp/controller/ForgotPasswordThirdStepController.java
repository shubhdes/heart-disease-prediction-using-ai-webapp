package com.hdp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hdp.service.LoginService;
import com.hdp.utils.HttpUtils;
import com.hdp.utils.ProcessStatus;
import com.hdp.vo.ProcessVO;

public class ForgotPasswordThirdStepController extends CommonController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7669175384995635733L;

	private static final Logger logger = LogManager.getLogger(ForgotPasswordThirdStepController.class);

	private final LoginService loginService;

	public ForgotPasswordThirdStepController() {
		//
		this.loginService = new LoginService();
	}

	@Override
	public void doService(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// read request parameters
		final String userId = request.getParameter(HttpUtils.userIdParam).trim();
		final String password0 = request.getParameter(HttpUtils.passwordParam).trim();
		final String password1 = request.getParameter(HttpUtils.confirmPasswordParam).trim();

		logger.info("Request parameters userId:" + userId + " password0:" + password0 + " password1:" + password1);

		// reset password
		final ProcessVO processVO = this.loginService.forgotPassword3(userId, password0, password1);

		logger.info("Result returned from service:" + processVO);

		if (ProcessStatus.PASSWORD_MISMATCHED == processVO.getProcessStatus()) {
			// password mismatched
			request.setAttribute("errormsg",
					"<div class='alert alert-danger' style='height:70px'><a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>Password and confirm password do not match.</div>");
			request.setAttribute("autofocus", "autofocus");

			RequestDispatcher rqstDispatcher = request.getRequestDispatcher("jsp/forgotpassword2.jsp");
			// redirect to forgot password page
			rqstDispatcher.forward(request, response);

		} else if (ProcessStatus.PASSWORD_UPDATE_SUCCESS == processVO.getProcessStatus()) {
			// password rested
			request.setAttribute("disabled", "disabled");
			request.setAttribute("errormsg",
					"<div class='alert alert-success' style='height:70px'><a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>Password changed successfully <a href='jsp/home.jsp'> Click here</a> to go to login page.</div>");

			RequestDispatcher rqstDispatcher = request.getRequestDispatcher("jsp/forgotpassword2.jsp");
			// redirect to forgot password page
			rqstDispatcher.forward(request, response);

		} else if (ProcessStatus.EXCEPTION == processVO.getProcessStatus()) {
			// exception handling
		}
	}
}