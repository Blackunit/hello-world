package com.whaley.db;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/*
 * 获取“swapi.hongware.com/openApi/data/erp/erpOrderDetail”此接口的url
 */

public class ErpOrderDetailUrl {

	public static URL getUrl(String orderNumber){
		String name="whaley";
		String isLog="1";
		String isInvoice="0";
		String nick="微鲸电商";
		String method="swapi.data.erp.order.detail";
		String format="xml";
		Long timestamp=System.currentTimeMillis()/1000;
		try {
			String s =Base64Util.encode(nick.getBytes("UTF-8"))+Base64Util.encode(method.getBytes("UTF-8"))+
					Base64Util.encode(format.getBytes("UTF-8"))+Base64Util.encode(timestamp.toString().getBytes("UTF-8"));
			String param="nick="+URLEncoder.encode(nick,"utf-8")+"&name="+name+"&method="+method+"&isLog="
					+isLog+"&isInvoice="+isInvoice+"&orderNumber="+orderNumber+"&format="+format+"&timestamp="
					+timestamp.toString()+"&sign="+MD5Util.MD5(s);
			URL url=new URL("http://swapi.hongware.com/openApi/data/erp/erpOrderDetail?"+param);
			return url;
		} catch (UnsupportedEncodingException e) {
			return null;
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
}
