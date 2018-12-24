package com.sdlh.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlipayBankNoCheck {
	private static Logger logger = LoggerFactory.getLogger(AlipayBankNoCheck.class);
	public static void main(String[] args) {
		String host = "https://yunyidata.market.alicloudapi.com";
        String path = "/bankAuthenticate4";
        String method = "POST";
        String appcode = "23520bb741914f24ae7377a6ea8f519f";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("ReturnBankInfo", "YES");
        bodys.put("cardNo", "6229013093915119");
        bodys.put("idNo", "610328198504031816");
        bodys.put("name", "何志龙");
        bodys.put("phoneNo", "15129222933");
        
        try {
            /**
            * 重要提示如下:
            * HttpUtils请从
            * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
            * 下载
            *
            * 相应的依赖请参照
            * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
            */
        	HttpResponse	response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static String checkUserBankCardInfo(String cardNo,String idNo,String name,String phoneNo){
		String result = "{}";
		try {
			String host = "https://yunyidata.market.alicloudapi.com";
	        String path = "/bankAuthenticate4";
	        String method = "POST";
	        String appcode = "23520bb741914f24ae7377a6ea8f519f";
	        Map<String, String> headers = new HashMap<String, String>();
	        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	        headers.put("Authorization", "APPCODE " + appcode);
	        //根据API的要求，定义相对应的Content-Type
	        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	        Map<String, String> querys = new HashMap<String, String>();
	        Map<String, String> bodys = new HashMap<String, String>();
	        bodys.put("ReturnBankInfo", "YES");
	        bodys.put("cardNo", cardNo);
	        bodys.put("idNo", idNo);
	        bodys.put("name", name);
	        bodys.put("phoneNo", phoneNo); 
        	HttpResponse	response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            //获取response的body
            result = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
        	logger.error("验证银行卡信息异常", e);
        }
		return result;
	}
}
