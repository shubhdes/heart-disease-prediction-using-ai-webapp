package com.hdp.utils;

import java.util.Properties;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class HttpUtils {

	public static Properties httpProperties;

	// File for http web request response configurations
	public static final String configFileName = "config/httpConfig.properties";

	// request response parameters
	public static final String userIdParam = "txtuserid";
	public static final String passwordParam = "txtpassword";
	public static final String confirmPasswordParam = "txtconfirmpassword";
	public static final String oldPasswordParam = "txtoldpassword";
	public static final String newPasswordParam = "txtnewpassword";
	public static final String fnameParam = "txtfname";
	public static final String lnameParam = "txtlname";
	public static final String emailIdParam = "txtemailid";
	public static final String mobileParam = "txtmobile";
	public static final String secretQuestionParam = "txtsecretquestion";
	public static final String answer0Param = "txtanswer0";
	public static final String answer1Param = "txtanswer1";
	public static final String otp0Param = "txtotp0";
	public static final String otp1Param = "txtotp1";
	public static final String ageParam = "txtage";
	public static final String genderParam = "txtgender";
	public static final String cigParam = "txtcigs";
	public static final String cholParam = "txtchol";
	public static final String diaParam = "txtdia";
	public static final String sysParam = "txtsys";
	public static final String diabParam = "txtdiab";
	public static final String gluParam = "txtglu";
	public static final String heartRateParam = "txtheartrate";
	public static final String optionParam = "txtoption";
	public static final String miscParam = "miscParam";
	public static final String predictParam = "predictParam";

	// error parameters
	public static final String exceptionMsg = "exceptionmsg";
	public static final String errorMsg = "errormsg";

	// http
	public static final String httpUrl = "httpUrl";
	public static final String httpMethod = "httpMethod";
	public static final String httpCharSet = "httpCharSet";
	public static final String httpResponseName = "httpResponseName";

}
