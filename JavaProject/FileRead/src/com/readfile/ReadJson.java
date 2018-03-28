package com.readfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ReadJson {
	
	public static void main(String[] args) {
		String path = "F:/��Ա�����ָ�ʽ.json";
		String sets=ReadFile(path);//���json�ļ�������    
		List<IntegralCard> list = new ArrayList<IntegralCard>();
        try {
        	//JSONObject jsonObject=JSONObject.fromObject(object).fromString(sets);  //��ʽ����json���� 
        	JSONObject jsonObject=JSONObject.fromObject(sets);
            JSONArray jsonArray = jsonObject.getJSONArray("integralcrad");  //����json����
        //    for (int i = 0; i < jsonArray.length(); i++) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                IntegralCard integralCard  = new IntegralCard();
                integralCard.setAccountId(jsonObject2.getString("accountId"));
                integralCard.setMoneytype(jsonObject2.getString("moneytype"));
                integralCard.setCostmoney(jsonObject2.getString("costmoney"));
                integralCard.setReqtype(jsonObject2.getString("reqtype"));
                integralCard.setSuit(jsonObject2.getString("suit"));
                integralCard.setTradeid(jsonObject2.getString("tradeid"));
                list.add(integralCard);
              }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //ȡ������
        System.out.println("-------------ȡ������-------------");
        for(int j = 0; j < list.size(); j++){
        	IntegralCard  card = list.get(j);
        	System.out.println("accountId="+card.getAccountId()+"---"+j);
        	System.out.println("moneytype="+card.getMoneytype()+"---"+j);
        }
	}
	
	//���ļ��������ַ���
	public static String ReadFile(String path){
	    File file = new File(path);
	    BufferedReader reader = null;
	    String laststr = "";
	    try {
		     reader = new BufferedReader(new FileReader(file));
		     String tempString = null;
		     @SuppressWarnings("unused")
			int line = 1;
		     //һ�ζ���һ�У�ֱ������nullΪ�ļ�����
		     while ((tempString = reader.readLine()) != null) {
			      laststr = laststr+tempString;
			      line--;
		     }
		     reader.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } finally {
		     if (reader != null) {
			      try {
			    	  reader.close();
			      } catch (IOException e1) {
			      }
		     }
	    }
	    return laststr;
	}
}
