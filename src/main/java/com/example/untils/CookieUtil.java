package com.example.untils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * cookie工具类
 * @author Tommy
 *
 */
public class CookieUtil {
	
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static Cookie[] getAllCookies(HttpServletRequest request){
		return request.getCookies();
	}
	
	/**
	 * 
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String cookieName){
		Cookie[] cookies = getAllCookies(request);
		if(cookies == null || cookies.length == 0){
			return null;
		}
		Cookie resultCookie = null;
		for(Cookie cookie : cookies){
			
	  	if(cookieName.equals(cookie.getName())){
				resultCookie = cookie;
				try {
					//logger.info("---decode vlaue： "+java.net.URLDecoder.decode(cookie.getValue(), "UTF-8") );
					resultCookie.setValue(java.net.URLDecoder.decode(cookie.getValue(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				break;
			}
		}
		return resultCookie;
	}
	
}
