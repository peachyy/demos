package com.peachyy.email;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class EmailTest  extends TestCase {
	public void sendEmailTest(){
		ServerHost  server=ServerHost.getInstance();
		server.putEmailListener(new DefualtListener());
		List<String> rec=new ArrayList<String>();
		List<String> filenames=new ArrayList<String>();
		List<String> cc=new ArrayList<String>();
		List<String> bcc=new ArrayList<String>();		
		rec.add("970815940@qq.com");
		rec.add("peachyy@sina.cn");
		//rec.add("872482259@qq.com");
	//	rec.add("13658398114@sina.cn");	
	//	rec.add("xiaochao9252@qq.com");
		//抄送人
		cc.add("peach_mr@sina.com");
		//秘密抄送
		bcc.add("taoxs@innoway.cn");
		//添加附件
		filenames.add("F:\\Caches\\QQ\\970815940\\FriendMsg.db");		
		SimpleEmail sm=new SimpleEmail();
		sm.setContent("fdsafds");
		sm.setFilenames(filenames);
		sm.setReceiveUser(rec);
		sm.setSubject("QQSMTP test");
		sm.setBccUser(bcc);
		sm.setCcUser(cc);	
		server.sendEmail(sm);
	}
	public static void main(String[] args) {
		ServerHost  server=ServerHost.getInstance();
		server.putEmailListener(new DefualtListener());
		List<String> rec=new ArrayList<String>();
		List<String> filenames=new ArrayList<String>();
		List<String> cc=new ArrayList<String>();
		List<String> bcc=new ArrayList<String>();		
		rec.add("970815940@qq.com");
		rec.add("peachyy@sina.cn");
		//rec.add("872482259@qq.com");
	//	rec.add("13658398114@sina.cn");	
	//	rec.add("xiaochao9252@qq.com");
		//抄送人
		cc.add("peach_mr@sina.com");
		//秘密抄送
		bcc.add("taoxs@innoway.cn");
		//添加附件
		filenames.add("F:\\Caches\\QQ\\970815940\\FriendMsg.db");		
		SimpleEmail sm=new SimpleEmail();
		sm.setContent("fdsafds");
		sm.setFilenames(filenames);
		sm.setReceiveUser(rec);
		sm.setSubject("QQSMTP test");
		sm.setBccUser(bcc);
		sm.setCcUser(cc);	
		server.sendEmail(sm);
	}
}
