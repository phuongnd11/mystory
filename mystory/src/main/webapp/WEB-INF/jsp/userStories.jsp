<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<div class="content_wrapper">
	<input type="hidden" id="paged" value="${ wall.page }" />
	<ul id="story_list" class="story_list">
		<c:if test="${ not empty wall.author }">
			<li class="topic_item">
				<spring:message code="label.story.by.user" arguments="${request.contextPath}/user/${wall.author}, ${ wall.author}"/>
			</li>
		</c:if>
	
		<c:forEach items="${ wall.stories }" var="story">
			<li class="story_item <c:if test="${ story.voteCount lt -4 }">bad_story</c:if>">
				<c:if test="${ story.voteCount lt -4 }">
					<div class="bad_story_warning">
						<spring:message code="message.story.bad.warning"/><a href="#" class="bad_story_view"><spring:message code="label.comment.bad.warning"/></a>
					</div>
				</c:if>
				
				<div class="author_info">
					<div class="author_upper">
						<a href="<c:url value='/user/${ story.author.name}' />" class="author_avatar_container">
							<c:if test="${ not empty story.author.smallAvatar }">
								<img width="14" height="14" class="user_avatar" src="${story.author.smallAvatar }" alt="${ story.author.name}">
							</c:if>
						</a>
						<div class="author_name_container">
							<a class="author_username" href="<c:url value='/user/${ story.author.name}' />">${ story.author.name }</a>
							<label class="author_story_points" title="<spring:message code="label.user.points" arguments="${ story.author.name },${ story.author.points }"/>">${ story.author.points }</label>
						</div>
					</div>
					<div class="author_lower">
						<a href="<c:url value='/story/${story.id}#comment_list_wrapper'/>" class="btn author_btn comment_count_btn"><span>${ story.commentCount }</span></a>
						<span title="${ story.submittedDate }" class="btn author_btn story_submit_time"><span>${ story.shortTime }</span></span>
					</div>
				</div>
				<div class="vote_panel ${ story.votedUpByCurrentReader ? 'voted_up' : '' } ${ story.votedDownByCurrentReader ? 'voted_down' : '' }">
					<button class="vote_btn vote_up_btn" data-vtype="story" data-vid="${story.id }"></button>
					<span class="vote_count">${ story.voteCount }</span>
					<button class="vote_btn vote_down_btn" data-vtype="story" data-vid="${story.id }"></button>
				</div>
				<div class="story_item_inner">
					<c:choose>
						<c:when test="${ not empty story.featuredImage }">
							<img class="featured_img" src="${ story.featuredImage }" />
						</c:when>
						<c:otherwise>
							<img class="featured_img" src="http://localhost:8080/image/39f2c17a30dc9dc26e2f71dc82198676_medium" />
						</c:otherwise>
					</c:choose>
					<div class="story_item_content">
						<h2 class="topic_name">
							<a href="<c:url value='/story/${story.friendUrl}'/>"><c:out value="${story.title}"/></a>
						</h2>
						<div class="excerpt">${ story.content }</div>
						<%--<p class="post_subinfo">
							<span><spring:message code="label.story.in" /></span><a href="<c:url value='/tag/${story.categoryName}'/>">${story.categoryDisplayName }</a>
							 Comment out until remember is implemented
							<sec:authorize access="isAuthenticated()">
								<span class="action_divider"></span><a href="#" class="action_link save_topic"><spring:message code="label.action.remember" /></a>
							</sec:authorize>
							 
						</p>--%>
					</div>
				</div>
			</li>
		</c:forEach>
	</ul>
</div>