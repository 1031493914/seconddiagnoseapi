package com.sd.mobileapi.service.imp;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.sd.mobileapi.service.LoginService;
@Service
public class LoginServiceImp extends BaseServiceImp implements LoginService {

	@Override
	public String getUseridByMobile(String mobile) {
		// TODO Auto-generated method stub
		String sql="select id from user where mobile= ? limit 1";
		Object[] params = {mobile};
		return queryString(sql, params);
	}

	@Override
	public void addSecretByMobile(String newuserid, String secret) {
		// TODO Auto-generated method stub
		String sql = "insert into mobilesecret(userid,secret) values(?,?)";
		Object[] params = {newuserid,secret};
		update(sql, params);
	}

	@Override
	public void updateSecretByMobile(String userid, String secret) {
		// TODO Auto-generated method stub
		String sql="update mobilesecret set userid=? ,secret=?";
		Object[] params = {userid,secret};
		update(sql, params);
	}

}
