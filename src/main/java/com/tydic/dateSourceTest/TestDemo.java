package com.tydic.dateSourceTest;

import com.tydic.dca.DCAClient;
import com.tydic.dca.config.DCAMConfig;
import com.tydic.dca.connection.DCAConnector;

public class TestDemo
{
  public static void main(String[] args)
    throws InterruptedException
  {
	  //String a= "445476550048^919^0^0^0^0^2019-10-28^2020-07-15^100200^999";
	  //System.out.println(a.substring(0, a.indexOf("^")));
    DCAClient client = getConnect();
    //client.set("BILLING.TEST:1", "123");
    Thread.sleep(2000L);
      
    //System.out.println(client.lrange("PLCA.ENHANCE_ACCT_PAYS:200012213846|919",0,-1));
    //System.out.println(client.renamenx("?PLCA.ENHANCE_CREDIT_DEGREE", "PLCA.ENHANCE_CREDIT_DEGREE"));
  }
  

	 static ThreadLocal<DCAClient> curDcaPool = new ThreadLocal<DCAClient>();

	  public static DCAClient getConnect()
	  {
	    DCAClient con = null;
	    try {
	      con = (DCAClient)curDcaPool.get();
	      if (con != null) {
	        return con;
	      }

	      DCAMConfig dcamConfig = new DCAMConfig("133.64.172.35", 2201, "133.64.172.35", 2201, "tydic", "admin", "123456");

	      DCAConnector connector = new DCAConnector(dcamConfig);
	      con = connector.connect();
	      curDcaPool.set(con);
	    }
	    catch (Exception e) {
	      System.out.println("get dca connection error!"+ e);
	    }
	    return con;
	  }
}
