<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
</head>
<body>
	<h1>Welcome,  <c:out value="${loginUser.userName}"/></h1>
	<p>This is your dashboard. Nothing to see here yet.</p>
	<a href="/users/login/register">Log out</a>
</body>
</html>