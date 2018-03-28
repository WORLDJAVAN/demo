package com.testFather;

import java.util.Hashtable;

public class Hash_Table {

	public static void main(String[] args) {
		Hashtable<String, Object> ht = new Hashtable<String, Object>();
        ht.put("sad", new Sad_F());
        ht.put("Happy", new Happy_F());
       
        Father fa;
        fa = (Father) ht.get("sad");
        fa.actor();
        
        fa = (Father) ht.get("Happy");
        fa.actor();
	}

}
