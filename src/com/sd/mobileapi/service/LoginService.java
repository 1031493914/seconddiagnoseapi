package com.sd.mobileapi.service;


import com.sd.mobileapi.model.User;

public interface LoginService {

	String getUseridByMobile(String mobile);

	void addSecretByMobile(String string, String secret);

	void updateSecretByMobile(String mobile, String secret);


	
}
