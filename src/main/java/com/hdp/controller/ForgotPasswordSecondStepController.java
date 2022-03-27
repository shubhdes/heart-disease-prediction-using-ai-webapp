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

public class ForgotPasswordSecondStepController extends CommonController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3304153689316345086L;

	private static final Logger logger = LogManager.getLogger(ForgotPasswordSecondStepController.class);

	private final LoginService loginService;

	public ForgotPasswordSecondStepController() {
		//
		this.loginService = new LoginService();
	}

	@Override
	public void doService(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// read request parameters
		final String username = request.getParameter(HttpUtils.userIdParam);
		final String option = request.getParameter(HttpUtils.optionParam);

		logger.info("Request parameters username:" + username + " option:" + option);

		if ("otp".equalsIgnoreCase(option)) {
			final String param0 = request.getParameter(HttpUtils.otp0Param).trim();
			final String param1 = request.getParameter(HttpUtils.otp1Param).trim();

			// auth using otp
			final ProcessVO processVO = this.loginService.forgotPassword2(username, option, param0, param1);

			logger.info("Result returned from service:" + processVO);

			if (ProcessStatus.OTP_MISMATCHED == processVO.getProcessStatus()) {
				// otp mismatched
				request.setAttribute("errormsg",
						"<div class='alert alert-danger' style='height:70px'><a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>OTP value does not match.Please try again.</div>");
				request.setAttribute(HttpUtils.userIdParam, username);
				request.setAttribute(HttpUtils.otp0Param, param0);
				request.setAttribute(HttpUtils.optionParam, "otp");
				request.setAttribute("div2", "hidden");
				request.setAttribute("autofocus", "autofocus");

				RequestDispatcher rqsDispatcher = request.getRequestDispatcher("jsp/forgotpassword1.jsp");
				// redirect to forgot password page
				rqsDispatcher.forward(request, response);

			} else if (ProcessStatus.OTP_MATCHED == processVO.getProcessStatus()) {
				// otp matched
				request.setAttribute(HttpUtils.userIdParam, username);

				RequestDispatcher rqsDispatcher = request.getRequestDispatcher("jsp/forgotpassword2.jsp");
				// redirect to forgot password page
				rqsDispatcher.forward(request, response);

			} else if (ProcessStatus.EXCEPTION == processVO.getProcessStatus()) {
				// exception handling
			}

		} else if (option.equalsIgnoreCase("secret")) {

			final String secretQuestion = request.getParameter(HttpUtils.secretQuestionParam).trim();
			final String param0 = request.getParameter(HttpUtils.answer0Param).trim();
			final String param1 = request.getParameter(HttpUtils.answer1Param).trim();

			// auth using otp
			final ProcessVO processVO = this.loginService.forgotPassword2(username, option, param0, param1);

			logger.info("Result returned from service:" + processVO);

			if (ProcessStatus.ANSWER_MISMATCHED == processVO.getProcessStatus()) {
				// answer mismatched
				request.setAttribute("errormsg",
						"<div class='alert alert-danger' style='height:70px'><a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>OTP value does not match.Please try again.</div>");
				request.setAttribute(HttpUtils.userIdParam, username);
				request.setAttribute(HttpUtils.secretQuestionParam, secretQuestion);
				request.setAttribute(HttpUtils.answer0Param, param0);
				request.setAttribute(HttpUtils.optionParam, "secret");
				request.setAttribute("div1", "hidden");
				request.setAttribute("autofocus", "autofocus");

				RequestDispatcher rqsDispatcher = request.getRequestDispatcher("jsp/forgotpassword2.jsp");
				// redirect to forgot password page
				rqsDispatcher.forward(request, response);

			} else if (ProcessStatus.ANSWER_MATCHED == processVO.getProcessStatus()) {
				// answer matched
				request.setAttribute(HttpUtils.userIdParam, username);

				RequestDispatcher rqsDispatcher = request.getRequestDispatcher("jsp/forgotpassword2.jsp");
				// redirect to forgot password page
				rqsDispatcher.forward(request, response);

			} else if (ProcessStatus.EXCEPTION == processVO.getProcessStatus()) {
				// exception handling
			}

		}
	}
}