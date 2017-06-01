package com.zaloni.MailConnect;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.google.api.services.gmail.Gmail;


public class MailApp {
	public static void main(String [] args) throws MessagingException, IOException
	{
		String to = "xodiac642@gmail.com";
		String from ="kashyapbarua@gmail.com";
		String user ="kashyapbarua@gmail.com";
		String subject ="Test Email";
		String body_text ="Test email containing CSV file";
		File file =new File("/home/xodiac/Documents/GitKraken/resource_manager_tool/myjdbcfile.csv");
		MimeMessage mimemessage = MailService.createEmailWithAttachment(to,from,subject,body_text, file);
		Gmail service = GmailApi.getGmailService();
		MailService.sendMessage(service,user,mimemessage);
	}
}
