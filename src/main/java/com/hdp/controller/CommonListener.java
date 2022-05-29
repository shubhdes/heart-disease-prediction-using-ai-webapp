package com.hdp.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hdp.utils.DBConnectionUtils;
import com.hdp.utils.MailingUtils;
import com.hdp.utils.OtpUtils;
import com.hdp.utils.PredictionUtils;

public class CommonListener implements ServletContextListener {

	private static final Logger logger = LogManager.getLogger(CommonListener.class);

	@Override
	public void contextInitialized(ServletContextEvent context) {
		//
		try {
			// db config loading
			logger.info("Loading database configurations started");
			DBConnectionUtils.load(context.getServletContext().getRealPath(""));
			logger.info("Loading database configurations completed");

			// mail config loading
			logger.info("Loading mail configurations started");
			MailingUtils.load(context.getServletContext().getRealPath(""));
			logger.info("Loading mail configurations completed");

			// prediction config loading
			logger.info("Loading prediction configurations started");
			logger.info(PredictionUtils.load(context.getServletContext().getRealPath("")).toSummaryString());
			logger.info("Loading prediction configurations completed");

			// otp config loading
			logger.info("Loading otp configurations started");
			OtpUtils.load(context.getServletContext().getRealPath(""));
			logger.info("Loading otp configurations completed");

		} catch (Exception ex) {
			// exception handling
			logger.error("Exception occured on application startup:" + ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// close db connection pool
		logger.info("Closing database connection pool started");
		DBConnectionUtils.datasource().close();
		logger.info("Closing database connection pool completed");
	}
}
