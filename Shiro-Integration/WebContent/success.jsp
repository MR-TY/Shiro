<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	Welcome:<shiro:principal></shiro:principal>
	
	<shiro:hasRole name="admin">
		<br><br>
		<a href="admin.jsp">admin page</a>
	</shiro:hasRole>
	
	<shiro:hasRole name="user">
		<br><br>
		<a href="user.jsp">user page</a>
	</shiro:hasRole>
	
	<br><br>
	<a href="/shiro/LoginOut">退出账号</a>
	
	<br><br>
	<a href="shiro/testShiroAnnotation">Test ShiroAnnotation</a>
</body>
</html>