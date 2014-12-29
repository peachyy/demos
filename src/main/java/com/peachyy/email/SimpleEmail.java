package com.peachyy.email;

import java.util.List;

public class SimpleEmail {
	/**
	 * 邮件主题
	 */
	private String subject;
	/**
	 * 邮件内容 正文
	 */
	private String content;
	/**
	 * 收件人
	 */
	private List<String> receiveUser;
	/**
	 * 抄送
	 */
	private List<String> ccUser;
	/**
	 * 秘密抄送
	 */
	private List<String> bccUser;
  	private List<String> filenames;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getReceiveUser() {
		return receiveUser;
	}
	public void setReceiveUser(List<String> receiveUser) {
		this.receiveUser = receiveUser;
	}
	public List<String> getFilenames() {
		return filenames;
	}
	public void setFilenames(List<String> filenames) {
		this.filenames = filenames;
	}
	public List<String> getCcUser() {
		return ccUser;
	}
	public void setCcUser(List<String> ccUser) {
		this.ccUser = ccUser;
	}
	public List<String> getBccUser() {
		return bccUser;
	}
	public void setBccUser(List<String> bccUser) {
		this.bccUser = bccUser;
	}	
}
