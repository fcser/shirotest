package com.dayang.service;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import cn.myhexin.dao.ShiroDAO;

public class ShiroService {

	private ShiroDAO shiroDAO;
	
	public void setShiroDAO(ShiroDAO shiroDAO) {
		this.shiroDAO=shiroDAO;
	}
	
	public void doLogin(String username,String password) throws Exception {
		Subject currentUser=SecurityUtils.getSubject();
		if(!currentUser.isAuthenticated()) {
			UsernamePasswordToken token=new UsernamePasswordToken(username,password);
			token.setRememberMe(true);
			try {
				currentUser.login(token);
			}catch (UnknownAccountException uae) {
				System.out.println("账户不存在");
				throw new Exception( "账户不存在");
			} catch (IncorrectCredentialsException ice) {
				System.out.println("密码不正确");
				throw new Exception( "密码不正确");
			} catch (LockedAccountException lae) {
				System.out.println("用户被锁定了");
				throw new Exception( "用户被锁定了 ");
			} catch (AuthenticationException ae) {
				//无法判断是什么错了
				System.out.println("未知错误");
				throw new Exception( "未知错误");
			}
		}
	}
	
	public String getPasswordByUserName(String username) {
		return shiroDAO.getPasswordByUserName(username);
	}
	
	public List<String> getPermissionByUserName(String username){
		return shiroDAO.getPermisionByUserName(username);
	}
}
