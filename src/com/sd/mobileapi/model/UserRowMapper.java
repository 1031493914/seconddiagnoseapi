package com.sd.mobileapi.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setMobile(rs.getString("mobile"));
		user.setUsername(rs.getString("username"));
		user.setPwd(rs.getString("pwd"));
		user.setRole(rs.getString("role"));
		user.setImg(rs.getString("img"));
		user.setToken(rs.getString("token"));
		user.setCreatetime(rs.getTimestamp("time"));
		user.setAccid(rs.getString("accid"));
		user.setUsernumber(rs.getString("usernumber"));
		user.setPatientcode(rs.getString("patientcode"));
		user.setOuttime(rs.getString("outtime"));
		user.setBedcode(rs.getString("bedcode"));
		user.setDuty(rs.getString("duty"));
		user.setStaytime(rs.getString("staytime"));
		return user;
	}

}
