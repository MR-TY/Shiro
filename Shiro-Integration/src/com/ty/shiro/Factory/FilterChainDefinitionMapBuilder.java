package com.ty.shiro.Factory;
import java.util.LinkedHashMap;
/**
 * 
* @Description: 利用工厂模式设置用户的权限
* @ClassName: FilterChainDefinitionMapBuilder.java
* @version: v1.0.0
* @author: 
* @date: 2018年4月23日 下午8:53:29
 */
public class FilterChainDefinitionMapBuilder {
	public LinkedHashMap<String, String> buildFilterChainDefinitionMap(){
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("/login.jsp", "anon");
		map.put("/shiro/login", "anon");
		map.put("/shiro/LoginOut", "logout");
		map.put("/user.jsp", "authc,roles[user]");//需要认证之后，并且只能是user这个权限才能登录
		map.put("/admin.jsp", "authc,roles[admin]");//需要认证之后，并且只能是admin这个权限才能登录
		map.put("/success.jsp", "user");//不需要认证的，当退出页面，在进入这个页面只要cookie还存在，就不用在进行验证
		map.put("/**", "authc");
		return map;
	}
}

