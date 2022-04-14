package com.hdp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hdp.service.RegistrationService;
import com.hdp.utils.ApplicationErrorUtils;
import com.hdp.utils.HttpUtils;
import com.hdp.utils.ProcessStatus;
import com.hdp.vo.ProcessVO;

public class UpdateRegistrationController extends CommonController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5548320255032534081L;

	private static final Logger logger = LogManager.getLogger(UpdateRegistrationController.class);

	private RegistrationService registrationService;

	public UpdateRegistrationController() {
		//
		this.registrationService = new RegistrationService();
	}

	@Override
	public void doService(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// read request parameters
		final String fname = request.getParameter(HttpUtils.fnameParam).trim();
		final String lname = request.getParameter(HttpUtils.lnameParam).trim();
		final String mobile = request.getParameter(HttpUtils.mobileParam).trim();
		final String secretQuestion = request.getParameter(HttpUtils.secretQuestionParam).trim();
		final String answer = request.getParameter(HttpUtils.answer0Param).trim();

		final HttpSession session = request.getSession(false);
		final String emailId = session.getAttribute(HttpUtils.userIdParam).toString();

		logger.info("Request parameters emailId:" + emailId + " fname:" + fname + " lname:" + lname + " mobile:"
				+ mobile + " secretQuestion:" + secretQuestion + " answer:" + answer);

		// update registration
		ProcessVO processVO = this.registrationService.upateUser(fname, lname, emailId, mobile, secretQuestion, answer);

		logger.info("Result returned from service:" + processVO);

		if (ProcessStatus.REGISTRATION_UPDATE_SUCCESS == processVO.getProcessStatus()) {
			// registration updated

			request.setAttribute("msg", "$('#modal-msg').modal('show');");

			RequestDispatcher rqstDispatcher = request.getRequestDispatcher("jsp/edit_profile.jsp");
			// redirect to profile page
			rqstDispatcher.forward(request, response);

		} else if (ProcessStatus.EXCEPTION == processVO.getProcessStatus()) {
			// exception handling
			ApplicationErrorUtils.addError(processVO.getProcessAttributes().get(HttpUtils.exceptionMsg).toString());
		}
	}
}