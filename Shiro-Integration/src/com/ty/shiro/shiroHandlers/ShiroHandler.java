package com.ty.shiro.shiroHandlers;

import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ty.shiro.service.ShiroService;
@Controller
@RequestMapping("/shiro")
public class ShiroHandler {
	private final static Logger LOG = LogManager.getLogger(ShiroHandler.class);
	//不做登出，就会出现缓存
//	@RequestMapping("LoginOut")
//	public String Out(){
//		return null;
//	}
	
	@Autowired
	private ShiroService shiroService;
	
	@RequestMapping("/testShiroAnnotation")
	public String TestService(HttpSession session){
		System.out.println("进入测试页面");
		session.setAttribute("key", "123456");
		shiroService.OutPut();
		return "redirect:/success.jsp";
	}
	
	@RequestMapping("/login")
	public String login(@RequestParam("UserName")String UserName,
			@RequestParam("PassWord")String PassWord){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
				//3...如果没有认证把用户名和密码封装为token对象，参数：用户名和密码
				UsernamePasswordToken token = new UsernamePasswordToken(UserName, PassWord);
				token.setRememberMe(true);
				try {
					System.out.println("1:"+token.hashCode());
					// 4...获取当前的登录的token
					currentUser.login(token);
				}
				//所有认证异常的父类
				catch (AuthenticationException  ae) {
					System.out.println("登录失败:" + ae.getMessage());
				}
		}
		return "redirect:/success.jsp";
	}
}
