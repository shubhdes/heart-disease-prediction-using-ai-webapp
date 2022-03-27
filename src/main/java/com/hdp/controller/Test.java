package com.hdp.controller;

import java.io.IOException;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class Test {

	/** file names are defined */
	public static final String TRAINING_DATA_SET_FILENAME = "hdp_train_out.csv";
	public static final String TESTING_DATA_SET_FILENAME = "hdp_test_out.csv";
	public static final String PREDICTION_DATA_SET_FILENAME = "hdp_prediction.csv";

	/**
	 * This method is to load the data set.
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static Instances getDataSet(String fileName) throws IOException {
		/**
		 * we can set the file i.e., loader.setFile("finename") to load the data
		 */
		int classIdx = 9;
		/** the arffloader to load the arff file */
		CSVLoader loader = new CSVLoader();
		// loader.setFile(new File(fileName));
		/** load the traing data */
		loader.setSource(Test.class.getResourceAsStream("/" + fileName));
		/**
		 * we can also set the file like loader3.setFile(new
		 * File("test-confused.arff"));
		 */
		Instances dataSet = loader.getDataSet();
		/** set the index based on the data given in the arff files */
		dataSet.setClassIndex(dataSet.numAttributes() - 1);
		return dataSet;
	}

	/**
	 * This method is used to process the input and return the statistics.
	 * 
	 * @throws Exception
	 */
	public static void process() throws Exception {

		Instances trainingDataSet = getDataSet(TRAINING_DATA_SET_FILENAME);
		Instances testingDataSet = getDataSet(TESTING_DATA_SET_FILENAME);
		/** Classifier here is Linear Regression */
		Classifier classifier = new weka.classifiers.functions.Logistic();
		/** */
		classifier.buildClassifier(trainingDataSet);
		/**
		 * train the alogorithm with the training data and evaluate the algorithm with
		 * testing data
		 */
		Evaluation eval = new Evaluation(trainingDataSet);
		eval.evaluateModel(classifier, testingDataSet);
		/** Print the algorithm summary */
		System.out.println("** Logistic Regression Evaluation with Datasets **");
		System.out.println(eval.toSummaryString());
		System.out.print(" the expression for the input data as per alogorithm is ");
		System.out.println(classifier);

//		System.out.println("Actual                |                 Predicted");
//		for (int i = 0; i < testingDataSet.numInstances(); i++) {
//			double actualClass = testingDataSet.instance(i).classValue();
//			String actualClassStr = testingDataSet.classAttribute().value((int) actualClass);
//
//			double expectedClass = classifier.classifyInstance(testingDataSet.instance(i));
//			String expectedClassStr = testingDataSet.classAttribute().value((int) expectedClass);
//			System.out.println(actualClassStr + "                |                 " + expectedClassStr);
//		}

//		Instance predicationDataSet = getDataSet(PREDICTION_DATA_SET_FILENAME).lastInstance();
//		double value = classifier.classifyInstance(predicationDataSet);
//		System.out.println(value);
//		// predicationDataSet.setClassValue(value);
//		String prediction = predicationDataSet.classAttribute().value((int) value);
//		/** Prediction Output */
//		System.out.println(prediction);

		Instance predictionDataSet = new DenseInstance(10);
		predictionDataSet.setValue(0, 50);
		predictionDataSet.setValue(1, 0);
		predictionDataSet.setValue(2, 0);
		predictionDataSet.setValue(3, 260);
		predictionDataSet.setValue(4, 1);
		predictionDataSet.setValue(5, 190);
		predictionDataSet.setValue(6, 130);
		predictionDataSet.setValue(7, 260);
		predictionDataSet.setValue(8, 85);

		predictionDataSet.setDataset(trainingDataSet);

		double value = classifier.classifyInstance(predictionDataSet);
		System.out.println(value);
		// predictionDataSet.setClassValue(value);
		String prediction = predictionDataSet.classAttribute().value((int) value);
		/** Prediction Output */
		System.out.println(prediction);
	}

	public static void main(String[] args) throws Exception {
		process();
	}

}
