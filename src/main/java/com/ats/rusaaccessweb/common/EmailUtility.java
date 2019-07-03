package com.ats.rusaaccessweb.common;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.web.multipart.MultipartFile;

import com.ats.rusaaccessweb.model.Info;

 
 

public class EmailUtility {

	public static Info sendEmail(String senderEmail,String senderPassword,String recipientEmail,String mailsubject,
		String text, List<MultipartFile> files ) {
		
		Info info=new Info();
		
		try {
			
		final String emailSMTPserver = "smtp.gmail.com";
		final String emailSMTPPort = "587";
		final String mailStoreType = "imaps";
		final String username = senderEmail;//"atsinfosoft@gmail.com";
		final String password =senderPassword;//"atsinfosoft@123";

		System.out.println("username: " + username);
		System.out.println("password: " + password);
		System.out.println("recipientEmail: " + recipientEmail);
		System.out.println("mailsubject: " + mailsubject);
		System.out.println("text: " + text); 

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Store mailStore = session.getStore(mailStoreType);
			mailStore.connect(emailSMTPserver, username, password);

			String address =recipientEmail;// "atsinfosoft@gmail.com";// address of to

			String subject = mailsubject;//" Login Credentials For RUSA Login  ";

			Message mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(username));
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
			mimeMessage.setSubject(subject);
			 
			Multipart multipart = new MimeMultipart(); //1
			    
			MimeBodyPart messageBodyPart = new MimeBodyPart(); 
			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(text, "text/html; charset=utf-8");
			multipart.addBodyPart(messageBodyPart);
            mimeMessage.setContent(multipart);
			 
            if(files.get(0).getOriginalFilename()!="") {
            	
            	for(int i=0;i<files.size();i++)
                {
                    //System.out.println(files.get(i));

                    messageBodyPart = new MimeBodyPart();
                    
                    File convFile = new File( files.get(i).getOriginalFilename() );
    		        FileOutputStream fos = new FileOutputStream( convFile );
    		        fos.write( files.get(i).getBytes() );
    		        fos.close(); 
                    DataSource source = new FileDataSource(convFile); 
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(files.get(i).getOriginalFilename());
                    multipart.addBodyPart(messageBodyPart);
                    mimeMessage.setContent(multipart);
                }
            }
			 
            /*for(int i=0;i<address.length;i++)
            {
            	mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address[i]));
            	Transport.send(mimeMessage);
            }*/
			
            Transport.send(mimeMessage);
            
		} catch (Exception e) {
			e.printStackTrace();
			info.setError(true);
			info.setMsg("email_exce");
		}
			
			info.setError(false);
			info.setMsg("success_email");
		}catch (Exception e) {
			
			info.setError(true);
			info.setMsg("email_exce");
		}
		
		return info;
		
	} 

}
