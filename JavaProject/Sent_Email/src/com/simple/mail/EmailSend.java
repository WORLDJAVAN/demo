package com.simple.mail;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * <pre>
 *   Title: EmailSend.java
 *   Description: 简单邮件发送
 *   Project:member project
 *   Copyright: yundaex.com Copyright (c) 2013
 *   Company: shanghai yundaex
 * </pre>
 * 
 */
public class EmailSend {	
	public static boolean sendTextMail(Sender mailInfo) {		
		// 判断是否需要身份认证
		Properties pro = mailInfo.getProperties();
		
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, new Mailnfor(mailInfo.getUserName(), mailInfo.getPassword()));
		
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			
			// 设置邮件消息的主要内容
			Multipart multipart = new MimeMultipart();         
            //设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText(mailInfo.getContent());
            multipart.addBodyPart(contentPart);
            //添加附件
            BodyPart messageBodyPart= new MimeBodyPart();
            DataSource source = new FileDataSource(mailInfo.getAffix());
            //添加附件的内容
            messageBodyPart.setDataHandler(new DataHandler(source));
            //添加附件的标题
            //这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
            sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
            messageBodyPart.setFileName("=?GBK?B?"+enc.encode(mailInfo.getAffixName().getBytes())+"?=");
            multipart.addBodyPart(messageBodyPart);
          
            mailMessage.setContent(multipart);

			// 发送邮件
			Transport.send(mailMessage);
			
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}	
}