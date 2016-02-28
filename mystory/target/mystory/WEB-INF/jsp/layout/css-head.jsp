<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
<c:when test="${ serverStatus eq 'dev' }">
	<link rel="stylesheet/less" type="text/css" href="${pageContext.request.contextPath}/resources/less/coixaygio-bootstrap.less" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/libs/less-1.4.1.min.js"></script>
</c:when>
<c:otherwise>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/coixaygio-bootstrap.css" />
</c:otherwise>
</c:choose>