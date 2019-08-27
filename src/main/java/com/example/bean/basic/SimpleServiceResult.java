/*
 *  @(#)SimpleServiceResult.java	1.0 2016/04/01
 *
 * Copyright (c) 2016, 上海坦思计算机系统有限公司 版权所有.
 * TES PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.example.bean.basic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SimpleServiceResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final char MSG_TYPE_ERROR 	= 'E';
	public static final char MSG_TYPE_INFO 		= 'I';
	public static final char MSG_TYPE_WARNING	= 'W';
	
	/**
	 * 没有消息的正常结果
	 */
	public static final SimpleServiceResult OK = new SimpleServiceResult(true);
	
	/**
	 * 结果
	 */
	private boolean isOk = true;

		/**
	 * 消息
	 */
	protected List<Message> msgs;
	
	/**
	 * 消息类型
	 */
	private char msgType = MSG_TYPE_INFO;
	
	/**
	 * 备注
	 */
	private String comment;


	public class Message implements Serializable {
		private static final long serialVersionUID = 875220265072895432L;

		private String msgId;
		
		private String[] params;
		
		public Message(String msgId, String[] params) {
			super();
			this.msgId = msgId;
			this.params = params;
		}
		
		public String getMsgId() {
			return msgId;
		}
		public void setMsgId(String msgId) {
			this.msgId = msgId;
		}
		public String[] getParams() {
			return params;
		}
		public void setParams(String[] params) {
			this.params = params;
		}
	}

	protected SimpleServiceResult(boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * 有消息的结果
	 * @param msgId 暂定如果已E开头为失败消息
	 * @param params 消息的参数
	 */
	public SimpleServiceResult(String msgId, String... params) {
		this.addMsg(msgId, params);
	}

	/**
	 * 追加消息
	 * @param msgId
	 * @param objects
	 */
	public SimpleServiceResult addMsg(String msgId, String... params) {
		Message newMsg = new Message(msgId, params);
		if(msgs == null){
			msgs = new ArrayList<Message>();
		}
		char flag = msgId.charAt(0);
		if(flag == 'E'){
			this.setMsgType(MSG_TYPE_ERROR);
			this.isOk = false;
		}else if(flag == 'W'){
			if(this.getMsgType() != MSG_TYPE_ERROR){
				this.setMsgType(MSG_TYPE_WARNING);
			}
			this.isOk = false;
		}
		msgs.add(newMsg);
		return this;
	}	
	
	public char getMsgType() {
		return msgType;
	}

	public void setMsgType(char msgType) {
		this.msgType = msgType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}
	
	public List<Message> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<Message> msgs) {
		this.msgs = msgs;
	}
}
