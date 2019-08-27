package com.example.bean.basic;


import java.io.Serializable;


/**
 * ajax json 返回值包装对象
 * @author Tommy
 *
 */
public class JsonResult implements Serializable {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -7073856910135669765L;

	/**
	 * 操作结果（成功或失败）
	 */
	boolean isOk = true;
	
	/**
	 * 消息类型
	 */
	char msgType;

	/**
	 * 操作提示信息
	 */
	String message;
	
	/**
	 * 备注
	 */
	String comment;
	
	/**
	 * 数据
	 */
	Object data;

	public JsonResult() {
		super();
	}

	public JsonResult(char msgType, String message) {
		super();
		this.isOk = false;
		this.msgType = msgType;
		this.message = message;
	}

	public boolean isOk() {
		return isOk;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	
	public char getMsgType() {
		return msgType;
	}

	public void setMsgType(char msgType) {
		this.msgType = msgType;
	}	
}
