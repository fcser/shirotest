package com.dayang.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.myhexin.service.ShiroService;

@Controller
public class ShiroContraller {

	@Autowired
	private ShiroService shiroService;
	
	@RequestMapping("/gologin.html")
	public String goLogin() {
		return "/login.jsp";
	}
	
	@RequestMapping("/login.html")
	public ModelAndView login(String username,String password) {
		System.out.println("username:"+username+"   password:"+password);
		try {
			shiroService.doLogin(username, password);
		}catch(Exception e) {
			return new ModelAndView("/error.jsp","msg",e.getMessage());
		}
		return new ModelAndView("/index.jsp");
	}
	
	@RequestMapping("/logout.html")
	public String logout() {
		Subject currentUser=SecurityUtils.getSubject();
		currentUser.logout();
		return "/login.jsp";
	}
	//PathVariable获取请求中的动态参数
	@RequestMapping("/do{act}.html")
	public ModelAndView get(@PathVariable String act) {
		System.out.println("requestmapping"+act);
		return new ModelAndView("/page.jsp","page",act);
	}
}
