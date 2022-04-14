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
import com.hdp.utils.ApplicationErrorUtils;
import com.hdp.utils.HttpUtils;
import com.hdp.utils.ProcessStatus;
import com.hdp.vo.ProcessVO;

public class UpdatePasswordController extends CommonController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4712533237310083119L;

	private static final Logger logger = LogManager.getLogger(UpdatePasswordController.class);

	private final LoginService loginService;

	public UpdatePasswordController() {
		//
		this.loginService = new LoginService();
	}

	@Override
	public void doService(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// read request parameters
		final String oldpassword = request.getParameter(HttpUtils.oldPasswordParam).trim();
		final String newpassword = request.getParameter(HttpUtils.newPasswordParam).trim();

		final HttpSession session = request.getSession(false);
		final String userId = session.getAttribute(HttpUtils.userIdParam).toString();

		logger.info(
				"Request parameters userId:" + userId + " oldpassword:" + oldpassword + " newpassword:" + newpassword);

		// change password
		ProcessVO processVO = this.loginService.passwordChange(userId, oldpassword, newpassword);

		logger.info("Result returned from service:" + processVO);

		if (ProcessStatus.OLD_PASSWORD_INCORRECT == processVO.getProcessStatus()) {
			//
			request.setAttribute("m1", "Old password does not match");
			request.setAttribute("m2", "<div style='color:red'>Error</div>");
			request.setAttribute("msg", "$('#modal-msg').modal('show');");

			final RequestDispatcher rqstDispatcher = request.getRequestDispatcher("jsp/edit_password.jsp");
			// redirect to change password page
			rqstDispatcher.forward(request, response);

		} else if (ProcessStatus.PASSWORD_UPDATE_SUCCESS == processVO.getProcessStatus()) {
			//
			request.setAttribute("m1", "Password changed successfully");
			request.setAttribute("m2", "<div style='color:green'>Success</div>");
			request.setAttribute("msg", "$('#modal-msg').modal('show');");

			final RequestDispatcher rqstDispatcher = request.getRequestDispatcher("jsp/edit_password.jsp");
			// redirect to change password page
			rqstDispatcher.forward(request, response);

		} else if (ProcessStatus.EXCEPTION == processVO.getProcessStatus()) {
			// exception handling
			ApplicationErrorUtils.addError(processVO.getProcessAttributes().get(HttpUtils.exceptionMsg).toString());
		}
	}
}