package com.hdp.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hdp.utils.HttpUtils;
import com.hdp.utils.MailingUtils;
import com.hdp.utils.PredictionUtils;
import com.hdp.utils.ProcessStatus;
import com.hdp.vo.ProcessVO;

public class PredictionService {

	private static final Logger logger = LogManager.getLogger(PredictionService.class);

	public PredictionService() {
		//
	}

	public ProcessVO predict(final String fname, final String username, final String age, final String gender,
			final String cig, final String chol, final String dia, final String sys, final String diab,
			final String glu, final String heartrate) throws MalformedURLException, IOException {
		//
		try {
			logger.info("Logistic model call to retrieve prediction started");
			double predictionClass = PredictionUtils.predict(age, gender, cig, chol, dia, sys, diab, glu, heartrate);
			logger.info("Logistic model call to retrieve prediction completed prediction:" + predictionClass);

			// mail body
			String body = predictionClass == 1 ? "danger zone" : "safe zone";

			// send prediction report email
			logger.info("Sending email for predicition request");
			MailingUtils.predictionEmail(new String[] { fname, body }, username);
			logger.info("Email sent for predicition request");

			return new ProcessVO(ProcessStatus.PREDICTION_SUCCESS,
					Collections.singletonMap(HttpUtils.predictParam, predictionClass));
		} catch (Exception ex) {
			// exception handling
			logger.error("Exception occured while processing request:" + ex.getMessage());
			return new ProcessVO(ProcessStatus.EXCEPTION,
					Collections.singletonMap(HttpUtils.exceptionMsg, ex.getMessage()));
		}
	}
}
