package com.hdp.utils;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class MailingUtils {

	public static Properties emailProperties;

	// File for email configurations
	public static final String configFileName = "config/emailConfig.properties";

	public static final Properties serverConfigs = new Properties();

	// mail server configs
	public static final String mailSmtpStartTlsEnable = "mail.smtp.starttls.enable";
	public static final String mailSmtpAuth = "mail.smtp.auth";
	public static final String mailSmtpHost = "mail.smtp.host";
	public static final String mailSmtpPort = "mail.smtp.port";

	public static final String userId = "userId";
	public static final String password = "password";

	// email configs
	public static final String registrationSubject = "registration.subject";
	public static final String registrationBody = "registration.body";

	public static final String forgotPasswordSubject = "forgotPassword.subject";
	public static final String forgotPasswordBody = "forgotPassword.body";

	public static final String predictionSubject = "prediction.subject";
	public static final String predictionBody = "prediction.body";

	public static void load(final String path) throws IOException {
		// load email.properties
		emailProperties = CommonUtils.loadFileToProperties(Paths.get(path, configFileName).toString());

		// load mail server configs
		serverConfigurations();
	}

	public static void serverConfigurations() {
		// mail server configs
		serverConfigs.put("mail.smtp.starttls.enable", emailProperties.get(mailSmtpStartTlsEnable).toString());
		serverConfigs.put("mail.smtp.auth", emailProperties.get(mailSmtpAuth).toString());
		serverConfigs.put("mail.smtp.host", emailProperties.get(mailSmtpHost).toString());
		serverConfigs.put("mail.smtp.port", emailProperties.get(mailSmtpPort).toString());
	}

	public static Session session() {
		// mail server authentication
		Session session = Session.getInstance(serverConfigs, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailProperties.getProperty(userId).toString(),
						emailProperties.getProperty(password).toString());
			}
		});
		return session;
	}

	public static void registrationEmail(final String[] msgParams, final String toEmailId)
			throws AddressException, MessagingException {
		//
		email(emailProperties.getProperty(userId), toEmailId, emailProperties.getProperty(registrationSubject),
				MessageFormat.format(emailProperties.getProperty(registrationBody), msgParams));
	}

	public static void forgotPasswordEmail(final String[] msgParams, final String toEmailId)
			throws AddressException, MessagingException {
		//
		email(emailProperties.getProperty(userId), toEmailId, emailProperties.getProperty(forgotPasswordSubject),
				MessageFormat.format(emailProperties.getProperty(forgotPasswordBody), msgParams));
	}

	public static void predictionEmail(final String[] msgParams, final String toEmailId)
			throws AddressException, MessagingException {
		//
		email(emailProperties.getProperty(userId), toEmailId, emailProperties.getProperty(predictionSubject),
				MessageFormat.format(emailProperties.getProperty(predictionBody), msgParams));
	}

	public static void email(final String fromEmailId, final String toEmailId, final String subject, final String body)
			throws AddressException, MessagingException {
		//
		final MimeMessage msg = new MimeMessage(session());
		// add sender
		msg.setFrom(new InternetAddress(fromEmailId));
		// add receiver
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailId));
		// add subject
		msg.setSubject(subject);
		// add body
		msg.setContent(body, "text/html; charset=utf-8");
		// send email
		Transport.send(msg);
	}
}
