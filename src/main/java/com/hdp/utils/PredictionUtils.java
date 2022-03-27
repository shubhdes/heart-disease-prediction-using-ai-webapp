package com.hdp.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class PredictionUtils {

	public static Properties emailProperties;

	// File for model training
	public static final String trainingDataFileName = "config/hdp_train.csv";
	// File for model testing
	public static final String testingDataFileName = "config/hdp_test.csv";

	public static Instances trainingDataSet;

	public static Instances testingDataSet;

	public static Classifier model;

	public static void load(final String path) throws Exception {

		// load training data from csv
		trainingDataSet = dataSet(path, trainingDataFileName);

		// load testing data from csv
		testingDataSet = dataSet(path, testingDataFileName);

		// create logistic regression model
		model();
	}

	public static Instances dataSet(final String path, final String fileName) throws IOException {
		// load data from csv file
		final CSVLoader loader = new CSVLoader();
		loader.setSource(new FileInputStream(Paths.get(path, fileName).toString()));

		final Instances dataSet = loader.getDataSet();

		// class attribute present at last column
		dataSet.setClassIndex(dataSet.numAttributes() - 1);

		return dataSet;
	}

	public static void model() throws Exception {

		model = new Logistic();
		model.buildClassifier(trainingDataSet);

		final Evaluation eval = new Evaluation(trainingDataSet);
		eval.evaluateModel(model, testingDataSet);

	}

	public static double predict(final String age, final String gender, final String cig, final String chol,
			final String dia, final String sys, final String diab, final String glu, final String heartrate)
			throws Exception {

		// load prediction dataset
		final Instance predictionDataSet = new DenseInstance(10);
		predictionDataSet.setValue(0, Double.valueOf(age));
		predictionDataSet.setValue(1, Double.valueOf(gender));
		predictionDataSet.setValue(2, Double.valueOf(cig));
		predictionDataSet.setValue(3, Double.valueOf(chol));
		predictionDataSet.setValue(4, Double.valueOf(diab));
		predictionDataSet.setValue(5, Double.valueOf(sys));
		predictionDataSet.setValue(6, Double.valueOf(dia));
		predictionDataSet.setValue(7, Double.valueOf(glu));
		predictionDataSet.setValue(8, Double.valueOf(heartrate));
		predictionDataSet.setDataset(trainingDataSet);

		// prediction
		final double predictionClass = model.classifyInstance(predictionDataSet);
		return predictionClass;
	}
}
