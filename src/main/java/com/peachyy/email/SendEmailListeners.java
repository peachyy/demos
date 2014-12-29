package com.peachyy.email;

public interface SendEmailListeners {
	/**
	 * 发送邮件之前
	 * @param context
	 * @return
	 */
	public boolean beforeSendEmail(EmailContext context);
	/**
	 * 发送邮件之后
	 * @param context
	 */
	public void afterSendEmail(EmailContext context);
	/**
	 * 发送邮件==发生异常情况
	 * @param context
	 */
	public void errorEmail(EmailContext context);
}
