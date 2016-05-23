package com.sd.mobileapi.util;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;






import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;   
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;   
import org.dom4j.Element;   


public class Sendsms {
	public static final String CODE = "code";
	public static final String MSG = "msg";
	
	private static String Url = "http://106.ihuyi.com/webservice/sms.php?method=Submit";
	
	public static void sendSms(String mobile,String secret){
		HttpClient client = new HttpClient(); 
		PostMethod method = new PostMethod(Url); 
			
		//client.getParams().setContentCharset("GBK");		
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");

	    String content = new String("您的验证码是："+secret+"。请不要把验证码泄露给其他人。"); 
	    
		NameValuePair[] data = {//提交短信
			    new NameValuePair("account", "cf_shacx"), 
			    new NameValuePair("password", "KWKNFZ"), 	//密码可以使用明文密码或使用32位MD5加密
			    new NameValuePair("mobile", mobile), 
			    new NameValuePair("content", content),
		};
		
		method.setRequestBody(data);		
		
		
		try {
			client.executeMethod(method);	
			
			String SubmitResult =method.getResponseBodyAsString();
					
			//System.out.println(SubmitResult);

			Document doc = DocumentHelper.parseText(SubmitResult); 
			Element root = doc.getRootElement();


			String code = root.elementText("code");	
			String msg = root.elementText("msg");	
			String smsid = root.elementText("smsid");	
			
			
			System.out.println(code);
			System.out.println(msg);
			System.out.println(smsid);
			
			if(code == "2"){
				System.out.println("短信提交成功");
			}
			
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	public static boolean justSendSms(String mobile,String content,String account,String password,Map<String,String> retMap){
		HttpClient client = new HttpClient(); 
		PostMethod method = new PostMethod(Url); 
			
		//client.getParams().setContentCharset("GBK");		
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");

	    NameValuePair[] data = {//提交短信
			    new NameValuePair("account", account), 
			    new NameValuePair("password", password), 	//密码可以使用明文密码或使用32位MD5加密
			    new NameValuePair("mobile", mobile), 
			    new NameValuePair("content", content),
		};
		
		method.setRequestBody(data);		
		
		
		try {
			client.executeMethod(method);	
			
			String SubmitResult =method.getResponseBodyAsString();
					
			//System.out.println(SubmitResult);

			Document doc = DocumentHelper.parseText(SubmitResult); 
			Element root = doc.getRootElement();


			String code = root.elementText("code");	
			String msg = root.elementText("msg");	
			String smsid = root.elementText("smsid");	
			
			
			System.out.println(code);
			System.out.println(msg);
			System.out.println(smsid);
			
			
			retMap.put(CODE, code);
			retMap.put(MSG, msg);
			
			if(code.equals("2")){
				System.out.println("短信提交成功");
				return true;
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			retMap.put(CODE, "-200");
			retMap.put(MSG, "HttpException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			retMap.put(CODE, "-300");
			retMap.put(MSG, "IOException");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			retMap.put(CODE, "-400");
			retMap.put(MSG, "DocumentException");
		}	
		return false;
	}
	
	public static void main(String [] args) {
		//Sendsms.sendSms("15951573016", "12323");
		
		Map<String,String> retMap = new HashMap<String,String>();
		
		//   new NameValuePair("account", "cf_shacx"), 
		//    new NameValuePair("password", "KWKNFZ"),
//		Sendsms.justSendSms("18602582663", "12323","cf_shacx","KWKNFZ",retMap);
		Sendsms.sendSms("15706841760", "12323");
//		String name = "宝马M5";
//		String serviceName = "火花塞";
//		
//		String content1 = String.format(Constants.noticeBaoYangNoOBDSmsTemplate,name);
//		String content2 = String.format(Constants.noticeBaoYangSmsTemplate,name);
//		String content3 = String.format(Constants.noticeErrorSmsTemplate,name,"2014-01-01",serviceName);
		
		//Sendsms.justSendSms("15951573016",content1,"cf_shacx","KWKNFZ",retMap);
		//Sendsms.justSendSms("15951573016",content2,"cf_shacx","KWKNFZ",retMap);
//		Sendsms.justSendSms("15951573016",content3,"cf_shacx","KWKNFZ",retMap);
//		HttpClient client = new HttpClient(); 
//		PostMethod method = new PostMethod(Url); 
//			
//		//client.getParams().setContentCharset("GBK");		
//		client.getParams().setContentCharset("UTF-8");
//		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
//
//	    String content = new String("您的验证码是：7528。请不要把验证码泄露给其他人。"); 
//	    
//		NameValuePair[] data = {//提交短信
//			    new NameValuePair("account", "cf_shacx"), 
//			    new NameValuePair("password", "KWKNFZ"), 	//密码可以使用明文密码或使用32位MD5加密
//			    new NameValuePair("mobile", "15195010350"), 
//			    new NameValuePair("content", content),
//		};
//		
//		method.setRequestBody(data);		
//		
//		
//		try {
//			client.executeMethod(method);	
//			
//			String SubmitResult =method.getResponseBodyAsString();
//					
//			//System.out.println(SubmitResult);
//
//			Document doc = DocumentHelper.parseText(SubmitResult); 
//			Element root = doc.getRootElement();
//
//
//			String code = root.elementText("code");	
//			String msg = root.elementText("msg");	
//			String smsid = root.elementText("smsid");	
//			
//			
//			System.out.println(code);
//			System.out.println(msg);
//			System.out.println(smsid);
//			
//			if(code == "2"){
//				System.out.println("短信提交成功");
//			}
//			
//		} catch (HttpException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//		

	}
	
}