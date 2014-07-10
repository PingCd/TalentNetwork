package com.talentnetwork.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import com.talentnetwork.constant.GloableParams;

import android.content.Context;
import android.widget.Toast;


public class HttpClientUtil {
	public final String URL = "http://www.0710rczx.com/";


	public HttpClientUtil() {
	}
	public void getToast(Context context,String str){
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	}

	/**
	 * 发送xml文件到服务器
	 * 
	 * @param url
	 * @param xml
	 */
	/**
	 * 
	 * @author Administrator 获取网页内容
	 * @TODO tyc
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public  String getRequest(Context context,String url ,Map<String, String> params) throws Exception {
		DefaultHttpClient client= new DefaultHttpClient(new BasicHttpParams());
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
		return getRequest(url,client,params);
	}

	protected static String getRequest(String url, DefaultHttpClient client,Map<String, String> params)
			throws Exception {

		String result = null;
		int statusCode = 0;
		
		
		HttpPost postMethod = new HttpPost(url);
		
		// 设置公共头信
//		post.setHeaders(headers);
		// 设置参数
		if (params != null && params.size() > 0) {
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			for (Map.Entry<String, String> item : params.entrySet()) {
				BasicNameValuePair pair = new BasicNameValuePair(item.getKey(),
						item.getValue());
				parameters.add(pair);

			}
			try {
				UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(
						parameters, GloableParams.CHARSET);
				postMethod.setEntity(encodedFormEntity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

		try {
			HttpResponse httpResponse = client.execute(postMethod);
			// statusCode == 200 正常
			statusCode = httpResponse.getStatusLine().getStatusCode();
			// 处理返回的httpResponse信息
			result = retrieveInputStream(httpResponse.getEntity());
			
		}catch (SocketException e) {
			//连接不到网络
		}catch (Exception e) {
			throw new Exception(e);
		} finally {
			postMethod.abort();
		}
		return result;
	}

	protected static String retrieveInputStream(HttpEntity httpEntity) {
		int length = (int) httpEntity.getContentLength();
		if (length < 0)
			length = 10000;
		StringBuffer stringBuffer = new StringBuffer(length);
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(
					httpEntity.getContent(), HTTP.UTF_8);
			char buffer[] = new char[length];
			int count;
			while ((count = inputStreamReader.read(buffer, 0, length - 1)) > 0) {
				stringBuffer.append(buffer, 0, count);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}

	


}
