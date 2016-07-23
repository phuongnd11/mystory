<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="apple-touch-icon" sizes="57x57" href="/resources/imgs/favicon/apple-icon-57x57.png">
<link rel="apple-touch-icon" sizes="60x60" href="/resources/imgs/favicon/apple-icon-60x60.png">
<link rel="apple-touch-icon" sizes="72x72" href="/resources/imgs/favicon/apple-icon-72x72.png">
<link rel="apple-touch-icon" sizes="76x76" href="/resources/imgs/favicon/apple-icon-76x76.png">
<link rel="apple-touch-icon" sizes="114x114" href="/resources/imgs/favicon/apple-icon-114x114.png">
<link rel="apple-touch-icon" sizes="120x120" href="/resources/imgs/favicon/apple-icon-120x120.png">
<link rel="apple-touch-icon" sizes="144x144" href="/resources/imgs/favicon/apple-icon-144x144.png">
<link rel="apple-touch-icon" sizes="152x152" href="/resources/imgs/favicon/apple-icon-152x152.png">
<link rel="apple-touch-icon" sizes="180x180" href="/resources/imgs/favicon/apple-icon-180x180.png">
<link rel="icon" type="image/png" sizes="192x192"  href="/resources/imgs/favicon/android-icon-192x192.png">
<link rel="icon" type="image/png" sizes="32x32" href="/resources/imgs/favicon/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="96x96" href="/resources/imgs/favicon/favicon-96x96.png">
<link rel="icon" type="image/png" sizes="16x16" href="/resources/imgs/favicon/favicon-16x16.png">
<link rel="manifest" href="/resources/imgs/favicon/manifest.json">
<meta name="msapplication-TileColor" content="#ffffff">
<meta name="msapplication-TileImage" content="/resources/imgs/favicon/ms-icon-144x144.png">
<meta name="theme-color" content="#ffffff">
<meta name="description" content="">
<meta name="author" content="">
<title>Chuyentrolinhtinh.vn</title>

<script type="text/javascript">
    function crunchifyAjax() {
        $.ajax({
            url : 'ajaxtest',
            success : function(data) {
            }
        });
    }
</script>

<jsp:include page="layout/css-head.jsp" />
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
