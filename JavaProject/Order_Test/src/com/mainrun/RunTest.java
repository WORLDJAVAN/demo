package com.mainrun;

import java.util.HashMap;
import java.util.Map;

public class RunTest {

	public static void main(String[] args) {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("partnerid", "19988510010"); //�ͻ��ʺ�
		params.put("password", "wWTSfauVJ6PAcBvY83pGs7NqIeiM5x"); //��������	
		params.put("version", "1.0");
		params.put("request", "data");	
		
		StringBuffer requestBuffer = new StringBuffer();	

		requestBuffer.append("<orders>");
		requestBuffer.append("<order>");
		requestBuffer.append("<order_serial_no>12226127</order_serial_no>");
		requestBuffer.append("<khddh>BCA1000012315</khddh>");
		requestBuffer.append("<nbckh>2001123</nbckh>");
		
		requestBuffer.append("<sender>");
		requestBuffer.append("<name>��С��</name><company>����</company><city>����ʡ�������У�������</city><address>����·999��</address><postcode>221435</postcode><phone>021-8592652</phone><mobile>13761960078</mobile><branch>410000</branch>");
		requestBuffer.append("</sender>");
		
		requestBuffer.append("<receiver>");
		requestBuffer.append("<name>½����</name><company>ǧǧ</company><city>�Ϻ��У��Ϻ��У�������</city><address>�Ϻ��У��Ϻ��У�������ӯ�۶�·6633��</address><postcode>201700</postcode><phone>020-57720341</phone><mobile>13761960075</mobile><branch>315100</branch>");
		requestBuffer.append("</receiver>");
		
		requestBuffer.append("<weight>11</weight><size></size><value>20</value><freight></freight><premium></premium><other_charges></other_charges>");
		
		requestBuffer.append("<collection_currency></collection_currency><collection_value></collection_value><special>��װ</special>");
		
		requestBuffer.append("<items><item><name>����</name><number>1</number><remark>��ɫ</remark></item></items><remark></remark>");
		requestBuffer.append("<cus_area1>�����ţ�123 ���κţ�456212</cus_area1><cus_area2></cus_area2>");		
		
		requestBuffer.append("</order>");
		requestBuffer.append("</orders>");
	
		params.put("xmldata", requestBuffer.toString());
	
		/* re_data Ϊ��õ��������ֵ*/
		String re_data =  EnDecoder.encode(params);
		
		System.out.println(re_data);
	}

}
