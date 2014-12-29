package com.peachyy.email;

import javax.mail.PasswordAuthentication;

public class CheckPOP  extends javax.mail.Authenticator {
	private String username;
	private String password;
	public CheckPOP(){}
	public CheckPOP(String username,String password){
		this.username=username;
		this.password=password;
	}
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		
		return new PasswordAuthentication(username,password);
	}
}
