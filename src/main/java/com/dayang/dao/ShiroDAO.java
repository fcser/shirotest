package com.dayang.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class ShiroDAO {

	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
	}
	
	public String getPasswordByUserName(String username) {
		String sql="select password from shiro_user where user_name=?";
		System.out.println("SQL1:"+sql);
		String password="123456";
		//String password=jdbcTemplate.queryForObject(sql, String.class,username);
		System.out.println("next");
		return password;
	}
	
	public List<String> getPermisionByUserName(String username){
		String sql="select p.perm_name from shiro_role_permission p inner join shiro_user_role r on p.role_name=r.role_name where r.user_name=?";
		List<String> perms=jdbcTemplate.queryForList(sql, String.class, username);
		return perms;
	}
}
