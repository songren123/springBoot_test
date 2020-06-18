package com.tydic.common.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.tydic.dca.DCAClient;
import com.tydic.dca.config.DCAMConfig;
import com.tydic.dca.connection.DCAConnector;


@Configuration
@ConfigurationProperties(prefix = "dca")
public class DcaClientTemplate {
    
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@Value("${flag}")
	private String dcaFlag;

	@Value("${reconnectCount}")
	private int reconnectCount;
	
	  @Value("${ip}")
	  private String ip;
	  @Value("${port}")
	  private int port;
	  @Value("${slaveIp}")
	  private String slaveIp;
	  @Value("${slavePort}")
	  private int slavePort;
	  @Value("${acctID}")
	  private String acctID;
	  @Value("${dca.userName}")
	  private String userName;
	  @Value("${dca.passWord}")
	  private String passWord;
	  @Value("${pid}")
	  private int pid;
	  @Value("${processName}")
	  private String processName;
	  @Value("${dcasConnTimeOut}")
	  private int dcasConnTimeOut;
	  @Value("${dcasSoTimeOut}")
	  private int dcasSoTimeOut;
	  @Value("${dcasAgainNumber}")
	  private int dcasAgainNumber;

	
	  //private DCAClient dcaClient;
	
	
	 ThreadLocal<DCAClient> curDcaPool = new ThreadLocal<DCAClient>();

	  public DCAClient getConnect()
	  {
	    DCAClient con = null;
	    try {
	      con = (DCAClient)this.curDcaPool.get();
	      if (con != null) {
	        return con;
	      }

	      DCAMConfig dcamConfig = new DCAMConfig(ip, port, slaveIp, slavePort, acctID, userName, passWord);

	      DCAConnector connector = new DCAConnector(dcamConfig);
	      con = connector.connect();
	      this.curDcaPool.set(con);
	    }
	    catch (Exception e) {
	      logger.error("get dca connection error!", e);
	    }
	    return con;
	  }

	/***
	 * 底层存放的是list
	 */
	public List<String> queryNonBusiness(String key2) {
		if (!key2.startsWith(dcaFlag)) {
			key2 = dcaFlag + "." + key2;
		}
		logger.debug("start get dca key2:" + key2);
		List<String> result = null;
		long startTime = 0L;
		long endTime = 0L;
		try {
			startTime = System.currentTimeMillis();
			 
			//result = getConnect().lrange(key2, 0, -1);
			result = getConnect().hvals(key2);
			endTime = System.currentTimeMillis();
			logger.debug("time cost: " + (endTime - startTime) + ",redis key[" + key2 + "],ret:" + result);
			if (result != null && result.size() == 0) {
				result = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("dca get key[" + key2 + "] value error reconnectCount:" + reconnectCount, e);
		}

		return result;
	}
	
	/***
	 * 底层存放的是list
	 */
	public String queryNonBusiness(String key,String val) {
		String result = null;
		long startTime = 0L;
		long endTime = 0L;
		try {
			startTime = System.currentTimeMillis();
			List<String> resultSet = getConnect().lrange(key+":"+val, 0, -1);
			endTime = System.currentTimeMillis();
			logger.debug("time cost: " + (endTime - startTime) + ",redis key[" +key+":"+val+ "],ret:" + result);
			if (resultSet != null && resultSet.size() > 0) {
				result = resultSet.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("dca get key[" +key+":"+val + "] value error reconnectCount:" + reconnectCount, e);
		}

		return result;
	}

	public Long lrem(String key2, long count, String value) {
		if (!key2.startsWith(dcaFlag)) {
			key2 = dcaFlag + "." + key2;
		}
		long ret = 0;
		long startTime = 0L;
		long endTime = 0L;
		try {
			startTime = System.currentTimeMillis();
			
			ret = getConnect().lrem(key2, count, value);
			
			endTime = System.currentTimeMillis();
			logger.debug("time cost: " + (endTime - startTime) + " dca lrem key[" + key2 + "],value[" + value
					+ "],redis ret:" + ret);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("dca lrem key[" + key2 + "],value[" + value + "]  error reconnectCount:" + reconnectCount, e);
		}
		return ret;
	}

	public Long lpush(String key2, String value) {
		if (!key2.startsWith(dcaFlag)) {
			key2 = dcaFlag + "." + key2;
		}
		long ret = 0;
		long startTime = 0L;
		long endTime = 0L;
		try {
			startTime = System.currentTimeMillis();
			 
			ret = getConnect().lpush(key2, value);
			
			endTime = System.currentTimeMillis();
			logger.debug("time cost: " + (endTime - startTime) + " dca lpush key[" + key2 + "],value[" + value
					+ "],redis ret:" + ret);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("dca lpush key[" + key2 + "],value[" + value + "]  error reconnectCount:" + reconnectCount, e);
		}
		return ret;
	}

	public Boolean expire(String key, int timeout) throws Exception {
		if (!key.startsWith(dcaFlag)) {
			key = dcaFlag + "." + key;
		}
		Boolean ret = false;
		long startTime = 0L;
		long endTime = 0L;
		try {
			startTime = System.currentTimeMillis();
		 
			ret = getConnect().expire(key, timeout);

			endTime = System.currentTimeMillis();
			logger.debug("time cost: " + (endTime - startTime) + ",redis ret:" + ret);
		} catch (Exception e) {
			logger.error("dca expire value error reconnectCount:" + reconnectCount, e);
			endTime = System.currentTimeMillis();
		}

		return ret;
	}

	public Long incr(String key) throws Exception {
		if (!key.startsWith(dcaFlag)) {
			key = dcaFlag + "." + key;
		}
		Long ret = 0L;
		long startTime = 0L;
		long endTime = 0L;
		try {
			startTime = System.currentTimeMillis();
			 
			ret = getConnect().incr(key);
			
			endTime = System.currentTimeMillis();
			logger.debug("time cost: " + (endTime - startTime) + ",redis ret:" + ret);
		} catch (Exception e) {
			logger.error("dca incr value error reconnectCount:" + reconnectCount, e);
			endTime = System.currentTimeMillis();
		}

		return ret;
	}
	
	
	
	public String getValue(String key) throws Exception {
		if (!key.startsWith(dcaFlag)) {
			key = dcaFlag + "." + key;
		}
		String ret = null;
		long startTime = 0L;
		long endTime = 0L;
		try {
			startTime = System.currentTimeMillis();
			 
			ret = getConnect().get(key);
			
			endTime = System.currentTimeMillis();
			logger.debug("time cost: " + (endTime - startTime) + ",redis ret:" + ret);
		} catch (Exception e) {
			logger.error("dca incr value error reconnectCount:" + reconnectCount, e);
			endTime = System.currentTimeMillis();
		}

		return ret;
	}
	
	public Long del(String key) throws Exception {
		if (!key.startsWith(dcaFlag)) {
			key = dcaFlag + "." + key;
		}
		Long ret = 0L;
		long startTime = 0L;
		long endTime = 0L;
		try {
			startTime = System.currentTimeMillis();
			String[] keys = {key}; 
			ret = getConnect().del(keys);
			
			endTime = System.currentTimeMillis();
			logger.debug("time cost: " + (endTime - startTime) + ",redis ret:" + ret);
		} catch (Exception e) {
			logger.error("dca incr value error reconnectCount:" + reconnectCount, e);
			endTime = System.currentTimeMillis();
		}

		return ret;
	}
	
	public Long hsetValue(String key2,String obj_id, String value) {
		if (!key2.startsWith(dcaFlag)) {
			key2 = dcaFlag + "." + key2;
		}
		long ret = 0;
		long startTime = 0L;
		long endTime = 0L;
		try {
			startTime = System.currentTimeMillis();
			 
			ret = getConnect().hset(key2, obj_id, value);
			
			endTime = System.currentTimeMillis();
			logger.debug("time cost: " + (endTime - startTime) + " dca hset key[" + key2 + "],value[" + value
					+ "],redis ret:" + ret);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("dca hset key[" + key2 + "],value[" + value + "]  error reconnectCount:" + reconnectCount, e);
		}
		return ret;
	}
	
}
