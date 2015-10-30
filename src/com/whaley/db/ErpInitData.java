package com.whaley.db;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ErpInitData {

	public static void  writeDB(){
		int pageNo=875;//起始页号


		String orderNumber;//订单编号
		String expressNumber;//快递单号
		String oldOrderNumber;//原订单编号
		String shopName;//店铺名称
		String logisticName;//物流公司code
		String orderStatus;//订单状态
		String wareHouse;//仓库
		String orderType;//订单类型
		String orderWay;//来源方式
		String payType;//付款类型
		String payTime;//付款时间
		String orderDate;//下单时间
		String created;//订单完成时间
		String downloadTime;//下载时间
		String memberName;//会员昵称
		String telephone;//买家电话
		String consignee;//收货人
		String address;//收货人地址
		String mobilePhone;//收货人手机
		String memo;//买家备注
		String sellerMemo;//卖家备注
		String buyerMessage;//买家留言
		String erpMemo;//Erp内部备注
		String orderNote;//订单便签
		String totalAmount;//商品总金额
		String initialActualAmount;//实付金额
		String actualAmount;//应付金额
		String salesOrderAgioMoney;//让利金额
		String discountFee;//商品总让利
		String totalDepositRate;//总折扣率
		String deliveryCost;//快递费用
		String logisticsCost;//预估物流成本
		String promotionInfo;//优惠信息
		String isApplyForRefund;//是否申请退款
		String isInterrupted;//是否拦截
		String processExtraStatus;//流程额外状态
		String isQuickSalesOrder;//是否加急
		String totalWeight;//总重量
		String actualWeight;//订单净重


		URL url;
		String sql_insert;
		HttpURLConnection connection;
		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document document;
		NodeList erpOrders;
		Connection conn;
		Statement statement;
		
		
		String startTime="2015-10-01 00:00:00";
		String endTime="2015-10-31 23:59:59";

	/*	SimpleDateFormat sdf_datetime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf_time=new SimpleDateFormat("HH:mm:ss");

		String datetime="2015-10-26 00:00:00";

		String[] time=new String[24*12];
		for(int i=0;i<24*12;i++){
			try {
				time[i]=sdf_time.format(sdf_datetime.parse(datetime));
				datetime=sdf_datetime.format(sdf_datetime.parse(datetime).getTime()+5*60*1000);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		List<String> timeList=Arrays.asList(time);*/

		while(true){

			/*long currentTime=System.currentTimeMillis();
			String startTime=sdf_datetime.format(currentTime-2*48*60*60*1000);
			String endTime=sdf_datetime.format(currentTime);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}*/
			System.out.println(startTime);
			System.out.println(endTime);
//			if(timeList.contains(sdf_time.format(currentTime))){
//				while(true){
					url=SearchOrderUrl.getUrl(startTime,endTime,pageNo,"crt");
								System.out.println(pageNo);

					pageNo++; //增加页号

					try {
						connection=(HttpURLConnection) url.openConnection();
						dbf=DocumentBuilderFactory.newInstance();
						db=dbf.newDocumentBuilder();
						document = db.parse(connection.getInputStream()); //从“http://swapi.hongware.com/openApi/data/erp/searchOrder”接口获取的数据
						if(document.getElementsByTagName("isSuccess").item(0).getFirstChild().getNodeValue().equals("true")){

							erpOrders=document.getElementsByTagName("erpOrder");
//							System.out.println(erpOrders.getLength());
							if(erpOrders.getLength()<=0){ //如果没有订单数据则退出循环
								System.out.println("没有订单数据了");
								pageNo=1;
								break;
							}
							else{

								/**
								 * 连接数据库
								 */
								conn=MySQLConn.getConnection();
								if(conn==null){
									System.out.println("连接数据库出错！");
									System.exit(-1);
								}
								statement=conn.createStatement();

								for(int i=0;i<erpOrders.getLength();i++){
									orderNumber=document.getElementsByTagName("orderNumber").item(i).getFirstChild().getNodeValue();
									if(document.getElementsByTagName("expressNumber").item(i).getLastChild()==null){
										expressNumber=null;
									}
									else {
										expressNumber=document.getElementsByTagName("expressNumber").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("oldOrderNumber").item(i).getLastChild()==null){
										oldOrderNumber=null;
									}
									else {
										oldOrderNumber=document.getElementsByTagName("oldOrderNumber").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("shopName").item(i).getLastChild()==null){
										shopName=null;
									}
									else {
										shopName=document.getElementsByTagName("shopName").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("logisticName").item(i).getLastChild()==null){
										logisticName=null;
									}
									else {
										logisticName=document.getElementsByTagName("logisticName").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("orderStatus").item(i).getLastChild()==null){
										orderStatus=null;
									}
									else {
										orderStatus=document.getElementsByTagName("orderStatus").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("wareHouse").item(i).getLastChild()==null){
										wareHouse=null;
									}
									else {
										wareHouse=document.getElementsByTagName("wareHouse").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("orderType").item(i).getLastChild()==null){
										orderType=null;
									}
									else {
										orderType=document.getElementsByTagName("orderType").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("orderWay").item(i).getLastChild()==null){
										orderWay=null;
									}
									else {
										orderWay=document.getElementsByTagName("orderWay").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("payType").item(i).getLastChild()==null){
										payType=null;
									}
									else {
										payType=document.getElementsByTagName("payType").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("payTime").item(i).getLastChild()==null){
										payTime=null;
									}
									else {
										payTime=document.getElementsByTagName("payTime").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("orderDate").item(i).getLastChild()==null){
										orderDate=null;
									}
									else {
										orderDate=document.getElementsByTagName("orderDate").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("created").item(i).getLastChild()==null){
										created=null;
									}
									else {
										created=document.getElementsByTagName("created").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("dowloadTime").item(i).getLastChild()==null){
										downloadTime=null;
									}
									else {
										downloadTime=document.getElementsByTagName("dowloadTime").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("memberName").item(i).getLastChild()==null){
										memberName=null;
									}
									else {
										memberName=document.getElementsByTagName("memberName").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("telephone").item(i).getLastChild()==null){
										telephone=null;
									}
									else {
										telephone=document.getElementsByTagName("telephone").item(i).getFirstChild().getNodeValue();
									}
									if(document.getElementsByTagName("consignee").item(i).getLastChild()==null){
										consignee=null;
									}
									else {
										if((consignee=document.getElementsByTagName("consignee").item(i).getFirstChild().getNodeValue()).contains("'"))
											consignee=consignee.replace('\'', '‘');
									}

									if(document.getElementsByTagName("address").item(i).getLastChild()==null){
										address=null;
									}
									else {
										if((address=document.getElementsByTagName("address").item(i).getFirstChild().getNodeValue()).contains("'"))
											address=address.replace('\'','‘');
									}

									if(document.getElementsByTagName("mobilePhone").item(i).getLastChild()==null){
										mobilePhone=null;
									}
									else {
										if((mobilePhone=document.getElementsByTagName("mobilePhone").item(i).getFirstChild().getNodeValue()).contains("'"))
											mobilePhone=mobilePhone.replace('\'','‘');
									}

									if(document.getElementsByTagName("memo").item(i).getLastChild()==null){
										memo=null;
									}
									else {
										memo=document.getElementsByTagName("memo").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("sellerMemo").item(i).getLastChild()==null){
										sellerMemo=null;
									}
									else {
										sellerMemo=document.getElementsByTagName("sellerMemo").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("buyerMessage").item(i).getLastChild()==null){
										buyerMessage=null;
									}
									else {
										buyerMessage=document.getElementsByTagName("buyerMessage").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("erpMemo").item(i).getLastChild()==null){
										erpMemo=null;
									}
									else{
										erpMemo=document.getElementsByTagName("erpMemo").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("orderNote").item(i).getLastChild()==null){
										orderNote=null;
									}
									else {
										orderNote=document.getElementsByTagName("orderNote").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("totalAmount").item(i).getLastChild()==null){
										totalAmount=null;
									}
									else {
										totalAmount=document.getElementsByTagName("totalAmount").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("initialActualAmount").item(i).getLastChild()==null){
										initialActualAmount=null;
									}
									else {
										initialActualAmount=document.getElementsByTagName("initialActualAmount").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("actualAmount").item(i).getLastChild()==null){
										actualAmount=null;
									}
									else {
										actualAmount=document.getElementsByTagName("actualAmount").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("salesOrderAgioMoney").item(i).getLastChild()==null){
										salesOrderAgioMoney=null;
									}
									else {
										salesOrderAgioMoney=document.getElementsByTagName("salesOrderAgioMoney").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("discountFee").item(i).getLastChild()==null){
										discountFee=null;
									}
									else {
										discountFee=document.getElementsByTagName("discountFee").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("totalDepositRate").item(i).getLastChild()==null){
										totalDepositRate=null;
									}
									else {
										totalDepositRate=document.getElementsByTagName("totalDepositRate").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("deliveryCost").item(i).getLastChild()==null){
										deliveryCost=null;
									}
									else {
										deliveryCost=document.getElementsByTagName("deliveryCost").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("logisticsCost").item(i).getLastChild()==null){
										logisticsCost=null;
									}
									else {
										logisticsCost=document.getElementsByTagName("logisticsCost").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("promotionInfo").item(i).getLastChild()==null){
										promotionInfo=null;
									}
									else {
										promotionInfo=document.getElementsByTagName("promotionInfo").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("isApplyForRefund").item(i).getLastChild()==null){
										isApplyForRefund=null;
									}
									else {
										isApplyForRefund=document.getElementsByTagName("isApplyForRefund").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("isInterupted").item(i).getLastChild()==null){
										isInterrupted=null;
									}
									else {
										isInterrupted=document.getElementsByTagName("isInterupted").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("processExtraStatus").item(i).getLastChild()==null){
										processExtraStatus=null;
									}
									else {
										processExtraStatus=document.getElementsByTagName("processExtraStatus").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("isQuickSalesOrder").item(i).getLastChild()==null){
										isQuickSalesOrder=null;
									}
									else {
										isQuickSalesOrder=document.getElementsByTagName("isQuickSalesOrder").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("totalWeight").item(i).getLastChild()==null){
										totalWeight=null;
									}
									else {
										totalWeight=document.getElementsByTagName("totalWeight").item(i).getFirstChild().getNodeValue();
									}

									if(document.getElementsByTagName("actualWeight").item(i).getLastChild()==null){
										actualWeight=null;
									}
									else {
										actualWeight=document.getElementsByTagName("actualWeight").item(i).getFirstChild().getNodeValue();
									}



									sql_insert="insert ignore into ordersinfo values('"+orderNumber+"','"+expressNumber+"','"+oldOrderNumber+"','"
											+shopName+"','"+logisticName+"','"+orderStatus+"','"+wareHouse+"','"+orderType+"','"+orderWay+"','"
											+payType+"','"+payTime+"','"+orderDate+"','"+created+"','"+downloadTime+"','"+memberName+"','"
											+telephone+"','"+consignee+"','"+address+"','"+mobilePhone+"','"+memo+"','"+sellerMemo+"','"
											+buyerMessage+"','"+erpMemo+"','"+orderNote+"','"+totalAmount+"','"
											+initialActualAmount+"','"+actualAmount+"','"+salesOrderAgioMoney+"','"+discountFee+"','"
											+totalDepositRate+"','"+deliveryCost+"','"+logisticsCost+"','"+promotionInfo+"','"
											+isApplyForRefund+"','"+isInterrupted+"','"+processExtraStatus+"','"+isQuickSalesOrder
											+"','"+totalWeight+"','"+actualWeight+"')";

									System.out.println(sql_insert);
									statement.executeUpdate(sql_insert);
									ErpInitProductData.writeDB(orderNumber);
								} //for end

								statement.close();
								conn.close();
							} //else end
						} //if end
						else{
						}

					} catch (IOException e) {
						e.printStackTrace();
						pageNo--;
					} catch (ParserConfigurationException e) {
						e.printStackTrace();
					} catch (SAXException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
						pageNo--;
					} 
				}
//			}//if timelist end
//		}//while end

	}
}
