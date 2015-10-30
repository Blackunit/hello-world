package com.whaley.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ErpInitProductData {

	public static void writeDB(String orderNumber) throws IOException, ParserConfigurationException, SAXException, SQLException{

		/*
		 * 商品详情
		 */
		String productNumber; //商品编码
		String productName;   //商品名称
		String skuNumber;     //规格编码
		String skuName;       //规格名称
		String orderCount;    //商品数量
		String giftCount;     //赠送数量
		String amount;		  //商品折扣总金额
		String price;		  //商品原价
		String discountRate;  //折扣率
		String agioPrice;	  //折后单价
		String refundStatus;  //申请退款状态
		String isScaned;	  //是否配货扫描过
		String productMemo;	      //备注
		

		String sql_insert;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document orderDetailDocument=db.parse(ErpOrderDetailUrl.getUrl(orderNumber).openConnection().getInputStream());
		Connection conn=MySQLConn.getConnection();
		if(conn==null){
			System.out.println("连接数据库出错！");
			System.exit(-1);
		}
		Statement statement=conn.createStatement();


		System.out.println(orderNumber);
		
		for(int i=0;i<orderDetailDocument.getElementsByTagName("productNumber").getLength() && orderDetailDocument.getElementsByTagName("productNumber").getLength()>0;i++){

			/*
			 * 不考虑没有产品信息的订单
			 */
			
			try{
				if(orderDetailDocument.getElementsByTagName("productNumber").item(i).getLastChild()==null){
					productNumber=null;
				}
				else {
					productNumber=orderDetailDocument.getElementsByTagName("productNumber").item(i).getFirstChild().getNodeValue();
				}

				if(orderDetailDocument.getElementsByTagName("productName").item(i).getLastChild()==null){
					productName=null;
				}
				else {
					productName=orderDetailDocument.getElementsByTagName("productName").item(i).getFirstChild().getNodeValue();
				}

				if(orderDetailDocument.getElementsByTagName("skuNumber").item(i).getLastChild()==null){
					skuNumber=null;
				}
				else {
					skuNumber=orderDetailDocument.getElementsByTagName("skuNumber").item(i).getFirstChild().getNodeValue();
				}

				if(orderDetailDocument.getElementsByTagName("skuName").item(i).getLastChild()==null){
					skuName=null;
				}
				else {
					skuName=orderDetailDocument.getElementsByTagName("skuName").item(i).getFirstChild().getNodeValue();
				}

				if(orderDetailDocument.getElementsByTagName("orderCount").item(i).getLastChild()==null){
					orderCount="0";
				}
				else {
					orderCount=orderDetailDocument.getElementsByTagName("orderCount").item(i).getFirstChild().getNodeValue();
				}

				if(orderDetailDocument.getElementsByTagName("giftCount").item(i).getLastChild()==null){
					giftCount=null;
				}
				else {
					giftCount=orderDetailDocument.getElementsByTagName("giftCount").item(i).getFirstChild().getNodeValue();
				}

				if(orderDetailDocument.getElementsByTagName("amount").item(i).getLastChild()==null){
					amount=null;
				}
				else {
					amount=orderDetailDocument.getElementsByTagName("amount").item(i).getFirstChild().getNodeValue();
				}

				if(orderDetailDocument.getElementsByTagName("price").item(i).getLastChild()==null){
					price=null;
				}
				else {
					price=orderDetailDocument.getElementsByTagName("price").item(i).getFirstChild().getNodeValue();
				}

				if(orderDetailDocument.getElementsByTagName("discountRate").item(i).getLastChild()==null){
					discountRate=null;
				}
				else {
					discountRate=orderDetailDocument.getElementsByTagName("discountRate").item(i).getFirstChild().getNodeValue();
				}

				if(orderDetailDocument.getElementsByTagName("agioPrice").item(i).getLastChild()==null){
					agioPrice=null;
				}
				else {
					agioPrice=orderDetailDocument.getElementsByTagName("agioPrice").item(i).getFirstChild().getNodeValue();
				}

				if(orderDetailDocument.getElementsByTagName("refundStatus").item(i).getLastChild()==null){
					refundStatus=null;
				}
				else {
					refundStatus=orderDetailDocument.getElementsByTagName("refundStatus").item(i).getFirstChild().getNodeValue();
				}

				if(orderDetailDocument.getElementsByTagName("isScaned").item(i).getLastChild()==null){
					isScaned=null;
				}
				else {
					isScaned=orderDetailDocument.getElementsByTagName("isScaned").item(i).getFirstChild().getNodeValue();
				}

				if(orderDetailDocument.getElementsByTagName("memo").item(i).getLastChild()==null){
					productMemo=null;
				}
				else {
					productMemo=orderDetailDocument.getElementsByTagName("memo").item(i).getFirstChild().getNodeValue();
				}

				sql_insert = "insert ignore into orderinfo values('"+orderNumber+"','"+productNumber+"','"+productName+"','"
						+skuNumber+"','"+skuName+"','"+orderCount+"','"+giftCount+"','"+amount+"','"
						+price+"','"+discountRate+"','"+agioPrice+"','"+refundStatus+"','"+isScaned+"','"
						+productMemo+"')";
				statement.executeUpdate(sql_insert);
			}catch(NullPointerException e){
				continue;
			}
		} //for end

		statement.close();
		conn.close();

	}

}
