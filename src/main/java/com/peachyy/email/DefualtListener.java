package com.peachyy.email;

public class DefualtListener implements SendEmailListeners {

	public boolean beforeSendEmail(EmailContext context) {
		System.out.println("beforeSendEmail:"+context.getEmail().getSubject()+"==状态"+context.getStatus());
		
		
		return false;
	}

	public void afterSendEmail(EmailContext context) {
		System.out.println("afterSendEmail:"+context.getEmail().getSubject()+"==状态"+context.getStatus());

	}

	public void errorEmail(EmailContext context) {
		System.out.println("errorEmail"+context.getEmail().getSubject()+"==状态"+
	context.getStatus()+"ERROR:"+context.getThrowable().getMessage());

	}

}
