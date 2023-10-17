package com.userservice;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.userservice.constant.ConstantVariables;

public class SendEmail {
	public void getEmail(String to, String from, String userName, String password, Properties props, String subject,
			String messageBody) throws MessagingException {
		messageBody = messageBody.replace("[[name]]","Samar Kumar Raghuvanshi");
		messageBody = messageBody.replace("[[URL]]","http://localhost:9020/api/auth/verify?code=ikvpPPcfi0Ixd41HnXCf0NvzdNYcwPqmWitBrLXR3YYZOdNeQ0fXb9UYVSt8ah8c");
		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(messageBody, "text/html");
		MimeMultipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setContent(multipart);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Have You got Mail!");
			message.setText(messageBody, "UTF-8", "html");
			Transport.send(message);
		} catch (MessagingException ex) {
			System.out.println(ex);

		}
	}

	public static void main(String arg[]) {
		SendEmail sendEmail = new SendEmail();
		String to = "sunilkmr5775@gmail.com";
		String from = "sunilkmr1349@gmail.com";
		final String username = "sunilkmr1349@gmail.com";
		final String password = "ajwbowrvpygdkqtp";
		String subject = "Html Template";

//		String body = "<i> Congratulations!</i><br>";
//		body += "<b>Your Email is working!</b><br>";
//		body += "<font color=red>Thank </font>";
		String body = ConstantVariables.EMAIL_CONTENT;
		String host = "smtp.gmail.com";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		try {
			sendEmail.getEmail(to, from, username, password, props, subject, body);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}