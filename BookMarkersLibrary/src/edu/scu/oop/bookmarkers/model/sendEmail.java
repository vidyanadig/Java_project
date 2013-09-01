package edu.scu.oop.bookmarkers.model;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

public class sendEmail implements Runnable {
	String emailID;
	String itemTitle;
	
	
	public sendEmail (String emailID, String itemTitle) {
		this.emailID = emailID;
		this.itemTitle = itemTitle;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//1. Set some properties
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

		Properties props = new Properties();
		props.setProperty("mail.smtps.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.setProperty("mail.smtps.auth", "true");

		Session session = Session.getInstance(props, null);

		props.put("mail.smtps.quitwait", "false");

		//2. Set the msg body
		String msgBody = "The book you reserved " + itemTitle + " is available! Please stop by.";

		//3. Create a new message
		try {
			Message msg = new MimeMessage(session);
			//msg.setFrom(new InternetAddress("mailnadig@gmail.com"));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailID, false));
			msg.setSubject("Your Book is available at Bookmarkers!");
			msg.setText(msgBody);
			msg.setSentDate(new Date());

			System.out.println("Sending email to" + emailID + " about " + itemTitle + ". This might take a while");

			SMTPTransport t = (SMTPTransport)session.getTransport("smtps");
			//4. Send out email from coen275java@gmail.com and password exceptions
			t.connect("smtp.gmail.com", "coen275java@gmail.com", "exceptions");
			t.sendMessage(msg, msg.getAllRecipients());      
			t.close();
		} catch (AddressException e) {
			// ...
		} catch (MessagingException e) {
			// ...
		}	
	}

}
