package com.zym.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroMysqlTest {
	private static final transient Logger log =
	LoggerFactory.getLogger(ShiroMysqlTest.class);
	public static void main(String[] args) {
		Factory<SecurityManager> factory =new IniSecurityManagerFactory("classpath:shiro-mysql.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token =new UsernamePasswordToken("test@shiro.com", "123456");
		token.setRememberMe(true);//是否记住用户
		try {
			currentUser.login(token);
			//当我们获登录用户之后
			log.info("用户 [" + currentUser.getPrincipal() + "] 登陆成功");
			//查看用户是否有角色
			if (currentUser.hasRole("admin")) {
					log.info("您有admin角色");
			} else {
				log.info("您没有admin角色");
			}
			if (currentUser.hasRole("test")) {
				log.info("您有test角色");
			} else {
				log.info("您没有test角色");
			}
			// 查看用户是否有某个权限
			if (currentUser.isPermitted("perm1")) {
				log.info("您有perm1权限");
			} else {
				log.info("您没有perm1权限");
			}
			if (currentUser.isPermitted("guest")) {
				log.info("您有guest权限");
			} else {
				log.info("您没有guest权限");
			}
			//登出
			currentUser.logout();
		} catch (UnknownAccountException uae) {
			log.info(token.getPrincipal() + "账户不存在");
		} catch (IncorrectCredentialsException ice) {
			log.info(token.getPrincipal() + "密码不正确");
		} catch (LockedAccountException lae) {
			log.info(token.getPrincipal() + "用户被锁定了 ");
		} catch (AuthenticationException ae) {
			//无法判断是什么错了
			log.info(ae.getMessage());
		}
	}
}

