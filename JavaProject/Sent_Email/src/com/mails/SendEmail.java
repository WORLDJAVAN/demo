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
 * �ʼ�����
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
	 * �ʼ�����
	 * @param receiver �������б�
	 * @param copy �������б�
	 * @param secreter �������б�
	 * @param subject �ʼ�����
	 * @param content �ʼ�����
	 * @param fileList �����б�
	 * @return String(���ͳɹ�/����ʧ��)
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

			/* �����Ự */
			Session session = Session.getInstance(p);
			Message msg = new MimeMessage(session); // ������Ϣ
			
			BodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart(); // ��Ӹ���
			msg.setFrom(new InternetAddress(userEmail)); // ��������Ϣ

			String toReceiverList = null;
			String toCopyList = null;
			String toSecreterList = null;

			/* ����ռ����嵥 */
			if (receiver != null) {
				toReceiverList = getMailList(receiver);
				InternetAddress[] iaToList = new InternetAddress().parse(toReceiverList);
				msg.setRecipients(Message.RecipientType.TO, iaToList); 
			}

			/* ��ӳ������嵥 */
			if (copy != null) {
				toCopyList = getMailList(copy);
				InternetAddress[] iaToListcs = new InternetAddress().parse(toCopyList);
				msg.setRecipients(Message.RecipientType.CC, iaToListcs);
			}

			/* ����������嵥 */
			if (secreter != null) {
				toSecreterList = getMailList(secreter);
				InternetAddress[] iaToListms = new InternetAddress().parse(toSecreterList);
				msg.setRecipients(Message.RecipientType.BCC, iaToListms);
			}
			
			msg.setSentDate(new Date()); // ��������
			msg.setSubject(subject); // ����
			msg.setText(content); // ����

			/* ��ʾ��HTML��ʽ���ı�����  */
			messageBodyPart.setContent(content, "text/html;charset=gbk");
			multipart.addBodyPart(messageBodyPart);

			/* ����������  */
			if (fileList != null) {
				addTach(fileList, multipart);
			}

			msg.setContent(multipart);

			/* �ʼ�������������֤ */
			Transport transport = session.getTransport("smtp");
			transport.connect(serverHost, userEmail,password);
			transport.sendMessage(msg, msg.getAllRecipients()); 
			return "���ͳɹ�";

		} catch (Exception e) {
			e.printStackTrace();
			return "����ʧ��";			
		}
	}

	/**
	 * ��ø����б�
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
     * ��÷����б�
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
