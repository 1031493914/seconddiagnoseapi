package com.sd.mobileapi.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MobileSecretRowMapper implements RowMapper<MobileSecret>{

	@Override
	public MobileSecret mapRow(ResultSet rs, int rowNum) throws SQLException {
		MobileSecret mobilesecret=new MobileSecret();
		mobilesecret.setId(rs.getInt("id"));
		mobilesecret.setUserid(rs.getString("userid"));
		mobilesecret.setSecret(rs.getString("secret"));
		return mobilesecret;
	}

}
