package com.zaloni.MailConnect;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.google.api.services.gmail.Gmail;


public class MailApp {
	public static void main(String [] args) throws MessagingException, IOException
	{
		String to = "kashyapbarua@gmail.com";
		String from ="priya300495@gmail.com";
		String user ="priya300495@gmail.com";
		String subject ="Ki laagey bhai?";
		String body_text ="Yo Yo. Dhinchak Pooja";
		File file =new File("e:\\myjdbcfile.csv");
		MimeMessage mimemessage = MailService.createEmailWithAttachment(to,from,subject,body_text, file);
		Gmail service = GmailApi.getGmailService();
		MailService.sendMessage(service,user,mimemessage);
	}
}
