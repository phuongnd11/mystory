<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>


<c:choose>
<c:when test="${ serverStatus eq 'dev' }">
	<!-- LIBRARIES -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/libs/jquery-1.10.2-min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/libs/jquery-ui-1.10.3.custom.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/libs/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/libs/mustache.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/libs/mudim-0.9-r162.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/libs/jquery.loadmask.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/libs/jquery-validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/libs/jquery-validate/additional-methods.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/libs/sceditor/jquery.sceditor.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/libs/sceditor/bbcode.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/libs/sceditor/languages/vi.js"></script>
	
	
	<!-- File Uploads -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/libs/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/libs/jquery.iframe-transport.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/libs/jquery.fileupload.js"></script>
	
	
	<!-- MYS functionalities. Features are pushed into MYS global object in mys-core.js to avoid polluting global namespace -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mys-core.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mys-i18n.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mys-voting.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mys-modals.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mys-endless-scrolling.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mys-userprofile.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mys-editor.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mys-validation.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mys-upload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mys-story.js"></script>
	
	
	<%-- Navbar sort 
	<sec:authorize access="isAuthenticated()">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mys-categoryList.js"></script>
	</sec:authorize>
	--%>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mys-config.js"></script>
</c:when>

<c:otherwise>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/compressed/mys.min.js"></script>
</c:otherwise>
</c:choose>

<!-- Google Analytic --> 
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-45369983-1', 'mystory.vn');
  ga('send', 'pageview');
</script>

<!-- MYS Templates -->
<jsp:include page="../templates/storyTemplate.jsp" />
<jsp:include page="../templates/messageListTemplate.jsp" />
<jsp:include page="../templates/messageDetailTemplate.jsp" />
<jsp:include page="../templates/messageComposeTemplate.jsp" />