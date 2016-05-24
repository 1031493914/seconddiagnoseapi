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
import com.sd.mobileapi.util.Sendsms;

/**
 * 登陆和注册接口
 * 
 * @author jx
 */
@Controller
public class LoginRegisterController {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private LoginService loginService;

	@Autowired
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	@RequestMapping(value = "/getLogin")
	public void getLogin(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		String str = getRequestBodyString(req);
		if (StringUtils.isBlank(str)) {
			responseErrorData("JSON串为空", resp);
			return;
		}
		system.out.println("testgithub");
		JSONObject json = new JSONObject(str);
		String loginname = json.optString("username");
		String loginpwd = json.optString("userpwd");
		User user = userService.getUserByLoginMail(loginname);
		if (user == null) {
			responseErrorData("用户名不正确", resp);
			return;
		}

		if (user.getPwd().equals(loginpwd)) {
			// 设置值班医生
			// 0代表医生 1代表护理 2代表病人
			if (user.getRole().equals("0")&&user.getDuty().equals("1")) {
					
				userService.updateDutyDoctor(user.getId());
			}
			if (user.getRole().equals("1")&&user.getDuty().equals("1")) {
				userService.updateDutyNurse(user.getId());
			}
			JSONObject resultObj = new JSONObject();
			resultObj.put("errorcode", 0);
			resultObj.put("message", "登陆成功");
			resultObj.put("userid", user.getId());
			resultObj.put("accid", user.getAccid());
			resultObj.put("role", user.getRole());
			resultObj.put("token", user.getToken());
			resultObj.put("name", user.getMobile());
			responseData(resultObj.toString(), resp);

		} else {
			responseErrorData("密码不正确", resp);
			return;
		}
	}

	/**
	 * 获取验证码接口
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSecret")
	public void getSecret(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		String post = getRequestBodyString(req);
		if (StringUtils.isBlank(post)) {
			responseErrorData("json串错误", resp);
			return;
		}
		System.out.println("testgithub");
		JSONObject json = new JSONObject(post);
		// 手机号
		String mobile = json.getString("mobile");
		// 角色 2代表病人
		String role = json.getString("role");

		if (StringUtils.isBlank(mobile)) {
			responseErrorData("请输入要绑定的手机号码", resp);
			return;
		}
		// 根据手机号查看数据库中是否存在这个用户
		String userid = loginService.getUseridByMobile(mobile);
		String secret = "";
		if (StringUtils.isBlank(userid)) {
			// 插入新用户
			userid = userService.insertNewUser(mobile, role, "") + "";
			secret = RandomStringUtils.random(6, "123456789");
			loginService.addSecretByMobile(userid, secret);
		} else {
//			secret = RandomStringUtils.random(6, "123456789");
//			loginService.updateSecretByMobile(userid, secret);
			responseErrorData("该手机号已经被注册", resp);
			return;
		}
		Sendsms.sendSms(mobile, secret);
		JSONObject resultObj = new JSONObject();
		resultObj.put("errorcode", 0);
		resultObj.put("userid", userid);
		resultObj.put("message", "验证码发送成功");
		responseData(resultObj.toString(), resp);
	}

	/**
	 * 验证手机验证码
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value = "/checksecret")
	public void checksecret(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		String str = getRequestBodyString(req);
		if (StringUtils.isBlank(str)) {
			responseErrorData("json串错误", resp);
			return;
		}
		JSONObject json = new JSONObject(str);
		String userid = json.getString("userid");
		String secret = json.optString("secret");
		String mobile = json.optString("mobile");
		User user = userService.getUserByUserId(userid);
		String usersecret = userService.getSecretByUserid(userid);
		if (secret.equals(usersecret)) {
			// 创建accid，并赋值
			JSONObject jsonObjSplit = new JSONObject(NeteaseUtil.SetAccid("setnewaccid" + user.getId(),mobile));
			String token="";
			try{
				JSONObject jsonob=(JSONObject) jsonObjSplit.get("info");
				token=jsonob.getString("token");
				System.out.println("token--"+token);
			}catch(Exception e){
				
			}
			
			
			// 验证的账户变为正式账户，并赋值accid
			userService.updateValid("1", userid, "setaccid" + user.getId(),token);

		} else {
			responseErrorData("验证码不正确", resp);
			return;
		}
		JSONObject resultObj = new JSONObject();
		resultObj.put("errorcode", 0);
		resultObj.put("userid", userid);
		resultObj.put("message", "验证码验证成功");
		responseData(resultObj.toString(), resp);
	}

	/**
	 * 设置密码功能
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value = "/setPassword")
	public void setPassword(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		String str = getRequestBodyString(req);
		if (StringUtils.isBlank(str)) {
			responseErrorData("json串错误", resp);
			return;
		}
		JSONObject json = new JSONObject(str);
		String userid = json.getString("userid");
		String password = json.getString("password");
		
		User user = userService.getUserByUserId(userid);

		
		// 设置新的密码
		userService.updatePassword(password, userid);
		JSONObject resultObj = new JSONObject();
		resultObj.put("errorcode", 0);
		resultObj.put("userid", userid);

		resultObj.put("message", "密码设置成功");
		responseData(resultObj.toString(), resp);
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
