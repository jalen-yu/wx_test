package cn.xingaohbd.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 
* @ClassName: HttpClientUtil
* @Description: httpClient工具类
* @author huang
* @date 2014-6-21 上午11:10:43
*
 */
public class HttpClientUtil {
	private static CloseableHttpClient __httpClient = null;

	private synchronized static CloseableHttpClient createHttpClient() {
		if (__httpClient == null) {
			__httpClient = HttpClients.createDefault();
		}
		return __httpClient;
	}

	public static String doPost(String url,Map map,String charset){
		Iterator iter = map.entrySet().iterator();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey()==null?"": entry.getKey();
			Object val = entry.getValue()==null?"":entry.getValue();
			formparams.add(new BasicNameValuePair(key.toString(), val.toString())); 
		}
		return doPost(url,formparams,charset);
	}
	public static String doPostI(String url,Map map,String charset,Map<String, String> headerMap,Integer sumImg){
		Iterator iter = map.entrySet().iterator();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey()==null?"": entry.getKey();
			Object val = entry.getValue()==null?"":entry.getValue();
			formparams.add(new BasicNameValuePair(key.toString(), val.toString()));
		}
		return doPostImg(url,formparams,charset,headerMap,sumImg);
	}
	public static String doPostH(String url,Map map,String charset,Map<String, String> headerMap){
		Iterator iter = map.entrySet().iterator();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey()==null?"": entry.getKey();
			Object val = entry.getValue()==null?"":entry.getValue();
			formparams.add(new BasicNameValuePair(key.toString(), val.toString()));
		}
		return doPostHb(url,formparams,charset,headerMap);
	}

	public static String doPost(String url, List<NameValuePair> params,
			String charset) {
		CloseableHttpClient httpClient = createHttpClient();
		//Assert.notNull(httpClient);

		HttpPost httpRequest = new HttpPost(url);
		String strResult = "doPostError";
		try {
			/* 添加请求参数到请求对象 */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, charset));
			/* 发送请求并等待响应 */
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				strResult = EntityUtils.toString(httpResponse.getEntity(),charset);

			} else {
				strResult = "Error Response: "
						+ httpResponse.getStatusLine().toString();
			}
		} catch (Exception ex) {
			__httpClient = null;
			ex.printStackTrace();
		}
		finally
		{
			if(httpRequest!=null)
			{
				httpRequest.releaseConnection();
				httpRequest=null;
			}
		}
		return strResult;
	}
	public static String doPostHb(String url, List<NameValuePair> params,
			String charset,Map<String, String> headerMap) {
		CloseableHttpClient httpClient = createHttpClient();
		//Assert.notNull(httpClient);
		
		HttpPost httpRequest = new HttpPost(url);
		Iterator iter = headerMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey()==null?"": entry.getKey();
			Object val = entry.getValue()==null?"":entry.getValue();
			httpRequest.setHeader(key.toString(), val.toString());
		}
		String strResult = "doPostError";
		byte[] b ;
		try {
			/* 添加请求参数到请求对象 */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, charset));
			/* 发送请求并等待响应 */
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				strResult = EntityUtils.toString(httpResponse.getEntity(),charset);

			} else {
				strResult = "Error Response: "
						+ httpResponse.getStatusLine().toString();
			}
		} catch (Exception ex) {
			__httpClient = null;
			ex.printStackTrace();
		}
		finally
		{
			if(httpRequest!=null)
			{
				httpRequest.releaseConnection();
				httpRequest=null;
			}
		}
		
		return strResult;
	}

	public static String doPostImg(String url, List<NameValuePair> params,
								  String charset,Map<String, String> headerMap,Integer sumImg) {
		CloseableHttpClient httpClient = createHttpClient();
		//Assert.notNull(httpClient);

		HttpPost httpRequest = new HttpPost(url);
		Iterator iter = headerMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey()==null?"": entry.getKey();
			Object val = entry.getValue()==null?"":entry.getValue();
			httpRequest.setHeader(key.toString(), val.toString());
		}
		String strResult = "doPostError";
		byte[] b ;
		try {
			/* 添加请求参数到请求对象 */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, charset));
			/* 发送请求并等待响应 */
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
//				strResult = EntityUtils.toString(httpResponse.getEntity(),charset);
				strResult = "H:\\codeImg\\"+sumImg+".png";
				OutputStream os = new FileOutputStream(strResult);
				InputStream is = httpResponse.getEntity().getContent();
				byte[] bs = new byte[1024];
				int len = -1;
				while ((len = is.read(bs))!=-1){
					os.write(bs,0,len);
				}
				os.flush();
				os.close();
				is.close();
			} else {
				strResult = "Error Response: "
						+ httpResponse.getStatusLine().toString();
			}
		} catch (Exception ex) {
			__httpClient = null;
			ex.printStackTrace();
		}
		finally
		{
			if(httpRequest!=null)
			{
				httpRequest.releaseConnection();
				httpRequest=null;
			}
		}
		return strResult;
	}
	
	 public static String getResultInfo(String url,Map map,String charset,Map<String, String> headerMap){
		 Iterator iter = map.entrySet().iterator();
			List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey()==null?"": entry.getKey();
				Object val = entry.getValue()==null?"":entry.getValue();
				formparams.add(new BasicNameValuePair(key.toString(), val.toString())); 
			}
			return doPostXml(url,formparams,charset,headerMap);
	 }
	 
	 @SuppressWarnings("unchecked")
	public static String doPostXml(String url, List<NameValuePair> params,
				String charset,Map<String, String> headerMap) {
			CloseableHttpClient httpClient = createHttpClient();
			//Assert.notNull(httpClient);
			
			HttpPost httpRequest = new HttpPost(url);
			Iterator iter = headerMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey()==null?"": entry.getKey();
				Object val = entry.getValue()==null?"":entry.getValue();
				httpRequest.setHeader(key.toString(), val.toString());
			}
			String strResult = "doPostError";
			try {
				/* 添加请求参数到请求对象 */
				httpRequest.setEntity(new UrlEncodedFormEntity(params, charset));
				/* 发送请求并等待响应 */
				HttpResponse httpResponse = httpClient.execute(httpRequest);
				/* 若状态码为200 ok */
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					/* 读返回数据 */
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					//2.创建解析器：DocumentBuilder
					DocumentBuilder db = dbf.newDocumentBuilder();
					//3.创建文档树模型Document
					org.w3c.dom.Document doc = db.parse(httpResponse.getEntity().getContent());
					strResult = EntityUtils.toString(httpResponse.getEntity(),charset);
				} else {
					strResult = "Error Response: "
							+ httpResponse.getStatusLine().toString();
				}
			} catch (Exception ex) {
				__httpClient = null;
				ex.printStackTrace();
			}
			finally
			{
				if(httpRequest!=null)
				{
					httpRequest.releaseConnection();
					httpRequest=null;
				}
			}
			
			return strResult;
		}
	
	public static String doGet(String url, Map params, String charset) {

		StringBuffer paramStr = new StringBuffer("");

		Iterator iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			paramStr.append("&").append(key).append("=").append(val);
		}

		if (!paramStr.equals("")) {
			String tempStr = paramStr.toString().replaceFirst("&", "?");
			url += tempStr;
		}

		return doGet(url, charset);

	}
	public static String doGetH(String url, Map params, String charset,Map<String, String> headerMap) {

		StringBuffer paramStr = new StringBuffer("");

		Iterator iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			paramStr.append("&").append(key).append("=").append(val);
		}

		if (!paramStr.equals("")) {
			String tempStr = paramStr.toString().replaceFirst("&", "?");
			url += tempStr;
		}
//		System.out.println("====="+url);
		return doGetHB(url, charset,headerMap);

	}
	public static String doGetHB(String url, String charset,Map<String, String> headerMap) {
		CloseableHttpClient httpClient = createHttpClient();
		//Assert.notNull(httpClient);

		
		String strResult = "doGetError";
		HttpGet httpRequest = null;
		try {
			 httpRequest = new HttpGet(url);
			Iterator iter = headerMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey()==null?"": entry.getKey();
				Object val = entry.getValue()==null?"":entry.getValue();
				httpRequest.setHeader(key.toString(), val.toString());
			}
			/* 发送请求并等待响应 */
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				strResult = EntityUtils.toString(httpResponse.getEntity(), charset);
			} else {
				strResult = "Error Response: "
						+ httpResponse.getStatusLine().toString();
			}
		} catch (ClientProtocolException ex) {
			__httpClient = null;
			ex.printStackTrace();
		} catch (IOException ex) {
			__httpClient = null;
			ex.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("http错处啦"+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			if(httpRequest!=null)
			{
				httpRequest.releaseConnection();
				httpRequest=null;
			}
		}
		return strResult;
	}
	public static String doGet(String url, String charset) {
		CloseableHttpClient httpClient = createHttpClient();
		//Assert.notNull(httpClient);
		HttpGet httpRequest = null;
		
		String strResult = "doGetError";

		try {
			 httpRequest = new HttpGet(url);
			/* 发送请求并等待响应 */
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				strResult = EntityUtils.toString(httpResponse.getEntity(), charset);
			} else {
				strResult = "Error Response: "
						+ httpResponse.getStatusLine().toString();
			}
		} catch (ClientProtocolException ex) {
			__httpClient = null;
			ex.printStackTrace();
		} catch (IOException ex) {
			__httpClient = null;
			ex.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("http错处啦"+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			if(httpRequest!=null)
			{
				httpRequest.releaseConnection();
				httpRequest=null;
			}
		}
		return strResult;
	}

	


	private static String inputStreamToString(InputStream in)
			throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

}
