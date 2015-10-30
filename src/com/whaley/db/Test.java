package com.whaley.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {

	public static void main(String[] args) {

		URL url=SearchOrderUrl.getUrl("2015-09-01 00:00:00","2015-09-30 23:59:59",1,"crt");
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			InputStream is=connection.getInputStream();
			InputStreamReader isr=new InputStreamReader(is);
			BufferedReader br=new BufferedReader(isr);
			String line;
			while((line=br.readLine())!=null){
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
