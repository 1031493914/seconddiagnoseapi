package com.sd.mobileapi.controller;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sd.mobileapi.model.DoctorPaitent;
import com.sd.mobileapi.model.User;
import com.sd.mobileapi.service.LoginService;
import com.sd.mobileapi.service.UserService;
import com.sd.mobileapi.util.Base64;
import com.sd.mobileapi.util.NeteaseUtil;

/**
 * 登陆和注册接口
 * 
 * @author jx
 */
@Controller
public class UserController {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * 添加病人
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value="/setpatient")
	public void setpatient(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String str=getRequestBodyString(req);
		if(StringUtils.isBlank(str)){
			responseErrorData("JSON串为空",resp);
			return;
		}
		JSONObject json=new JSONObject(str);
		String userid=json.optString("userid");
		String patientid=json.optString("patientid");
		//看是否添加过
		String id=userService.getDoctorPaitent(userid,patientid);	
		if(StringUtils.isBlank(id)){
			
			userService.insertDoctorPaitent(userid,patientid);		
			
		}
		//获取病人列表
		
		JSONObject resultObj = new JSONObject();
		resultObj.put("errorcode",0);
		resultObj.put("message", "病人添加成功");
		responseData(resultObj.toString(),resp);		
	}	
	
	
	
	
	/**
	 * 医生获取添加的病人列表
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value="/getlistpatientbydoctorid")
	public void getlistpatientbydoctorid(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String str=getRequestBodyString(req);
		if(StringUtils.isBlank(str)){
			responseErrorData("JSON串为空",resp);
			return;
		}
		JSONObject json=new JSONObject(str);
		String userid=json.optString("userid");
		//获取病人列表
		List<String> listpaitentid=userService.getListDoctorPaitentByParams(userid);
		List<User> listpatient=new ArrayList<User>();
		for(String paitentid:listpaitentid){
			User paitent =userService.getUserByUserId(paitentid);
			listpatient.add(paitent);
		}
		
		JSONArray ja=new JSONArray();
		JSONObject jo=null;
		for(User user:listpatient){
			jo=new JSONObject();
			jo.put("mobile", user.getMobile());
			jo.put("img", user.getImg());
			jo.put("name", user.getUsername());
			jo.put("userid", user.getId());
			jo.put("outtime", user.getOuttime());
			jo.put("staytime", user.getStaytime());
			jo.put("patientcode", user.getPatientcode());
			ja.put(jo);
		}
		
		
		
		JSONObject resultObj = new JSONObject();
		resultObj.put("errorcode",0);
		resultObj.put("message", "病人获取成功");
		resultObj.put("listpatient", ja);
		responseData(resultObj.toString(),resp);		
	}	
	
	
	
	
	/**
	 * 搜索病人
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value="/searchpatient")
	public void searchpatient(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String str=getRequestBodyString(req);
		if(StringUtils.isBlank(str)){
			responseErrorData("JSON串为空",resp);
			return;
		}
		JSONObject json=new JSONObject(str);
		String params=json.optString("params");
		//获取病人列表
		List<User> listuser=userService.getListUserByParams(params);
		JSONArray ja=new JSONArray();
		JSONObject jo=null;
		for(User user:listuser){
			jo=new JSONObject();
			jo.put("mobile", user.getMobile());
			jo.put("img", user.getImg());
			jo.put("name", user.getUsername());
			jo.put("userid", user.getId());
			ja.put(jo);
		}
		
		
		JSONObject resultObj = new JSONObject();
		resultObj.put("errorcode",0);
		resultObj.put("message", "搜索病人成功");
		resultObj.put("listpatient", ja);
		responseData(resultObj.toString(),resp);		
	}	
	
	
	
	
	/**
	 * 病人上传病历
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value="/submitcasehistory")
	public void submitcasehistory(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String str=getRequestBodyString(req);
		if(StringUtils.isBlank(str)){
			responseErrorData("JSON串为空",resp);
			return;
		}
		JSONObject json=new JSONObject(str);
		String userid=json.optString("userid");
		String result = json.optString("image");   	
		String imagename ="";
		if(StringUtils.isNotBlank(result)){
			 	byte[] imgresult = Base64.decode(result);  
			 	imagename= RandomStringUtils.random(8, "123456789abcdefghmlopqrstuvwxyz");
				OutputStream out = new FileOutputStream("/mnt/seconddiagnoseapi/img/"+imagename+".png");  
				out.write(imgresult);  
				out.close();
		}   
		String img="";
		if(StringUtils.isNotBlank(imagename)){
			img="/img/"+imagename+".png";
		}
		userService.updateUserPaitentImgByUserId(img,userid);
		JSONObject resultObj = new JSONObject();
		resultObj.put("errorcode",0);
		resultObj.put("message", "病历上传成功");
		responseData(resultObj.toString(),resp);		
	}	
	
	
	/**
	 * 获取病人信息
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value="/getinformation")
	public void getinformation(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String str=getRequestBodyString(req);
		if(StringUtils.isBlank(str)){
			responseErrorData("JSON串为空",resp);
			return;
		}
		JSONObject json=new JSONObject(str);
		String userid=json.optString("userid");
		String token=json.optString("token");
		User user=userService.getUserByUserId(userid);
		if(!user.getToken().equals(token)){
			responseErrorData("token错误",resp);
			return;
		}
		JSONObject resultObj = new JSONObject();
		JSONObject jo = new JSONObject();
		jo.put("name",user.getUsername()+"");
		jo.put("userid", user.getId()+"");
		jo.put("usernumber", user.getUsernumber()+"");
		jo.put("patientcode", user.getPatientcode()+"");
		jo.put("outtime", user.getOuttime()+"");
		jo.put("staytime", user.getStaytime()+"");
		jo.put("bedcode", user.getBedcode()+"");
		jo.put("role", user.getRole()+"");
		if(StringUtils.isBlank(user.getImg())||user.getImg().equals("/img/.png")){
			if(user.getRole().equals("student")){
				user.setImg("/img/studentIcon.png");					
			}else{
				user.setImg("/img/teacherIcon.png");					
			}
		}
		jo.put("img", "http://110.76.38.32:8081"+user.getImg()+"");
		resultObj.put("errorcode",0);
		resultObj.put("message", "获取个人信息成功");
		resultObj.put("user", jo);
		responseData(resultObj.toString(),resp);			
	}
	
	
	
	/**
	 * 完善个人信息
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value="/setinformation")
	public void setinformation(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String str=getRequestBodyString(req);
		if(StringUtils.isBlank(str)){
			responseErrorData("JSON串为空",resp);
			return;
		}
		JSONObject json=new JSONObject(str);
		String userid=json.optString("userid");
		User olduser=userService.getUserByUserId(userid);
		String name=json.optString("name");
		String usernumber=json.optString("usernumber");
		String patientcode=json.optString("patientcode");
		String outtime=json.optString("outtime");
		String staytime=json.optString("staytime");
		String bedcode=json.optString("bedcode");
		String result = json.optString("image");   	
		String imagename ="";
		if(StringUtils.isNotBlank(result)){
			 	byte[] imgresult = Base64.decode(result);  
			 	imagename= RandomStringUtils.random(8, "123456789abcdefghmlopqrstuvwxyz");
				OutputStream out = new FileOutputStream("/mnt/seconddiagnoseapi/img/"+imagename+".png");  
				out.write(imgresult);  
				out.close();
		}   
		String img="";
		if(StringUtils.isNotBlank(imagename)){
			img="/img/"+imagename+".png";
		}else{
			img=olduser.getImg();
		}
		userService.updateUserByUserId(name,usernumber,patientcode,outtime,bedcode,img,userid,staytime);
		JSONObject resultObj = new JSONObject();
		resultObj.put("errorcode",0);
		resultObj.put("message", "设置成功");
		responseData(resultObj.toString(),resp);		
	}	
	/**
	 * 读取数据
	 * 
	 * @param req
	 * @return
	 */
	private String getRequestBodyString(HttpServletRequest req) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					req.getInputStream(), "utf-8"));
			String line;
			while ((line = br.readLine()) != null) {
				builder.append(line);
			}
		} catch (IOException e) {
			// e.printStackTrace();
			return null;
		}
		return builder.toString();
	}

	private void responseErrorData(String string, HttpServletResponse resp)
			throws Exception {
		// TODO Auto-generated method stub
		JSONObject resultObj = new JSONObject();
		resultObj.put("errorcode", 1);
		resultObj.put("message", string);
		responseData(resultObj.toString(), resp);
	}

	private void responseSuccData(String string, HttpServletResponse resp)
			throws Exception {
		// TODO Auto-generated method stub
		JSONObject resultObj = new JSONObject();
		resultObj.put("errorcode", 0);
		resultObj.put("message", string);
		responseData(resultObj.toString(), resp);
	}

	private void responseData(String string, HttpServletResponse resp)
			throws Exception {
		// TODO Auto-generated method stub
		responseDate("application/json;charset=UTF-8", string, resp);

	}

	private void responseDate(String contentType, String value,
			HttpServletResponse resp) throws Exception {
		resp.setContentType(contentType);
		resp.getWriter().write(value);
	}
}
