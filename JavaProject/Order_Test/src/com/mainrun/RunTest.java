package com.mainrun;

import java.util.HashMap;
import java.util.Map;

public class RunTest {

	public static void main(String[] args) {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("partnerid", "19988510010"); //客户帐号
		params.put("password", "wWTSfauVJ6PAcBvY83pGs7NqIeiM5x"); //联调密码	
		params.put("version", "1.0");
		params.put("request", "data");	
		
		StringBuffer requestBuffer = new StringBuffer();	

		requestBuffer.append("<orders>");
		requestBuffer.append("<order>");
		requestBuffer.append("<order_serial_no>12226127</order_serial_no>");
		requestBuffer.append("<khddh>BCA1000012315</khddh>");
		requestBuffer.append("<nbckh>2001123</nbckh>");
		
		requestBuffer.append("<sender>");
		requestBuffer.append("<name>王小虎</name><company>凯利</company><city>江苏省，徐州市，新沂市</city><address>湖东路999号</address><postcode>221435</postcode><phone>021-8592652</phone><mobile>13761960078</mobile><branch>410000</branch>");
		requestBuffer.append("</sender>");
		
		requestBuffer.append("<receiver>");
		requestBuffer.append("<name>陆大有</name><company>千千</company><city>上海市，上海市，青浦区</city><address>上海市，上海市，青浦区盈港东路6633号</address><postcode>201700</postcode><phone>020-57720341</phone><mobile>13761960075</mobile><branch>315100</branch>");
		requestBuffer.append("</receiver>");
		
		requestBuffer.append("<weight>11</weight><size></size><value>20</value><freight></freight><premium></premium><other_charges></other_charges>");
		
		requestBuffer.append("<collection_currency></collection_currency><collection_value></collection_value><special>服装</special>");
		
		requestBuffer.append("<items><item><name>外套</name><number>1</number><remark>黑色</remark></item></items><remark></remark>");
		requestBuffer.append("<cus_area1>订单号：123 批次号：456212</cus_area1><cus_area2></cus_area2>");		
		
		requestBuffer.append("</order>");
		requestBuffer.append("</orders>");
	
		params.put("xmldata", requestBuffer.toString());
	
		/* re_data 为组好的请求参数值*/
		String re_data =  EnDecoder.encode(params);
		
		System.out.println(re_data);
	}

}
