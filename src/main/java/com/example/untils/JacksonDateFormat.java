package com.example.untils;

import org.codehaus.jackson.map.util.StdDateFormat;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * jackson日期格式，追加支持的格式
 * 
 * @author 黄志豪
 */
public class JacksonDateFormat extends StdDateFormat {
	private static final long serialVersionUID = 1L;

    protected final static String DATE_FORMAT_STR_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    protected final static String DATE_FORMAT_STR_DATE = "yyyy-MM-dd";

    protected final static DateFormat DATE_FORMAT_DATE_TIME;

    static {
        /* Another important thing: let's force use of GMT for
         * baseline DataFormat objects
         */
    	DATE_FORMAT_DATE_TIME = new SimpleDateFormat(DATE_FORMAT_STR_DATE_TIME);
    }
    
    protected transient DateFormat _formatDateTime;
    
	public JacksonDateFormat() {
	}

	public JacksonDateFormat clone() {
		return new JacksonDateFormat();
	}

	
    @Override
    public Date parse(String dateStr, ParsePosition pos)
    {
    	if(looksLikeYYYYMMDDHHMMSS(dateStr)){
    		_formatDateTime = (DateFormat) DATE_FORMAT_DATE_TIME.clone();
    		return _formatDateTime.parse(dateStr, pos);
    	}
    	if (dateStr.length() == DATE_FORMAT_STR_DATE.length()) {
    		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STR_DATE);
    		return sdf.parse(dateStr, pos);
    	}
    	return super.parse(dateStr, pos);
    }
    
    protected boolean looksLikeYYYYMMDDHHMMSS(String dateStr)
    {
        if (dateStr.length() == DATE_FORMAT_STR_DATE_TIME.length() && dateStr.charAt(10) == ' ') {
            return true;
        }
        return false;
    }
}