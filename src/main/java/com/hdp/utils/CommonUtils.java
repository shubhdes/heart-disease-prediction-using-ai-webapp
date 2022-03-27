package com.hdp.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class CommonUtils {

	public static Properties loadFileToProperties(final String resourcePath) throws IOException {
		Properties resourceProps = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(resourcePath);
			resourceProps.load(inputStream);
		} finally {
			// closing resources
			if (null != inputStream) {
				inputStream.close();
			}
		}
		return resourceProps;
	}

	public static String otp() {
		Random random = new Random();
		StringBuilder otp = new StringBuilder();
		for (int i = 1; i <= 4; i++) {
			otp.append(random.nextInt(10));
		}
		return otp.toString();
	}
}
