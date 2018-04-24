<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录成功的页面</title>
</head>
<body>


	<form action="shiro/login" method="POST">
		UserName:<input type="text" name="UserName"><br>
		PassWord:<input type="text" name="PassWord"><br>
		<input type="submit" value="Submit"><br>
	</form>
	
</body>
</html>