package com.example.untils;



public class SystemConstants {
	
	public static String SYSTEM_CODE="SYSTEM_CODE";
	
	public static String SYS_CONFIG_DATA="SYS_CONFIG_DATA";
	
	public static String MESSAGE_DATA="message";
	
	public static String MESSAGE_XML_PATH="WEB-INF/classes/message/messages.xml";
	
	public static String MESSAGE_JS_PATH="static/js/messages.js";
	
	public static String TEMPLATE_DIR_PATH="WEB-INF/template";

	/** batch用户Id */
	public static int BATCH_USER_ID = 1;
	
	/** 超级管理员*/
	public static String ADMIN="admin";
	

	/** 商品标签打印方式：商品标签页面 */
	public static final String PRODUCTBARCODEFLAG_BARCODEPRODUCT = "barcodeproduct";
	/** 商品标签打印方式：上架配仓页面 */
	public static final String PRODUCTBARCODEFLAG_IMPAUTOMATCHLIST = "impAutoMatchList";
	

	/** 储位类型默认：存储位*/
	public static byte LOCATION_TYPE=(byte)0;
	
	/**
	 * 存放件数（标准货位）默认值
	 */
	public static Integer QTY_PER_LOCATION=0;
	
	
	/** 页面级别权限*/
	public static String PAGE_GRADE = "page";
	/** 画面级别权限*/
	public static String FUNCTION_GRADE = "function";
	
	
	/** 密码或用户名错误*/
	public static Integer LOGIN_FLAG_ERROE = 0;
	/** 密码即将过期*/
	public static Integer LOGIN_FLAG_SOONPERIOD = 1;
	/** 密码过期*/
	public static Integer LOGIN_FLAG_PERIOD = 2;
	/** 未分配角色权限*/
	public static Integer LOGIN_FLAG_NO_ROLE = 3;
	
	/** 默认省的父级，抽取所有省*/
	public static Integer SYSTEM_REGION_PROVINCE = 0;
	
	/** 拆箱费*/
	public static Integer UNLOADING_FEE_ID = 1;
	/** 入库费*/
	public static Integer INCOMING_FEE_ID = 2;
	/** 保管费*/
	public static Integer STORAGE_FEE_ID = 3;
	/** 出库费*/
	public static Integer OUTGOING_FEE_ID = 4;
	/** 装箱费*/
	public static Integer LOADING_FEE_ID = 5;
	/** 运输费*/
	public static Integer DELIVERY_FEE_ID = 6;
	
	/** 按出库分配方式拆单*/
	public static String ORDER_SEPARATE_ITEM_RESERVE_TYPE = "reserve_type";
	/** 按出库分配方式拆单*/
	public static String ORDER_SEPARATE_ITEM_SUPP_TYPE = "supp_type";
	/** 按交货形式拆单*/
	public static String ORDER_SEPARATE_ITEM_DELIVERY_TYPE = "delivery_type";
	/** 按pick区分拆单*/
	public static String ORDER_SEPARATE_ITEM_PICK_TYPE = "pick_type";
	/** 按捡货类型拆单*/
	public static String ORDER_SEPARATE_ITEM_PICK_CLASS = "pick_class";
	/** 按波次规划拆单*/
	public static String ORDER_SEPARATE_ITEM_WAVE = "wave";
	
	/** 按出库分配方式拆单*/
	public static String RETURN_SEPARATE_ITEM_SUPP_TYPE = "supp_type";
	/** 按交货形式拆单*/
	public static String RETURN_SEPARATE_ITEM_DELIVERY_TYPE = "delivery_type";
	/** 按捡货类型拆单*/
	public static String RETURN_SEPARATE_ITEM_PICK_CLASS = "pick_class";
	
	/** 欠品出库类型:0 全不出库*/
	public static final String STOCK_OUT_TYPE_NO ="0";
	/** 欠品出库类型:1 库存有的部分出库*/
	public static final String STOCK_OUT_TYPE_PART ="1";
	
	/** 系统admin用户id*/
	public static final Integer ADMIN_USER_ID = 1;
	
	public static final String DOWNLOAD_DIR = "DOWNLOAD_DIR";

	public static final String DAS_EXPORT_DIR = "DAS_EXPORT_DIR";

	public static final String LAWSON_EXPORT_DIR = "LAWSON_EXPORT_DIR";
		
	/**zip包类型：主档*/
	public static final byte ZIP_TYPE_MASTER = (byte)1;
	/**zip包类型：出入库*/
	public static final byte ZIP_TYPE_INOUT = (byte)2;
	
	/**配仓时是否分配到拆零分拣位:否*/
	public static final String PUTAWAY_IN_DISMANTLE_SORTING_NO="0";
	/**配仓时是否分配到拆零分拣位:是*/
	public static final String PUTAWAY_IN_DISMANTLE_SORTING_YES="1";
	
	/** 门店退货存放的封仓储位*/
	public static final String RETURN_LOCK_LOCATION = "return_lock_location";
	
	/** 罗森物流中心编号*/
	public static final String LAWSON_CENTER_NO = "lawson_center_no";
	
	/** 罗森物流中心编号(门店退欠货数据)*/
	public static final String LAWSON_SHOP_RETURN_CENTER_NO = "lawson_shop_return_center_no";

	/**中心店编号（接口）*/
	public static final String CENTER_CODE = "center_code";
	
	/** 出库件数表导出商品编号范围*/
	public static final String HANDOVER_LIST_ITEM_NO = "handover_list_item_no";

	/** 储位编号中排的位数*/
	public static final String LOCATIONNO_ROW_LENGTH = "locationNo_row_length";

	/** 账票打印要显示参数名*/
	public static final String PRINT_PARAM_SHOW = "printParam";

	/**盘点方式*/
	public  static final String STOCKTAKE_MODE = "stocktake_mode";

	/**调用mis接口url*/
	public static final String URL = "http://lt.tes-sys.com:6037/WebApi/RequestMethod2";
	/**获取中转机地址*/
	public static final String WMS_TO_MIS = "wms_to_mis";
}
