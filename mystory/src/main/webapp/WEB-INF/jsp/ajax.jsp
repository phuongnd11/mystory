<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="msapplication-TileColor" content="#ffffff">
<meta name="msapplication-TileImage" content="/resources/imgs/favicon/ms-icon-144x144.png">
<meta name="theme-color" content="#ffffff">
<meta name="description" content="">
<meta name="author" content="">
<title>Chuyentrolinhtinh.vn</title>

<jsp:include page="css-ajax.jsp" />
<jsp:include page="layout/js-head.jsp" />
</head>
<body>
    
	<tiles:insertAttribute name="navbar" />
	<tiles:insertAttribute name="submenu" />
	<tiles:insertAttribute name="banner" />
	<tiles:insertAttribute name="body" />
    <tiles:insertAttribute name="footer" />
</body>
</html>
