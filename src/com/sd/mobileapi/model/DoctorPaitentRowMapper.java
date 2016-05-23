package com.sd.mobileapi.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DoctorPaitentRowMapper implements RowMapper<DoctorPaitent>{

	@Override
	public DoctorPaitent mapRow(ResultSet rs, int rowNum) throws SQLException {
		DoctorPaitent doctorpaitent = new DoctorPaitent();
		doctorpaitent.setId(rs.getInt("id"));
		doctorpaitent.setDoctorid(rs.getString("doctorid"));
		doctorpaitent.setPaitentid(rs.getString("paitentid"));
		return doctorpaitent;
	}

}
