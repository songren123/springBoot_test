package com.tydic.dateSourceTest.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.catalina.webresources.war.Handler;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.common.config.DcaClientTemplate;

@Service
public class TestService {
	@Autowired
	public SqlSessionTemplate sqlSessionTemplate;
	@Autowired
	public DcaClientTemplate dcaClientTemplate;
	
	private Logger log =LoggerFactory.getLogger(this.getClass());
	
	public void test() {
		int i =0;
		try {
			/*List<Map<String, Object>> resultList = sqlSessionTemplate
					.selectList("com.tydic.mapper.commonMapper.qryAllKeyID");
			for (Map<String, Object> map : resultList) {
				String prodInstId = map.get("prod_inst_id").toString();
				String keyId = map.get("key_id").toString();
				String keyValue = sqlSessionTemplate.selectOne("com.tydic.mapper.commonMapper.qryKeyvalue",prodInstId);
				dcaClientTemplate.del(keyId);
				dcaClientTemplate.lpush(keyId, keyValue);
				i++;
				log.info("刷新成功，第" + i + "个，KEY_ID为： " + keyId+"KEY_VALUE: "+keyValue);

			} */
			//dcaClientTemplate.del("PLCA.ENHANCE_CREDIT_DEGREE:201312238917|290");
			
			//Long b=dcaClientTemplate.hsetValue("PLCA.ENHANCE_CREDIT_DEGREE:201312238917|290", "201312238917", "201312238917^290^0^0^0^600^2020-02-28 18:07:59^2020-10-15 00:00:00^0A^999");
			
			//List<String> a=dcaClientTemplate.queryNonBusiness("PLCA.PNT_PROD_INST_919_200239416804");
			
			/*List<Map<String, Object>> resultList = sqlSessionTemplate.selectList("com.tydic.mapper.commonMapper.qryDataList7");
			for (Map<String, Object> map : resultList) {
				System.out.println("b================ljw============="+map);
				sqlSessionTemplate.update("com.tydic.mapper.commonMapper.qryDataList8", map);
				sqlSessionTemplate.update("com.tydic.mapper.commonMapper.qryDataList9", map);
				sqlSessionTemplate.update("com.tydic.mapper.commonMapper.qryDataList10", map);
				sqlSessionTemplate.update("com.tydic.mapper.commonMapper.qryDataList11", map);
				sqlSessionTemplate.update("com.tydic.mapper.commonMapper.qryDataList12", map);
			}*/
			
			
			/*List<Map<String, Object>> resultList = sqlSessionTemplate.selectList("com.tydic.mapper.commonMapper.qryDataList_p1");
			for (Map<String, Object> map : resultList) {
				System.out.println("b================ljw============="+map);
				Map<String, Object> mapnew =sqlSessionTemplate.selectOne("com.tydic.mapper.commonMapper.qryDataList_p2", map);
				System.out.println("c================ljw============="+mapnew);
				map.putAll(mapnew);
				Map<String, Object> mapCredit =sqlSessionTemplate.selectOne("com.tydic.mapper.commonMapper.qryDataList_p3", map);
                if(null==mapCredit) {
					sqlSessionTemplate.insert("com.tydic.mapper.commonMapper.inDataList_p2", map);
					sqlSessionTemplate.insert("com.tydic.mapper.commonMapper.inDataList_p3", map);
					sqlSessionTemplate.insert("com.tydic.mapper.commonMapper.inDataList_p4", map);
					sqlSessionTemplate.insert("com.tydic.mapper.commonMapper.inDataList_p5", map);
                }else {
                	System.out.println("d================ljw============="+map);	
                }
				i++;
				
			}*/
			
			List<Map<String, Object>> resultList = sqlSessionTemplate.selectList("com.tydic.mapper.commonMapper.qryDataList7a");
			for (Map<String, Object> map : resultList) {
				System.out.println("b================ljw============="+map);
				sqlSessionTemplate.update("com.tydic.mapper.commonMapper.qryDataList8a", map);
				sqlSessionTemplate.update("com.tydic.mapper.commonMapper.qryDataList9a", map);
				sqlSessionTemplate.update("com.tydic.mapper.commonMapper.qryDataList10a", map);
				sqlSessionTemplate.delete("com.tydic.mapper.commonMapper.qryDataList10b", map);
				sqlSessionTemplate.update("com.tydic.mapper.commonMapper.qryDataList11a", map);
				sqlSessionTemplate.update("com.tydic.mapper.commonMapper.qryDataList12a", map);
				i++;
			}
			
			System.out.println("i================ljw============="+i);
			
		} catch (Exception e) {
			log.error("--------------"+e.getMessage());
		}
		
	}
	
	
	
	
	public void pushDcaAddByCrm() {
		try {
		
			 sqlSessionTemplate.select("com.tydic.mapper.commonMapper.qryDataList6", new ResultHandler<Map<String, Object>>() {

				@Override
				public void handleResult(ResultContext<? extends Map<String, Object>> arg0) {
					
					Map<String,Object> map =(Map<String, Object>) arg0.getResultObject();
					String keyId = map.get("key_id").toString();				
					String objId = map.get("obj_id").toString();
					String keyValue = map.get("key_value").toString();
					String latn_id = map.get("latn_id").toString();
					keyId ="PLCA.ENHANCE_CREDIT_DEGREE:"+objId+"|"+latn_id;
					log.info("keyId为： " + keyId);
				    try {
						//Long a=dcaClientTemplate.del(keyId);
						Long b=dcaClientTemplate.hsetValue(keyId,objId,keyValue);
						log.info("b------===================: "+b);			
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				    					
					
				}
			});
			
			/*int i =0;
			for (int j = 0; j < 3200; j++) {
				
			Map<String,Object> mapa =new HashMap<String, Object>(); 
			log.info("i---1---: "+i);
			mapa.put("num_a",i);
			mapa.put("num_b",i+10000);
			log.info("i---2---: "+i);
			List<Map<String, Object>> resultList = sqlSessionTemplate.selectList("com.tydic.mapper.commonMapper.qryDataList2",mapa);
			for (Map<String, Object> map : resultList) {
				
				String keyId = map.get("key_id").toString();
				String objId = map.get("obj_id").toString();
				String keyValue = map.get("key_value").toString();
				try {
					//Long a=dcaClientTemplate.del(keyId);
					Long b=dcaClientTemplate.hsetValue(keyId,objId,keyValue);
					log.info("b------: "+b);
					//log.info("KEY_ID为： " + keyId);
					i++;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		  }*/
		} catch (Exception e) {
			log.error("--------------"+e);
		}
		
	}

}
