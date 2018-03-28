package com.mails;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
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
import javax.mail.internet.MimeUtility;

/**
 * 邮件发送
 * @author JOE
 *
 */
public class SendEmail {
	private static SendEmail instance = null;
	private static String serverHost = null;
	private static String serverPort = null;
	private static String userEmail = null;
	private static String password = null;

	static {
		Properties prop = new Properties();
		InputStream in = Object.class.getResourceAsStream("/email.properties");
		try {
			prop.load(in);
			serverHost = prop.getProperty("serverHost").trim();
			serverPort = prop.getProperty("serverPort").trim();
			userEmail = prop.getProperty("userEmail").trim();
			password = prop.getProperty("password").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private SendEmail() {
	}

	public static SendEmail getInstance() {
		if (instance == null) {
			instance = new SendEmail();
		}
		return instance;
	}

	/**
	 * 邮件发送
	 * @param receiver 接收者列表
	 * @param copy 抄送者列表
	 * @param secreter 秘送者列表
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @param fileList 附件列表
	 * @return String(发送成功/发送失败)
	 */
	@SuppressWarnings("static-access")
	public String sendEmail(String receiver[], String copy[], String secreter[], 
			String subject,String content, String fileList[]) {
		try {
			Properties p = new Properties();
			p.put("mail.smtp.auth", "true");
			p.put("mail.transport.protocol", "smtp");
			p.put("mail.smtp.host", serverHost);
			p.put("mail.smtp.port", serverPort);

			/* 建立会话 */
			Session session = Session.getInstance(p);
			Message msg = new MimeMessage(session); // 建立信息
			
			BodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart(); // 添加附件
			msg.setFrom(new InternetAddress(userEmail)); // 发件人信息

			String toReceiverList = null;
			String toCopyList = null;
			String toSecreterList = null;

			/* 添加收件人清单 */
			if (receiver != null) {
				toReceiverList = getMailList(receiver);
				InternetAddress[] iaToList = new InternetAddress().parse(toReceiverList);
				msg.setRecipients(Message.RecipientType.TO, iaToList); 
			}

			/* 添加抄送人清单 */
			if (copy != null) {
				toCopyList = getMailList(copy);
				InternetAddress[] iaToListcs = new InternetAddress().parse(toCopyList);
				msg.setRecipients(Message.RecipientType.CC, iaToListcs);
			}

			/* 添加密送人清单 */
			if (secreter != null) {
				toSecreterList = getMailList(secreter);
				InternetAddress[] iaToListms = new InternetAddress().parse(toSecreterList);
				msg.setRecipients(Message.RecipientType.BCC, iaToListms);
			}
			
			msg.setSentDate(new Date()); // 发送日期
			msg.setSubject(subject); // 主题
			msg.setText(content); // 内容

			/* 显示以HTML格式的文本内容  */
			messageBodyPart.setContent(content, "text/html;charset=gbk");
			multipart.addBodyPart(messageBodyPart);

			/* 保存多个附件  */
			if (fileList != null) {
				addTach(fileList, multipart);
			}

			msg.setContent(multipart);

			/* 邮件服务器进行验证 */
			Transport transport = session.getTransport("smtp");
			transport.connect(serverHost, userEmail,password);
			transport.sendMessage(msg, msg.getAllRecipients()); 
			return "发送成功";

		} catch (Exception e) {
			e.printStackTrace();
			return "发送失败";			
		}
	}

	/**
	 * 获得附件列表
	 * @param fileList
	 * @param multipart
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	private void addTach(String fileList[], Multipart multipart)
			throws MessagingException, UnsupportedEncodingException {
		for (int index = 0; index < fileList.length; index++) {
			MimeBodyPart mailArchieve = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(fileList[index]);
			mailArchieve.setDataHandler(new DataHandler(fds));
			mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(),"GBK", "B"));
			multipart.addBodyPart(mailArchieve);
		}
	}
	
    /**
     * 获得发送列表
     * @param mailArray
     * @return
     */
	private String getMailList(String[] mailArray) {
		StringBuffer toList = new StringBuffer();
		int length = mailArray.length;
		if (mailArray != null && length < 2) {
			toList.append(mailArray[0]);
		} else {
			for (int i = 0; i < length; i++) {
				toList.append(mailArray[i]);
				if (i != (length - 1)) {
					toList.append(",");
				}
			}
		}
		return toList.toString();
	}
}
