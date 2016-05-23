package com.sd.mobileapi.util;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class ListMessageUtil {
	 public static void main(String[] args) throws Exception{
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	        String url = "https://api.netease.im/nimserver/history/querySessionMsg.action";
	        HttpPost httpPost = new HttpPost(url);

	        String appKey = "cabeec1d91d1939d12efd9b7e75905a1";
	        String appSecret = "9767e914199f";
	        String nonce =  "12345";
	        String curTime = String.valueOf((new Date()).getTime() / 1000L);
	        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

	        // 设置请求的header
	        httpPost.addHeader("AppKey", appKey);
	        httpPost.addHeader("Nonce", nonce);
	        httpPost.addHeader("CurTime", curTime);
	        httpPost.addHeader("CheckSum", checkSum);
	        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

	        // 设置请求的参数
	        
	        
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String start="2016-03-28 00:00:00";
	        //得到毫秒数
	        long timeStart=sdf.parse(start).getTime();
	        long justNow =System.currentTimeMillis();
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add(new BasicNameValuePair("from", "helloworld1"));
	        nvps.add(new BasicNameValuePair("to", "helloworld"));
	        nvps.add(new BasicNameValuePair("begintime", timeStart+""));
	        nvps.add(new BasicNameValuePair("endtime",justNow+""));
	        nvps.add(new BasicNameValuePair("limit","99"));
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

	        // 执行请求
	        HttpResponse response = httpClient.execute(httpPost);

	        // 打印执行结果
	        System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
	    }
	
}
