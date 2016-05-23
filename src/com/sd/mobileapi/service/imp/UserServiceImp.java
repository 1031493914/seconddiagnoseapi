package com.sd.mobileapi.service.imp;


import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sd.mobileapi.model.DoctorPaitent;
import com.sd.mobileapi.model.DoctorPaitentRowMapper;
import com.sd.mobileapi.model.User;
import com.sd.mobileapi.model.UserRowMapper;
import com.sd.mobileapi.service.UserService;


@Service
public class UserServiceImp extends BaseServiceImp implements
		UserService{

	@Override
	public String getUserIdByLoginMail(String mail) {
		// TODO Auto-generated method stub
		String sql="select id from user where mail= ? limit 1";
		Object[] params = {mail};
		return queryString(sql, params);
	}

	@Override
	public long insertUser(String nickname, String mail, String loginpwd,
			String mobile, String imagename) {
		// TODO Auto-generated method stub
		Date date=new Date();
		String sql = "insert into user(name,mail,mobile,image,pwd,time) values(?,?,?,?,?,?)";
		Object[] params = {nickname,mail,mobile,imagename,loginpwd,date};
		return saveAndGetId(sql, params);
	}

	@Override
	public User getUserByLoginMail(String mobile) {
		// TODO Auto-generated method stub
		String sql = "select * from user where mobile = ? limit 1";
		return getEntry(sql, new UserRowMapper(), new Object[]{mobile});
	}

	@Override
	public void insertMessageByUserId(String userid, String message) {
		// TODO Auto-generated method stub
		Date date=new Date();
		String sql = "insert into comment(userid,message,time) values(?,?,?)";
		Object[] params = {userid,message,date};
		update(sql, params);
	}

	@Override
	public void insertLogByShopId(String shopid, String shoplog) {
		// TODO Auto-generated method stub
		Date date=new Date();
		String sql = "insert into shoplog(shopid,shoplog,time) values(?,?,?)";
		Object[] params = {shopid,shoplog,date};
		update(sql, params);
	}

	@Override
	public long insertNewUser(String mobile, String role,String token) {
		// TODO Auto-generated method stub
		Date date=new Date();
		String sql = "insert into user(mobile,role,token,time,status,username) values(?,?,?,?,?,?)";
		Object[] params = {mobile,role,token,date,"0",mobile};
		return saveAndGetId(sql, params);
	}

	@Override
	public User getUserByUserId(String userid) {
		// TODO Auto-generated method stub
		String sql = "select * from user where id = ? limit 1";
		return getEntry(sql, new UserRowMapper(), new Object[]{userid});
	}

	@Override
	public String getSecretByUserid(String userid) {
		// TODO Auto-generated method stub
		String sql="select secret from mobilesecret where userid= ? limit 1";
		Object[] params = {userid};
		return queryString(sql, params);
	}

	@Override
	public void updateValid(String string, String userid, String accid,String token) {
		// TODO Auto-generated method stub
		String sql="update user set status=?,accid=?,duty=?,token=? where id=?";
		Object[] params = {string,accid,"0",token,userid};
		update(sql, params);
	}

	@Override
	public void updatePassword(String password, String userid) {
		// TODO Auto-generated method stub
		String sql="update user set pwd=? where id=?";
		Object[] params = {password,userid};
		update(sql, params);
	}

	@Override
	public void updateUserByUserId(String name, String usernumber,
			String patientcode, String outtime, String bedcode, String img,
			String userid,String staytime) {
		// TODO Auto-generated method stub
		String sql="update user set username=?,usernumber=?,patientcode=?,outtime=?,bedcode=?,img=?,staytime=? where id=?";
		Object[] params = {name,usernumber,patientcode,outtime,bedcode,img,staytime,userid};
		update(sql, params);
	}

	@Override
	public void updateUserPaitentImgByUserId(String img, String userid) {
		// TODO Auto-generated method stub
		Date date=new Date();
		String sql = "insert into patientimg(userid,img,time) values(?,?,?)";
		Object[] params = {userid,img,date};
		update(sql, params);
	}

	@Override
	public void updateDutyDoctor(int id) {
		// TODO Auto-generated method stub
		String sql="update duty set userid=? where type=?";
		Object[] params = {id,"doctor"};
		update(sql, params);
	}

	@Override
	public void updateDutyNurse(int id) {
		// TODO Auto-generated method stub
		String sql="update duty set userid=? where type=?";
		Object[] params = {id,"nurse"};
		update(sql, params);
	}

	@Override
	public String getDutyDoctor() {
		// TODO Auto-generated method stub
		String sql="select userid from duty where type= ? ";
		Object[] params = {"doctor"};
		return queryString(sql, params);
	}

	@Override
	public String getDutyNurse() {
		// TODO Auto-generated method stub
		String sql="select userid from duty where type= ? ";
		Object[] params = {"nurse"};
		return queryString(sql, params);
	}

	@Override
	public List<User> getListUserByParams(String type) {
		// TODO Auto-generated method stub
		String sql="select * from user where username like ? or patientcode like ? or bedcode like ? and role=2";
		Object[] params = {"%"+type+"%","%"+type+"%","%"+type+"%"};
		return getEntryList(sql, new UserRowMapper(), params);
	}

	@Override
	public List<String> getListDoctorPaitentByParams(String userid) {
		// TODO Auto-generated method stub
		String sql="select paitentid from doctorpaitent where doctorid= ? ";
		Object[] params = {userid};
		return queryStringArray(sql, params);
	}

	@Override
	public void insertDoctorPaitent(String userid, String patientid) {
		// TODO Auto-generated method stub
		String sql = "insert into doctorpaitent(paitentid,doctorid) values(?,?)";
		Object[] params = {patientid,userid};
		update(sql, params);
	}

	@Override
	public String getDoctorPaitent(String userid, String patientid) {
		// TODO Auto-generated method stub
		String sql="select id from doctorpaitent where paitentid= ? and  doctorid=? limit 1";
		Object[] params = {patientid,userid};
		return queryString(sql, params);
	}


	
	


}
