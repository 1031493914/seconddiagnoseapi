package com.sd.mobileapi.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiTest {
	private String host = "http://210.22.108.78:8081";
//	private String host=  "http://118.144.88.201:8090/api";
//	private String host="http://api.anchexin.com";
//	private String host="http://localhost:8080/saicapi";
//	private String host="http://localhost:8080/seconddiagnoseapi";
	
	private HttpURLConnection conn;
	private void init(String url)throws Exception{
		if(conn != null) conn.disconnect();
		conn = (HttpURLConnection)new URL(url).openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");		
		conn.setConnectTimeout(10000);
		conn.setReadTimeout(10000);
	}
	public void sendOutput(String value) throws Exception{
		System.out.println("sendOutput=========================================");
		System.out.println("value:"+value);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		writer.write(value);
		writer.close();
	}
	public void printlnInput() throws Exception{
		int code = conn.getResponseCode();
		InputStream in = null;
		if(code == 200){
			in = conn.getInputStream();
		}else{
			in = conn.getErrorStream();
		}		
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder builder = new StringBuilder();
		String value = reader.readLine();
		while(value != null){
			builder.append(value);
			value = reader.readLine();
		}
		reader.close();		
		System.out.println("printlnInput=========================================");
		System.out.println("basestring:"+builder.toString().trim());
		if(code == 200){
			value =builder.toString();
		}
		System.out.println("ResponseCode:"+code);
		System.out.println("ContentType:"+conn.getContentType());
		//System.out.println("value:"+builder.toString());
		System.out.println("value:"+value);
		System.out.println("printlnInput OK=========================================");
	}	
	
	public void getSecret(String input) throws Exception{
		init(host+"/getSecret");
		sendOutput(input);	
		printlnInput();
		conn.disconnect();
	}

	
	public void getLogin(String input) throws Exception{
		init(host+"/getLogin");
		sendOutput(input);	
		printlnInput();
		conn.disconnect();
	}
	
	
	public void checksecret(String input) throws Exception{
		init(host+"/checksecret");
		sendOutput(input);	
		printlnInput();
		conn.disconnect();
	}
	
	
	public void setPassword(String input) throws Exception{
		init(host+"/setPassword");
		sendOutput(input);	
		printlnInput();
		conn.disconnect();
	}
	
	
	
	public void setinformation(String input) throws Exception{
		init(host+"/setinformation");
		sendOutput(input);	
		printlnInput();
		conn.disconnect();
	}
	
	public void searchpatient(String input) throws Exception{
		init(host+"/searchpatient");
		sendOutput(input);	
		printlnInput();
		conn.disconnect();
	}
	
	public void onlinenurse(String input) throws Exception{
		init(host+"/onlinenurse");
		sendOutput(input);	
		printlnInput();
		conn.disconnect();
	}
	public void onlinedoctor(String input) throws Exception{
		init(host+"/onlinedoctor");
		sendOutput(input);	
		printlnInput();
		conn.disconnect();
	}
	
	public void setpatient(String input) throws Exception{
		init(host+"/setpatient");
		sendOutput(input);	
		printlnInput();
		conn.disconnect();
	}
	
	
	public void getlistpatientbydoctorid(String input) throws Exception{
		init(host+"/getlistpatientbydoctorid");
		sendOutput(input);	
		printlnInput();
		conn.disconnect();
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		ApiTest test = new ApiTest();
		String onlinenurse="{\"userid\":\"4\"}";
		String onlinedoctor="{\"userid\":\"4\"}";
		String setpatient="{\"userid\":\"1\",\"patientid\":\"3\"}";
		String searchpatient="{\"params\":\"test\"}";
		String getLogin="{\"username\":\"18268123619\",\"userpwd\":\"lyft76149095\"}";
		String getSecret="{\"mobile\":\"13706841760\",\"role\":\"2\"}";
		String checksecret="{\"userid\":\"4\",\"token\":\"d2683ce61ec245da45c853e6385e705c\",\"secret\":\"316736\",\"mobile\":\"18602582663\"}";
		String setinformation="{\"userid\":\"4\",\"name\":\"teee\",\"usernumber\":\"123456\",\"outtime\":\"\",\"bedcode\":\"456\"}";
		String getlistpatientbydoctorid="{\"userid\":\"1\"}";
//		test.getLogin(getLogin);
		test.getSecret(getSecret);
//		test.checksecret(checksecret);
//		test.setinformation(setinformation);
//		test.onlinenurse(onlinenurse);
//		test.getlistpatientbydoctorid(getlistpatientbydoctorid);
	
	}
}
