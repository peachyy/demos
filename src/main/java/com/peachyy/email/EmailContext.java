package com.peachyy.email;
/**
 * email上下文
 * @author peach
 *
 */
public class EmailContext {
	/**
	 * 发送成功状态
	 */
	public static final String RESULT_SUCCESS="sendSuccess";
	/**
	 * 发送失败状态
	 */
	public static final String RESULT_ERROR="sendError";
	/**
	 * 待发邮件 但已经与邮件服务器创建会话
	 */
	public static final String RESULT_WATING="sendEating";
	public SimpleEmail getEmail() {
		return email;
	}
	public void setEmail(SimpleEmail email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Throwable getThrowable() {
		return throwable;
	}
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
	/**
	 * 邮件
	 */
	private SimpleEmail email;
	/**
	 * 邮件状态
	 */
	private String status;
	/**
	 * 消息
	 */
	private String message;
	/**
	 * 异常代码
	 */
	private Throwable throwable;
}
