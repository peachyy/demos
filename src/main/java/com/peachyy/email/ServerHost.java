package com.peachyy.email;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 模拟一个真实的邮件服务器 可以用来执行发送邮件的具体操作
 * @author peach
 *
 */
public class ServerHost {
	public static int THREADSIZE=5;
	/**
	 * 事件监听器  可以用来记录日志等
	 */
	private List<SendEmailListeners> listeners=new ArrayList<SendEmailListeners>();
	private static ServerHost emailServer=new ServerHost();
	public static Properties smtp;
	//single
	private ServerHost(){}
	private static  ExecutorService theadPool; 
	public  static ServerHost getInstance(){
		smtp=new Properties();
		smtp.put("mail.smtp.host", "c1.icoremail.net");
		smtp.put("mail.sender.username", "test@yxologistics.com");
		smtp.put("mail.sender.password", "yewuxitong");
		smtp.put("debug", true);
		theadPool = Executors.newFixedThreadPool(THREADSIZE);  
		return emailServer;
	}
	public void putEmailListener(SendEmailListeners lis){
		listeners.add(lis);
	}
	/**
	 * 发送邮件
	 */
	public void sendEmail(final SimpleEmail email){
		this.theadPool.execute(new Runnable() {

			public void run() {
				Transport tran=null;
				String result=null;
				try {
					Session session=getSession();
					doBeforeSend(getEmailContext(email,EmailContext.RESULT_WATING));			
					Message  message=handlerBuildMessage(session,email);
					//创建邮件传输协议SMTP
					tran=session.getTransport("smtp");
					//smtp验证
					tran.connect(smtp.getProperty("mail.smtp.host"),smtp.getProperty("mail.sender.username"), smtp.getProperty("mail.sender.password"));
					//执行发送
					tran.send(message,message.getAllRecipients());
					result=EmailContext.RESULT_SUCCESS;
				} catch (Exception e) {
					EmailContext context=getEmailContext(email,EmailContext.RESULT_ERROR);
					context.setThrowable(e);
					doErrorSend(context);
					result=EmailContext.RESULT_ERROR;
					e.printStackTrace();
				}finally{
					//关闭协议流
					try {
						tran.close();
						doAfterSend(getEmailContext(email,result));
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				}
				
			} 
			
			
		});

		
	}
	public void sendMail(List<SimpleEmail> emails){
		if(emails!=null && emails.size()>0){
			for (SimpleEmail simpleEmail : emails) {
				sendEmail(simpleEmail);
			}
		}
	}
	private EmailContext getEmailContext(SimpleEmail email,String status){
		EmailContext context=new EmailContext();
		context.setEmail(email);
		context.setStatus(status);		
		return context;
	}
	//////定义发送邮件的发送细节
	/**
	 * 得到会话对象
	 * @return
	 */
	private Session getSession(){
		//得到seesion对象
		Session session = Session.getDefaultInstance(smtp,new CheckPOP(smtp.getProperty("mail.sender.username"), smtp.getProperty("mail.sender.password")));
		//是否调试模式 调试会输出更多的日志信息
		session.setDebug(Boolean.parseBoolean(smtp.getProperty("debug")));	
		return session;
	}
	/**
	 * 把字符串转换为 发送邮件时可用的邮件地址
	 * @param lst
	 * @return
	 * @throws AddressException
	 */
	private InternetAddress[] parseInternetAddress(List<String> lst) throws AddressException{
		InternetAddress[] at=null;
		if(lst==null ||lst.size()<0){
			return at;
		}
		at=new InternetAddress[lst.size()];
		for (int i=0;i<lst.size();i++) {
			at[i]=new InternetAddress(lst.get(i));
		}
		return at;
	}	
	/**
	 * 处理邮件邮件消息对象  包含收件人信息
	 * @param session
	 * @param sm
	 * @return
	 * @throws MessagingException 
	 * @throws AddressException 
	 * @throws UnsupportedEncodingException 
	 */
	private Message handlerBuildMessage(Session session,SimpleEmail sm) throws AddressException, MessagingException, UnsupportedEncodingException{
		//创建minmemessage对象 
		MimeMessage message = new MimeMessage(session);
		//创建发件人 并设置到message属性
		message.setFrom(new InternetAddress(smtp.getProperty("mail.sender.username")));
		//设置邮件主题
		message.setSubject(sm.getSubject());
		List<String> rec=sm.getReceiveUser();
		//转换为Internetaddress[]
		InternetAddress[] sendTo = parseInternetAddress(rec);

		//设置收件人
		message.setRecipients(RecipientType.TO, sendTo);
		//设置抄送人
		if(sm.getCcUser()!=null && sm.getCcUser().size()>0){
			InternetAddress[] sendCC = parseInternetAddress(sm.getCcUser());
			message.setRecipients(RecipientType.CC, sendCC); // 抄送人 
		}
		//设置秘密抄送人
		if(sm.getBccUser()!=null && sm.getBccUser().size()>0){
			InternetAddress[] sendCC = parseInternetAddress(sm.getBccUser());
			message.setRecipients(RecipientType.BCC, sendCC); // 秘密抄送人 
		}		
		//创建邮件正文对象 可以包含附件 文件 等
		Multipart multipart = new MimeMultipart();
		
		//正文
		BodyPart contentPart = new MimeBodyPart();
		contentPart.setContent(sm.getContent(), "text/html;charset=UTF-8");
		multipart.addBodyPart(contentPart);
		
		// 添加附件的内容 如果有则添加附件
		List<String> filenames=sm.getFilenames();
		if(filenames!=null&&filenames.size()>0){
			for (String  str: filenames) {
				//附件正文对象
				MimeBodyPart attrMBP = new MimeBodyPart(); 
				//文件对象
				FileDataSource fds=new FileDataSource(str);
				//添加文件流到附件对象
				attrMBP.setDataHandler(new DataHandler(fds));
				//设置文件的文件名<code>MimeUtility.encodeText</code>是防止发送时文件名乱码
				attrMBP.setFileName(MimeUtility.encodeText(fds.getName()));
				//最后添加附件正文对象到正文主题中...
				multipart.addBodyPart(attrMBP);
			}
		}
		//将混合正文对象添加到消息上下文中
		message.setContent(multipart);
		//设置邮件发送日期
		message.setSentDate(new Date());
		message.saveChanges();		
		return message;
	}
	
	////监听事件
	public void doBeforeSend(EmailContext context){
		for (SendEmailListeners lis : listeners) {
			lis.beforeSendEmail(context);
		}
	}
	public void doAfterSend(EmailContext context){
		for (SendEmailListeners lis : listeners) {
			lis.afterSendEmail(context);
		}		
	}
	public void doErrorSend(EmailContext context){
		for (SendEmailListeners lis : listeners) {
			lis.errorEmail(context);
		}	
	}
}
