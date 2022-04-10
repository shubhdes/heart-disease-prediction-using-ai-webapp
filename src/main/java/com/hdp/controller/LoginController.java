package com.hdp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hdp.service.LoginService;
import com.hdp.utils.HttpUtils;
import com.hdp.utils.ProcessStatus;
import com.hdp.vo.ProcessVO;

public class LoginController extends CommonController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5900529408765983862L;

	private static final Logger logger = LogManager.getLogger(LoginController.class);

	private final LoginService loginService;

	public LoginController() {
		//
		this.loginService = new LoginService();
	}

	@Override
	public void doService(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// read request parameters
		String userId = request.getParameter(HttpUtils.userIdParam);
		String password = request.getParameter(HttpUtils.passwordParam);

		logger.info("Request parameters userId:" + userId + " password:" + password);

		// authenticate user
		ProcessVO processVO = this.loginService.auth(userId, password);

		logger.info("Result returned from service:" + processVO);

		if (ProcessStatus.AUTH_SUCCESS == processVO.getProcessStatus()) {
			// user does exit
			final HttpSession session = request.getSession(true);
			session.setAttribute(HttpUtils.userIdParam, userId);
			session.setAttribute(HttpUtils.fnameParam, processVO.getProcessAttributes().get(HttpUtils.fnameParam));

			// redirect to home page
			response.sendRedirect("jsp/home.jsp");

		} else if (ProcessStatus.AUTH_FAILED == processVO.getProcessStatus()) {
			// user does not exist
			request.setAttribute("msg",
					"<div class='alert alert-danger' style='height:50px;background-color:white;font-weight:bolder;border:none'>Invalid Credentials.</div>");

			final RequestDispatcher rqstDispatcher = request.getRequestDispatcher("jsp/newuser.jsp");
			// redirect to login page
			rqstDispatcher.forward(request, response);

		} else if (ProcessStatus.EXCEPTION == processVO.getProcessStatus()) {
			// exception handling
		}
	}
}