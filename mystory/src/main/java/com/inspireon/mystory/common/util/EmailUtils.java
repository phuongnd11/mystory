package com.inspireon.mystory.common.util;

import java.nio.charset.Charset;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtils {

	public static final String HOST = "smtp.gmail.com";
	public static final String FROM = "mystory.vn";
	public static final String PASS = "changetheworld";
	public static final String PORT = "587";
	public static final String AUTH = "true";
	public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
	
	
	public static void sendEmail(String receiver, String subject, String content) throws AddressException, MessagingException{
	
	    Properties props = System.getProperties();
	    props.put("mail.smtp.starttls.enable", "true"); // added this line
	    props.put("mail.smtp.host", HOST);
	    props.put("mail.smtp.user", FROM);
	    props.put("mail.smtp.password", PASS);
	    props.put("mail.smtp.port", PORT);
	    props.put("mail.smtp.auth", AUTH);

	    String[] to = {receiver}; // added this line

	    Session session = Session.getDefaultInstance(props, null);
	    MimeMessage message = new MimeMessage(session);
	    message.setFrom(new InternetAddress(FROM));
	    message.setHeader("Content-Type", "text/plain; charset=UTF-8");
	    
	    InternetAddress[] toAddress = new InternetAddress[to.length];

	    // To get the array of addresses
	    for( int i=0; i < to.length; i++ ) { // changed from a while loop
	        toAddress[i] = new InternetAddress(to[i]);
	    }

	    for( int i=0; i < toAddress.length; i++) { // changed from a while loop
	        message.addRecipient(Message.RecipientType.TO, toAddress[i]);
	    }
	    
	    String sub = new String(subject.getBytes(), UTF8_CHARSET);
	    String con = new String(content.getBytes(), UTF8_CHARSET);
	    
	    message.setSubject(sub);
	    message.setText(con);
	    Transport transport = session.getTransport("smtp");
	    transport.connect(HOST, FROM, PASS);
	    transport.sendMessage(message, message.getAllRecipients());
	    transport.close();
	}
	
}
