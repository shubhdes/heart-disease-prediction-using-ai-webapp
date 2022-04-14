package com.hdp.utils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class OtpUtils {

	public static Properties otpProperties;

	// File for email configurations
	public static final String configFileName = "config/otpConfig.properties";

	public static final String timeoutInterval = "timeoutInterval";

	public static void load(final String path) throws IOException {
		// load email.properties
		otpProperties = CommonUtils.loadFileToProperties(Paths.get(path, configFileName).toString());
	}

	public static String config(String configKey) {
		// fetch configuration
		return otpProperties.getProperty(configKey);
	}

}
