package cn.ddcome.util;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cn.ddcome.service.EmailIdentifyCodeService;
import net.sf.json.JSONObject;

public class SendMail {
	
	public static String randomNumber(int num) {   
        String randomNumberStr = "";   
        String[] letters = 
        		new String[]{"0","1","2","3","4","5","6","7","8","9"};
        //创建不带种子的测试Random对象   
        Random random = new Random();   
        for(int i=0; i<num; i++) {
        	int index = random.nextInt(letters.length);
        	randomNumberStr += letters[index];
        }
        return randomNumberStr;
	}   
	
	public static MimeMessage createMail(Session session) throws AddressException, MessagingException {
		MimeMessage mm = new MimeMessage(session);
		//设置发件人
		mm.setFrom(new InternetAddress("lxd_ddcome@163.com"));
		
		//设置收件人
		mm.setRecipient(Message.RecipientType.TO, new InternetAddress("1968490710@qq.com"));
		
		mm.setSubject("DDCOME的邮件");
		
		String identifyCode = randomNumber(5);

		String cont = "您的identifying code为["+identifyCode+"],复制code可以评论,很感谢您的留言";
		
		//存储identifyCode
		EmailIdentifyCodeService.getInstance().insert(identifyCode);
		
		mm.setContent(cont,"text/html;charset=gbk");
		
		return mm;
	}

	public static void sendMail() {
		Properties prop=new Properties();
		prop.put("mail.host","smtp.163.com" );
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", "true");
		
		
		//使用java发送邮件的5步骤
		try {
			Authentication authentication = new Authentication("lxd_ddcome@163.com","luckboy2017DD");

			
			//1.创建session
			Session session = Session.getDefaultInstance(prop, authentication);
			session.setDebug(true);
			
			
			//2.通过session获取Transport对象（发送邮件的核心API）
			Transport ts = session.getTransport();
			
			//3.通过邮件用户名密码连接
			ts.connect("smtp.163.com",25,"lxd_ddcome@163.com","luckboy2017DD");
			
			//4.创建邮件
			Message msg = createMail(session);
			
			//5.发送邮件
			ts.sendMessage(msg, msg.getAllRecipients());
			
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
	
}
