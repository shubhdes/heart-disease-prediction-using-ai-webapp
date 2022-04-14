package com.hdp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hdp.service.RegistrationService;
import com.hdp.utils.ApplicationErrorUtils;
import com.hdp.utils.HttpUtils;
import com.hdp.utils.ProcessStatus;
import com.hdp.vo.ProcessVO;

public class FetchRegistration extends CommonController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7242767572720954019L;

	private static final Logger logger = LogManager.getLogger(FetchRegistration.class);

	private final RegistrationService registrationService;

	public FetchRegistration() {
		//
		this.registrationService = new RegistrationService();
	}

	@Override
	public void doService(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// read request parameters
		final String emailId = request.getParameter(HttpUtils.emailIdParam);

		logger.info("Request parameters emailId:" + emailId);

		// fetch registration
		final ProcessVO processVO = this.registrationService.existingUser(emailId);

		logger.info("Result returned from service:" + processVO);

		if (ProcessStatus.USER_ALREADY_EXIST == processVO.getProcessStatus()) {
			// user does exist
			final PrintWriter writer = response.getWriter();
			writer.write(processVO.getProcessAttributes().get(HttpUtils.miscParam).toString());

		} else if (ProcessStatus.USER_DOES_NOT_EXIST == processVO.getProcessStatus()) {
			//
		} else if (ProcessStatus.EXCEPTION == processVO.getProcessStatus()) {
			// exception handling
			ApplicationErrorUtils.addError(processVO.getProcessAttributes().get(HttpUtils.exceptionMsg).toString());
		}
	}
}