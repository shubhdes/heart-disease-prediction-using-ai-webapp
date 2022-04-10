package com.hdp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hdp.utils.DBConnectionUtils;
import com.hdp.vo.PredictionVO;

public class PredictionDAO {

	private static final Logger logger = LogManager.getLogger(PredictionDAO.class);

	private static final String newPredictionQuery = "INSERT INTO prediction (emailid, age, gender, cig, chol, dia, sys, diab, glu, heartrate, result) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public void newPrediction(final PredictionVO predictionVO) throws SQLException {
		// pull connection from pool
		final Connection connection = DBConnectionUtils.connection();
		PreparedStatement prepStat = null;
		try {
			prepStat = connection.prepareStatement(newPredictionQuery);
			prepStat.setString(1, predictionVO.getEmailId());
			prepStat.setString(2, predictionVO.getAge());
			prepStat.setString(3, predictionVO.getGender());
			prepStat.setString(4, predictionVO.getCig());
			prepStat.setString(5, predictionVO.getChol());
			prepStat.setString(6, predictionVO.getDia());
			prepStat.setString(7, predictionVO.getSys());
			prepStat.setString(8, predictionVO.getDiab());
			prepStat.setString(9, predictionVO.getGlu());
			prepStat.setString(10, predictionVO.getHeartrate());
			prepStat.setString(11, predictionVO.getResult());

			logger.info("SQL newPrediction:" + newPredictionQuery + " parameters " + predictionVO);

			prepStat.executeUpdate();
		} finally {
			// closing resources
			if (null != prepStat) {
				prepStat.close();
			}
		}
	}
}
