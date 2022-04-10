package com.hdp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hdp.service.PredictionService;
import com.hdp.utils.HttpUtils;
import com.hdp.utils.ProcessStatus;
import com.hdp.vo.ProcessVO;

public class PredictionController extends CommonController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4814427275414468L;

	private static final Logger logger = LogManager.getLogger(PredictionController.class);

	private final PredictionService predictionService;

	public PredictionController() {
		//
		this.predictionService = new PredictionService();
	}

	@Override
	public void doService(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// read request parameters
		final String age = request.getParameter(HttpUtils.ageParam).trim();
		final String gender = request.getParameter(HttpUtils.genderParam).trim();
		final String cig = request.getParameter(HttpUtils.cigParam).trim();
		final String chol = request.getParameter(HttpUtils.cholParam).trim();
		final String dia = request.getParameter(HttpUtils.diaParam).trim();
		final String sys = request.getParameter(HttpUtils.sysParam).trim();
		final String diab = request.getParameter(HttpUtils.diabParam).trim();
		final String glu = request.getParameter(HttpUtils.gluParam).trim();
		final String heartrate = request.getParameter(HttpUtils.heartRateParam).trim();

		final HttpSession session = request.getSession(false);
		final String emailId = session.getAttribute(HttpUtils.userIdParam).toString();

		logger.info("Request parameters emailId:" + emailId + " age:" + age + " gender:" + gender + " cig:" + cig
				+ " chol:" + chol + " dia:" + dia + " sys:" + sys + " diab:" + diab + " glu:" + glu + " heartrate:"
				+ heartrate);

		final String fname = session.getAttribute(HttpUtils.fnameParam).toString();
		// predict result
		final ProcessVO processVO = this.predictionService.predict(fname, emailId, age, gender, cig, chol, dia, sys,
				diab, glu, heartrate);

		logger.info("Result returned from service:" + processVO);

		if (ProcessStatus.PREDICTION_SUCCESS == processVO.getProcessStatus()) {
			// prediction completed

			String msg1 = Double.valueOf(processVO.getProcessAttributes().get(HttpUtils.predictParam).toString()) == 1
					? "<div class='alert alert-danger' style='text-align: center;'>Danger Zone!!</div>"
					: "<div class='alert alert-success' style='text-align: center;'>Safe Zone!!</div>";
			request.setAttribute("msg", msg1);

			RequestDispatcher rqsDispatcher = request.getRequestDispatcher("jsp/output.jsp");
			// redirect to result page
			rqsDispatcher.forward(request, response);
		} else if (ProcessStatus.EXCEPTION == processVO.getProcessStatus()) {
			// exception handling
		}
	}
}