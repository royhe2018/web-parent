package com.sdlh.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.fasterxml.jackson.databind.JsonNode;

public class HttpsUtil {

    private static final class DefaultTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    private static HttpsURLConnection getHttpsURLConnection(String urlStr,Map<String,Object> param,String method) throws IOException {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SSLSocketFactory ssf = ctx.getSocketFactory();
        
        String params = "";
		for (String keyp : param.keySet()) {
			params += "&" + keyp + "=" + param.get(keyp);
		}
		urlStr += params.replaceAll("^&", "?");
        
        URL url = new URL(urlStr);
        HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
        httpsConn.setSSLSocketFactory(ssf);
        httpsConn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });
        httpsConn.setRequestMethod(method);
        httpsConn.setDoInput(true);
        httpsConn.setDoOutput(true);
        return httpsConn;
    }

    private static byte[] getBytesFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] kb = new byte[1024];
        int len;
        while ((len = is.read(kb)) != -1) {
            baos.write(kb, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        baos.close();
        is.close();
        return bytes;
    }

    private static void setBytesToStream(OutputStream os, byte[] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        byte[] kb = new byte[1024];
        int len;
        while ((len = bais.read(kb)) != -1) {
            os.write(kb, 0, len);
        }
        os.flush();
        os.close();
        bais.close();
    }

    public static JsonNode doGet(String url,Map<String,Object> param) throws IOException {
        HttpsURLConnection httpsConn = getHttpsURLConnection(url,param,"GET");
        byte[] resultByte = getBytesFromStream(httpsConn.getInputStream());
        JsonNode result = JsonUtil.convertStrToJson(new String(resultByte,"UTF-8"));
        return result;
    }

    public static JsonNode doPost(String uri, String data,Map<String,Object> param) throws IOException {
        HttpsURLConnection httpsConn = getHttpsURLConnection(uri,param, "POST");
        if(StringUtilLH.isNotEmpty(data)){
        	setBytesToStream(httpsConn.getOutputStream(), data.getBytes());
        }
        byte[] resultByte = getBytesFromStream(httpsConn.getInputStream());
        JsonNode result = JsonUtil.convertStrToJson(new String(resultByte));
        return result;
    }
    
    public static void main(String[] args) {
    	//testGet();
    	testPost();
    }
    
    private static void testPost() {
    	try {
    		List<Map<String,Object>> positionList = new ArrayList<Map<String,Object>>();
    		Map<String,Object> item1 = new HashMap<String,Object>();
    		item1.put("x", "116.449429");
    		item1.put("y", "40.014844");
    		item1.put("sp", 4);
    		item1.put("ag", "110");
    		item1.put("tm", new Date().getTime());
    		positionList.add(item1);
    		Thread.sleep(4000);
    		Map<String,Object> item2 = new HashMap<String,Object>();
    		item2.put("x", "116.449639");
    		item2.put("y", "40.014776");
    		item2.put("sp", 4);
    		item2.put("ag", "110");
    		item2.put("tm", new Date().getTime());
    		positionList.add(item2);
    		Thread.sleep(4000);
    		Map<String,Object> item3 = new HashMap<String,Object>();
    		item3.put("x", "116.449859");
    		item3.put("y", "40.014716");
    		item3.put("sp", 3);
    		item3.put("ag", "111");
    		item3.put("tm", new Date().getTime());
    		positionList.add(item3);
    		Thread.sleep(4000);
    		Map<String,Object> item4 = new HashMap<String,Object>();
    		item4.put("x", "116.450074");
    		item4.put("y", "40.014658");
    		item4.put("sp", 3);
    		item4.put("ag", "111");
    		item4.put("tm", new Date().getTime());
    		positionList.add(item4);
    		Map<String,Object> param = new HashMap<String,Object>();
    		param.put("key", "9d28f5fda07db528d149fc98f40d2d75");
    		String url="https://restapi.amap.com/v4/grasproad/driving";
    		JsonNode result = doPost(url,JsonUtil.convertObjectToJsonStr(positionList),param);
    		System.out.println(result.toString());
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
	private static void testGet() {
		try {
        	String url="https://restapi.amap.com/v3/assistant/coordinate/convert";
        	Map<String,Object> param = new HashMap<String,Object>();
        	param.put("locations", "116.481499,39.990475");
        	param.put("coordsys", "gps");
        	param.put("output", "JSON");
        	param.put("key", "9d28f5fda07db528d149fc98f40d2d75");
        	JsonNode result = doGet(url,param);
        	System.out.println(result.get("status"));
        	System.out.println(result.get("info"));
        	System.out.println(result.get("locations"));
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
    
    
}