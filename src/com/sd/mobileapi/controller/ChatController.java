package com.sd.mobileapi.controller;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class ChatController {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * 与值班医生聊天
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value="/onlinedoctor")
	public void onlinedoctor(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String str=getRequestBodyString(req);
		if(StringUtils.isBlank(str)){
			responseErrorData("JSON串为空",resp);
			return;
		}
		JSONObject json=new JSONObject(str);
		String userid=json.optString("userid");
		//获取值班医生
		String doctorid=userService.getDutyDoctor();
		User paitentuser=userService.getUserByUserId(userid);
		User doctoruser=userService.getUserByUserId(doctorid);
		
		JSONObject resultObj = new JSONObject();
		resultObj.put("errorcode",0);
		resultObj.put("message", "获取成功");
		resultObj.put("useraccid", paitentuser.getAccid());
		resultObj.put("doctoraccid", doctoruser.getAccid());
		responseData(resultObj.toString(),resp);		
	}	
	
	
	
	/**
	 * 与值班护理聊天
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value="/onlinenurse")
	public void onlinenurse(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String str=getRequestBodyString(req);
		if(StringUtils.isBlank(str)){
			responseErrorData("JSON串为空",resp);
			return;
		}
		JSONObject json=new JSONObject(str);
		String userid=json.optString("userid");
		//获取护理
		String nurseid=userService.getDutyNurse();
		User paitentuser=userService.getUserByUserId(userid);
		User nurseuser=userService.getUserByUserId(nurseid);
		
		JSONObject resultObj = new JSONObject();
		resultObj.put("errorcode",0);
		resultObj.put("message", "获取成功");
		resultObj.put("useraccid", paitentuser.getAccid());
		resultObj.put("nurseaccid", nurseuser.getAccid());
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
