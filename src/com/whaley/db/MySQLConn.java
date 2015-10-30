package com.whaley.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * 用于连接数据库
 */
public class MySQLConn {

	public static Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://172.16.10.143:3306/on_drp";
			String user="ondrp76";
			String password="adfEdfLOwWSj&kf#fS";
			Connection conn=DriverManager.getConnection(url, user, password);
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
