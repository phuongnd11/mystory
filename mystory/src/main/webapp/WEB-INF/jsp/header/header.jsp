<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<div id="fb-root"></div>
<script type="text/javascript">
(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

// I know, right?
var _kmq = _kmq || [];
var _kmk = _kmk || '3ca035a814c98242ff1952165d0add60e6c19fc7';
function _kms(u){
  setTimeout(function(){
    var d = document, f = d.getElementsByTagName('script')[0],
    s = d.createElement('script');
    s.type = 'text/javascript'; s.async = true; s.src = u;
    f.parentNode.insertBefore(s, f);
  }, 1);
}
_kms('//i.kissmetrics.com/i.js');
_kms('//doug1izaerwt3.cloudfront.net/' + _kmk + '.1.js');
</script>

<header id="header" class="header">
	<div class="header-inner container">
		<a href="<c:url value='/home'/>" class="mystory_logo">
			<img src="<c:url value='/resources/imgs/logo.png' />" alt="MyStory"/>
		</a>
		<div class="header_user pull-right">
			<sec:authorize access="isAuthenticated()" var="isAuth">
				<jsp:include page="/WEB-INF/jsp/header/components/userPanel.jsp" />
			</sec:authorize>
			<c:if test="${!isAuth}">
				<jsp:include page="/WEB-INF/jsp/header/components/loginPanel.jsp" />
			</c:if>
		</div>
		<div class="header_nav">
			<c:if test="${ not empty sortTypes}">
				<ul id="topic_type_list" class="top_tab">
					<c:forEach items="${ sortTypes }" var="sortType">
						<li <c:if test="${sortType.selected}"> class="selected"</c:if> ><a href="<c:url value='${ sortType.url }' />" id="sort_${ sortType.name }">${ sortType.displayName }</a></li>
					</c:forEach>
				</ul>
			</c:if>
		</div>
	</div>
</header>