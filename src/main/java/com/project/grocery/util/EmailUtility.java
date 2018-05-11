package com.project.grocery.util;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtility {
	
	public static void sendEMail(String to,String from,String username,String name,String subject,String pass){
		// Get the session object
				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");

				Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(from, pass);// change
																							// accordingly
					}
					});
					try {
						MimeMessage message1 = new MimeMessage(session);
						message1.setFrom(new InternetAddress(from));// change
																					// accordingly
						message1.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
						message1.setSubject("Message");
						message1.setText("UserName:"+username);
						message1.setText("Name:"+name);
						Transport.send(message1);

						System.out.println("message sent successfully");

					} catch (MessagingException e) {
						throw new RuntimeException(e);
					}
					
				}
	
	
	//.............................................
	
	public static void sendNewPassword(String to, String password) {
		// Get the session object
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("deliversewa11@gmail.com","Delivery@987");// change
				// accordingly
			}
		});

		// compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("deliversewa11@gmail.com"));// change
																			// accordingly
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Password Recovery ");
			message.setText("Your new password is......." + password);

			// send message
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	
	}




