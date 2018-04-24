package com.ty.shiro.service;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;
public class ShiroService {
	private final static Logger LOG = LogManager.getLogger(ShiroService.class);
	
	@RequiresRoles(value={"admin"})//只允许admin这个用户进行访问
	public void OutPut(){
		Session session = SecurityUtils.getSubject().getSession();//得到session
		Object object = session.getAttribute("key");
		System.out.println("service的输出:"+object);
	}
}
