package com.sdlh.common;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author dataochen
 * @Description
 * @date: 2017/11/7 17:49
 */
public class HttpRequestUtil {
	private static CloseableHttpClient httpClient;

	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(100);
		cm.setDefaultMaxPerRoute(20);
		cm.setDefaultMaxPerRoute(50);
		httpClient = HttpClients.custom().setConnectionManager(cm).build();
	}

	public static String get(String url) {
		CloseableHttpResponse response = null;
		BufferedReader in = null;
		String result = "";
		try {
			HttpGet httpGet = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000)
					.setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
			httpGet.setConfig(requestConfig);
			httpGet.setConfig(requestConfig);
			httpGet.addHeader("Content-type", "application/json; charset=utf-8");
			httpGet.setHeader("Accept", "application/json");
			response = httpClient.execute(httpGet);
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			result = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != response) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String post(String url, String jsonString) {
		CloseableHttpResponse response = null;
		BufferedReader in = null;
		String result = "";
		try {
			HttpPost httpPost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000)
					.setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
			httpPost.setConfig(requestConfig);
			httpPost.setConfig(requestConfig);
			httpPost.addHeader("Content-type", "application/json; charset=utf-8");
			httpPost.setHeader("Accept", "application/json");
			httpPost.setEntity(new StringEntity(jsonString,Charset.forName("UTF-8")));
			response = httpClient.execute(httpPost);
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			result = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != response) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static JsonNode getForJsonResult(String url, Map<String, Object> param) {
		CloseableHttpResponse response = null;
		BufferedReader in = null;
		JsonNode resultMap = null;
		String result = "";
		try {
			String params = "";
			for (String keyp : param.keySet()) {
				params += "&" + keyp + "=" + param.get(keyp);
			}
			url += params.replaceAll("^&", "?");
			HttpGet httpGet = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000)
					.setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
			httpGet.setConfig(requestConfig);
			httpGet.setConfig(requestConfig);
			httpGet.addHeader("Content-type", "application/json; charset=utf-8");
			httpGet.setHeader("Accept", "application/json");
			response = httpClient.execute(httpGet);
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			result = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != response) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(StringUtilLH.isNotEmpty(result)) {
			resultMap = JsonUtil.convertStrToJson(result);
		}
		return resultMap;
	}
}