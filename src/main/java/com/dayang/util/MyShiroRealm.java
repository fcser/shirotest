package com.dayang.util;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.myhexin.service.ShiroService;

public class MyShiroRealm extends AuthorizingRealm {

	private ShiroService shiroService;


	public void setShiroService(ShiroService shiroService) {
		this.shiroService = shiroService;
	}
	
	/**
	 * 获取当前用户权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		String username=(String)principals.fromRealm(getName()).iterator().next();
		if(username!=null) {
			List<String> perms=shiroService.getPermissionByUserName(username);
			if(perms!=null&&!perms.isEmpty()) {
				SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
				for(String each:perms) {
					info.addStringPermission(each);
				}
				return info;
			}
		}
		return null;
	}
	/**
	 * 查询是否此用户
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken atoken) throws AuthenticationException {
		// TODO Auto-generated method stub
		UsernamePasswordToken token=(UsernamePasswordToken)atoken;
		String username=token.getUsername();
		System.out.println("come in username:"+username);
		if(username!=null&&!"".equals(username)) {
			String password=shiroService.getPasswordByUserName(username);
			System.out.println("password:"+password);
			if(password!=null) {
				return new SimpleAuthenticationInfo(username,password,getName());
			}
		}
		return null;
	}

}
