
package com.example.bean.basic;
import java.util.List;

public class PageQueryResult<T>{

	private static final long serialVersionUID = -1140504575505840707L;

	private boolean isException = false;
	
	/**
	 * 总数
	 */
	private int total;
	
	/**
	 * 数据
	 */
	private List<T> rows;
	
	/**
	 * 底部合计相关数据
	 */
	private List<T> footer;
	
	public PageQueryResult() {
		super();
	}
	
	public PageQueryResult(int total, List<T> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	
	public PageQueryResult(int total, List<T> rows, List<T> footer) {
		super();
		this.total = total;
		this.rows = rows;
		this.footer = footer;
	}
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public List<T> getFooter() {
		return footer;
	}

	public void setFooter(List<T> footer) {
		this.footer = footer;
	}

	public boolean isException() {
		return isException;
	}

	public void setException(boolean isException) {
		this.isException = isException;
	}
	
	
}
