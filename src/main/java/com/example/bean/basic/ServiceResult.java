/*
 *  @(#)ServiceResult.java	1.0 2016/04/01
 *
 * Copyright (c) 2016, 上海坦思计算机系统有限公司 版权所有.
 * TES PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.example.bean.basic;

import java.io.Serializable;

/**
 * service返回值包装对象
 * @param <T>
 * 
 */
public class ServiceResult<T> extends SimpleServiceResult implements Serializable {
	private static final long serialVersionUID = -6652076509848001811L;

	/**
	 * 数据
	 */
	private T data;
	
	public ServiceResult() {
		super(true);
	}
	
	public ServiceResult(T data) {
		super(true);
		this.data = data;
	}
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
