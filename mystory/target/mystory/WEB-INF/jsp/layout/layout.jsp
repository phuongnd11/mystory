<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<!DOCTYPE html>

<!--
																		                    .@@@@@@@@@@@@                   
																		                  @@@`  @@@@@@@@@@@                 
																		                 @@ `    @@@@@@@@@@@                
																		               @@  @@@@`.` @`@@@@@@@@@              
																		              @`@@@@@@@@@@@@@@ @@@@@@@@@            
																		             @`@@@@@ @@@@@@@@@@ @@@@@@@@@           
																		            @@@@@@@   @@@@@@@@@@ @@@@@@@@@          
																		           @@@@@@      @@@@@@@@@@@@@@@@@@@          
	 __   __  _______  ___   _______  _______  _______  ______    __   __          @@@@@        @@@@@@@@@@@@@@@@@@`         
	|  |_|  ||   _   ||   | |       ||       ||       ||    _ |  |  | |  |        @@@@`          `@ `@@@@@@@@@@@@@@         
	|       ||  |_|  ||   | |  _____||_     _||   _   ||   | ||  |  |_|  |       @@@@              `  @@@@@@@@@@@@@         
	|       ||       ||   | | |_____   |   |  |  | |  ||   |_||_ |       |       @@                    @@@@@@@@@@@@         
	|       ||       ||   | |_____  |  |   |  |  |_|  ||    __  ||_     _|      @@@       `             @@@@@@@@@@@@        
	| ||_|| ||   _   ||   |  _____| |  |   |  |       ||   |  | |  |   |         @       @@       @      @@@@@@@@@@@        
	|_|   |_||__| |__||___| |_______|  |___|  |_______||___|  |_|  |___|         @       @`      `@       @@@@@@@@@@        
																		        @@       @       @@        @@@@@@@@@        
																		        @.               .         `@@@@@`@@@       
																		        @`                          `@@@  `@@       
																		        @.                           @@   `@@       
																		        .@                           @@   @@@       
																		         @         ``                @ `@@@@@       
																		         @        `@@@@@@@             @@@@@@       
																		         @@              `@            @@@@@@       
																		          @.    @        @@           @@@@@@@       
																		         @@@@    @.      @           .@@@@@@@       
																		        @ @@@@@   @@@@@@@          `@@@@@@@@@       
																		        @ @@@ @@@@`  ..`       `@@@@@@@@@@@@@       
																		        @ @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@       
																		        @ @@@@@@@@@ @         `@@@@@@@@@@@@@`       
																		       @@ @@@@@@@@@ @`       .@  @@@@@@@@@@@        
																		      @@ @@@@@@@@@  `@`  `@@,   `@@@@@@@@@@@        
																		     `@  @ @@@@@@@    @@@@      ``@@@@@@@@@@        
																		     @@  @` @.@@@               ` @@@@@@@@@         
																		     @.  ,@  @@@@               ``@@@@@@@@@         
																		     ,@`  .  @@@`                `@@@@@@@@          
																		      ,@@@@   @@                 ` @@@@@`           
																		         @@   `@                 ` @@@              
																		          @`  @@                 ` @`               
																		           @  @`                 ``@@               
																		           @@ @                  ``.@               
																		            @ @                  ```@               
																		            @@@                  `` @               
																		             @@                  `` @               
																		              @                  ```@               
																		              @                  ```@               
																		              @                  ```@               
																		              @                  ```@               
																		              `                  `` @           
 -->
 
<html lang="<spring:message code='lang.current'/>">

	<spring:eval expression="@applicationProperties.getProperty('environment')" var="serverStatus" scope="application"/>
	<spring:eval expression="@applicationProperties.getProperty('siteurl')" var="siteurl" scope="application"/>
	
	<head>
		<tiles:importAttribute name="titleType" scope="request" />
		<tiles:importAttribute name="title" toName="pageTitle" scope="request" />
		
		<c:choose>
			<c:when test="${titleType eq 'simple'}">
				<title><spring:message code="page.title.${pageTitle}" /></title>
				<meta property="og:title" content="<spring:message code="page.title.${pageTitle}" />"/>
			</c:when>
			<c:when test="${(titleType eq 'complex') and (pageTitle eq 'profileView') }">
				<title><spring:message code="page.title.profile" arguments="${profile.username }" /></title>
				<meta property="og:title" content="<spring:message code="page.title.profile" arguments="${profile.username }" />"/>
			</c:when>
			<c:when test="${(titleType eq 'complex') and (pageTitle eq 'profileEdit') }">
				<title><spring:message code="page.title.profile" arguments="${ profile.data.username }" /></title>
				<meta property="og:title" content="<spring:message code="page.title.profile" arguments="${ profile.data.username }" />"/>
			</c:when>
			<c:when test="${(titleType eq 'complex') and (pageTitle eq 'wall') }">
				<title><spring:message code="page.title.wall" arguments="${ wall.author }" /></title>
				<meta property="og:title" content="<spring:message code="page.title.wall" arguments="${ wall.author }" />"/>
			</c:when>
			<c:when test="${(titleType eq 'complex') and (pageTitle eq 'storyDetail') }">
				<title><spring:message code="page.title.storyDetail" arguments="${fn:substring(storyDetail.title, 0, 100)}" /></title>
				<meta property="og:title" content="<spring:message code="page.title.storyDetail" arguments="${fn:substring(storyDetail.title, 0, 100)}" />"/>
			</c:when>
			<c:otherwise>
				<title><spring:message code="page.title.siteName" /></title>
				<meta property="og:title" content="<spring:message code="page.title.siteName" />"/>
			</c:otherwise>
		</c:choose>
		
		
		<meta property="og:site_name" content="MyStory"/>
		<c:choose>
			<c:when test="${ not empty storyDetail and (not fn:contains(storyDetail.largeFeaturedImage, 'defaultFeatured')) }">
				<meta property="og:image" content="${ storyDetail.largeFeaturedImage }"/>
			</c:when>
			<c:otherwise>
				<meta property="og:image" content="http://img.photobucket.com/albums/v386/AVAVT/1521514_737951072883467_1244601070_n_zps95e38b93.jpg"/>
			</c:otherwise>
		</c:choose>
		
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<jsp:include page="css-head.jsp" />
		<jsp:include page="js-head.jsp" />
	</head>
	
	<body <sec:authorize access="isAuthenticated()">class="authenticated"</sec:authorize>>
		<!--[if lt IE 9 ]><jsp:include page="ltIE9_warn.jsp" /><![endif]-->
		<tiles:insertAttribute name="header" />
		<div id="content-container" class="content-container container">
			<div class="row">
				<aside class="sidebar col-md-3">
					<tiles:insertAttribute name="sidebar" />
				</aside>
				<div class="main_content col-md-9">
					<tiles:insertAttribute name="body" />
				</div>
			</div>
		</div>
		<tiles:insertAttribute name="footer" />
		<tiles:insertAttribute name="notification_modal" />
		<jsp:include page="/WEB-INF/jsp/layout/helper_modal.jsp" />
	</body>
</html>