package com.example.untils.constant;

/**
 * 一般常量
 *
 */
public class CommonConstants {
	
	//-----------------------------------------------------------------
	
	/**
	 * 记录登录前页面
	 */
	public static final String SHOP_BEFORE_LOGIN_PAGE = "SHOP_BEFORE_LOGIN_PAGE";
	
	/**
	 * 记录登录前页面
	 */
	public static final String OS_BEFORE_LOGIN_PAGE = "OS_BEFORE_LOGIN_PAGE";
	
	/**
	 * 主站session key
	 */
	public static final String SHOP_SESSION_KEY = "SHOP_SYSTEM_SESSION_ID";
	
	/**
	 * 后台管理系统session key
	 */
	public static final String OS_SESSION_KEY = "OS_SYSTEM_SESSION_ID";
	
	/**
	 * 登录用户在 SESSION中保存的 key
	 */
	public static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";
	
	/**
	 * 登录用户在 SESSION中保存的 key
	 */
	public static final String LOGIN_IP = "LOGIN_IP";
	
	/**
	 * 登录用户权限页面在SESSION中保持的key
	 */
	public static final String LOGIN_PAGES = "LOGIN_PAGES";
	
	/**
	 * 登陆用户权限仓库map 在SESSION中保持的key
	 */
	public static final String LOGIN_WAREHOUSE_LIST = "LOGIN_WAREHOUSE_LIST";
	
	/**
	 * 登陆用户权限仓库id 在SESSION中保持的key
	 */
	public static final String LOGIN_WAREHOUSE_ID_LIST = "LOGIN_WAREHOUSE_ID_LIST";
	
	/**
	 * 主站session 过期时间为 半小时
	 */
	public static final Integer SHOP_SESSION_TIMEOUT = 60 * 60;
	
	/**
	 * 后台管理系统session 过期时间为 半小时
	 */
	public static final Integer OS_SESSION_TIMEOUT = 30 * 60;
	
	//------------------------------------------------------------
	/**
	 *cookie中保存的登陆的用户名key。
	 */
	public static final String LOGIN_USER_NAME="LOGIN_USER_NAME";
	
	//------------------------------------------------------------
	/** redirect session的值的Key */
	public static final String REDIRECT_DATA_KEY = "redirect_data_key";

	// 尾数处理
	/** 尾数处理：舍厘见分进角 */
	public static final String MANTISSA_PROCESS_1 = "1";
	/** 尾数处理：四舍五入到分 */
	public static final String MANTISSA_PROCESS_2 = "2";
	/** 尾数处理：四舍五入到角 */
	public static final String MANTISSA_PROCESS_3 = "3";
	/** 尾数处理：四舍五入到圆 */
	public static final String MANTISSA_PROCESS_4 = "4";
	/** 尾数处理：小数位数 */
	public static final String AMOUNT_DECIMAL = "2";
	
	/**
	 * 导出Excel的行数
	 */
	public static final int EXPORT_EXCEL_COUNT = 2000;
	
	/** 编码格式 GB2312**/
	public static final String ENCODE_GB2312 = "GB2312";
	/** 编码格式 UTF-8**/
	public static final String ENCODE_UTF8 = "UTF-8";
}
