package com.sd.mobileapi.service;


import java.util.List;

import com.sd.mobileapi.model.DoctorPaitent;
import com.sd.mobileapi.model.User;

public interface UserService {

	String getUserIdByLoginMail(String mail);

	long insertUser(String nickname, String mail, String loginpwd,
			String mobile, String imagename);

	User getUserByLoginMail(String mobile);

	void insertMessageByUserId(String userid, String message);

	void insertLogByShopId(String shopid, String shoplog);

	long insertNewUser(String mobile, String role, String token);

	User getUserByUserId(String userid);

	String getSecretByUserid(String userid);

	void updateValid(String string, String userid, String accid, String token);

	void updatePassword(String password, String userid);

	void updateUserByUserId(String name, String usernumber, String patientcode,
			String outtime, String bedcode, String img, String userid, String staytime);

	void updateUserPaitentImgByUserId(String img, String userid);

	void updateDutyDoctor(int id);

	void updateDutyNurse(int id);

	String getDutyDoctor();

	String getDutyNurse();

	List<User> getListUserByParams(String params);

	List<String> getListDoctorPaitentByParams(String userid);

	void insertDoctorPaitent(String userid, String patientid);

	String getDoctorPaitent(String userid, String patientid);


	
}
