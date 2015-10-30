package com.whaley.db;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
/*
 * 获取“swapi.hongware.com/openApi/data/erp/searchOrder”此接口的url
 */
public class SearchOrderUrl {

	public static URL getUrl(String startTime,String endTime,int pageNo,String searchType){//startTime和endTime代表订单创建时间,pageNo表示页号
		String nick="微鲸电商";
		String name="whaley";
		String method="swapi.data.erp.order.search";
		String pageSize="50";
		String format="xml";
		Long timestamp=System.currentTimeMillis()/1000;
		try {
			String s =Base64Util.encode(nick.getBytes("UTF-8"))+Base64Util.encode(method.getBytes("UTF-8"))+
					Base64Util.encode(format.getBytes("UTF-8"))+Base64Util.encode(timestamp.toString().getBytes("UTF-8"));
			String param="nick="+URLEncoder.encode(nick,"utf-8")+"&name="+name+"&method="+method+"&searchType="
					+searchType+"&startTime="+URLEncoder.encode(startTime,"utf-8")+"&endTime="+URLEncoder.encode(endTime,"utf-8")+"&pageNo="+pageNo+"&pageSize="
					+pageSize+"&format="+format+"&timestamp="+timestamp.toString()+"&sign="+MD5Util.MD5(s);
			URL url=new URL("http://swapi.hongware.com/openApi/data/erp/searchOrder?"+param);
			return url;
		} catch (UnsupportedEncodingException e) {
			return null;
		} catch (MalformedURLException e) {
			return null;
		}
	}
}
