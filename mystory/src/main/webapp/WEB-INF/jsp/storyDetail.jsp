<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<jsp:include page="/WEB-INF/jsp/templates/commentEditTemplate.jsp"/>
<div class="content_wrapper">
	<div class="post_item first_post">
		<div class="author_info">
			<div class="author_upper">
				<a href="<c:url value='/user/${ storyDetail.author.name}' />" class="author_avatar_container">
					<img class="user_avatar" src="${storyDetail.author.smallAvatar }" alt="${ storyDetail.author.name}">
				</a>
				<div class="author_name_container">
					<a class="author_username" href="<c:url value='/user/${ storyDetail.author.name}' />">${ storyDetail.author.name }</a>
					<label class="author_story_points" title="<spring:message code="label.user.points" arguments="${ storyDetail.author.name },${ storyDetail.author.points }"/>">${ storyDetail.author.points }</label>
				</div>
			</div>
			<div class="author_lower">
				<a href="<c:url value='/user/${storyDetail.author.name}/story'/>" class="btn author_btn story_count_btn" title="<spring:message code='label.user.story' arguments='${storyDetail.author.name },${storyDetail.author.numberOfStories }'/>" ><span>${ storyDetail.author.numberOfStories }</span></a>
				<c:choose>
					<c:when test="${ userPanel.name eq storyDetail.author.name }">
						<span class="btn author_btn follower_count_btn" title="<spring:message code="label.user.followers" arguments="${ storyDetail.author.name },${ storyDetail.author.numberOfFollowers }"/>" >${ storyDetail.author.numberOfFollowers }</span>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn author_btn follower_count_btn btn_follow ${ storyDetail.author.followed ? 'is_followed' : '' }" data-username="${storyDetail.author.name}" title="<spring:message code="label.user.followers" arguments="${ storyDetail.author.name },${ storyDetail.author.numberOfFollowers }"/>" >${ storyDetail.author.numberOfFollowers }</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="post_item_inner">
			<div class="vote_panel ${ storyDetail.votedUpByCurrentReader ? 'voted_up' : '' } ${ storyDetail.votedDownByCurrentReader ? 'voted_down' : '' }">
				<button class="vote_btn vote_up_btn" data-vtype="story" data-vid="${storyDetail.id }"></button>
				<span class="vote_count">${ storyDetail.voteCount }</span>
				<button class="vote_btn vote_down_btn" data-vtype="story" data-vid="${storyDetail.id }"></button>
			</div>
			<div class="post_item_content">
				<h1 class="topic_name">
					<c:out value="${ storyDetail.title }"/>
				</h1>
				
				<c:if test="${ not fn:contains(storyDetail.largeFeaturedImage, 'defaultFeatured') }">
					<img class="story_featured_img" src="${ storyDetail.largeFeaturedImage }" />
				</c:if>
				
				${ storyDetail.content }
				
			</div>
			
			<div class="post_item_footer">
				<div class="footer_more_info">
					<span class="footer_info_item footer_submit_time" title="${ storyDetail.submittedDate }"><span>${ storyDetail.shortTime }</span></span>
					<span class="footer_info_item footer_view_count" title="<spring:message code='label.story.view.count' arguments='${ storyDetail.viewCount }'/>"><span>${storyDetail.viewCount}</span></span>
					<span class="footer_info_item footer_comment_count" title="<spring:message code='label.story.comment.count' arguments='${ storyDetail.commentCount }'/>"><span>${storyDetail.commentCount}</span></span>
					
					<div class="footer_category"><span><spring:message code="label.story.in"/></span><a href="<c:url value='/tag/${storyDetail.categoryName}'/>" >${storyDetail.categoryDisplayName }</a></div>
				</div>
				<c:if test="${(fn:length(storyDetail.previousChapters) gt 0) or (fn:length(storyDetail.nextChapters) gt 0)}" >
					<div class="linked_story clearfix">
						<c:if test="${fn:length(storyDetail.previousChapters) gt 0 }" >
							<div class="previous_topic">
								<h3><spring:message code="label.story.previous"/></h3>
								<ul>
									<c:forEach items="${storyDetail.previousChapters }" var="pChap">
										<li>
											<a href="<c:url value='/story/${pChap.id}' />">${pChap.title}</a>
										</li>
									</c:forEach>
								</ul>
							</div>
						</c:if>
						<c:if test="${fn:length(storyDetail.nextChapters) gt 0 }" >
							<div class="next_topic">
								<h3><spring:message code="label.story.next"/></h3>
								<ul>
									<c:forEach items="${storyDetail.nextChapters }" var="nChap">
										<li>
											<a href="<c:url value='/story/${nChap.id}' />">${nChap.title}</a>
										</li>
									</c:forEach>
								</ul>
							</div>
						</c:if>		
					</div>
				</c:if>
				<div class="footer_actions">
					<span class="fb-share-button" data-href="${siteurl}<c:url value='story/${storyDetail.friendUrl }' />" data-type="button_count"></span>
					<a href="#commentform" class="btn btn_important post_action"><spring:message code="label.action.comment"/></a>
					
					<sec:authorize access="canEditStory('${storyDetail.author.name}')" >				
						<a href="<c:url value='/story/editStory/${ storyDetail.friendUrl }'/>"class="btn post_action"><spring:message code="label.action.edit"/></a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</div>
	
	<c:if test="${ fn:length(storyDetail.comments) gt 0 }" >
		<div id="comment_list_wrapper" class="comment_list_wrapper">
			<%-- comment out until we allow 2 levels comment
			<span class="comment_sort">
				<spring:message code="label.story.view.comment"/>
				<select id="comment_sort_type">
					<option value="1" selected="selected"><spring:message code="label.all"/></option>
					<option value="2"><spring:message code="label.top"/></option>
				</select>
			</span>
			 --%>
			<ul id="all_comment" class="comment_list">
				<c:forEach items="${ storyDetail.comments }" var="comment">
					<li class="post_item comment <c:if test='${ comment.voteCount lt -3 }'>bad_comment</c:if>" id="comment-${comment.id }" data-comment-id="${comment.id }">
						<c:if test='${ comment.voteCount lt -3 }'>
							<div class="bad_comment_warning">
								<spring:message code="message.comment.bad.warning"/><a href="#" class="bad_comment_view"><spring:message code="label.comment.bad.warning"/></a>
							</div>
						</c:if>
						
						<div class="author_info">
							<div class="author_upper">
								<a href="<c:url value='/user/${ comment.author.name}' />" class="author_avatar_container">
									<img width="60" height="60" class="user_avatar" src="${comment.author.smallAvatar }" alt="${ comment.author.name}">
								</a>
								<div class="author_name_container">
									<a class="author_username" href="<c:url value='/user/${ comment.author.name}' />">${ comment.author.name }</a>
									<span class="author_story_points" title="<spring:message code="label.user.points" arguments="${ comment.author.name },${ comment.author.points }"/>">${ comment.author.points }</span>
								</div>
							</div>
							<div class="author_lower">
								<a href="<c:url value='/user/${comment.author.name}/story'/>" class="btn author_btn story_count_btn" title="<spring:message code='label.user.story' arguments='${comment.author.name },${comment.author.numberOfStories }'/>" ><span>${ comment.author.numberOfStories }</span></a>
								<c:choose>
									<c:when test="${ userPanel.name eq comment.author.name }">
										<span class="btn author_btn follower_count_btn" title="<spring:message code="label.user.followers" arguments="${ comment.author.name },${ comment.author.numberOfFollowers }"/>" >${ comment.author.numberOfFollowers }</span>
									</c:when>
									<c:otherwise>
										<button  type="button" class="btn author_btn follower_count_btn btn_follow  ${ comment.author.followed ? 'is_followed' : '' }" data-username="${comment.author.name}" title="<spring:message code="label.user.followers" arguments="${ comment.author.name },${ comment.author.numberOfFollowers }"/>" >${ comment.author.numberOfFollowers }</button>		
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="post_item_inner">
							<div class="vote_panel ${ comment.votedUpByCurrentReader ? 'voted_up' : '' } ${ comment.votedDownByCurrentReader ? 'voted_down' : '' }">
								<button class="vote_btn vote_up_btn" data-vtype="story" data-vid="${comment.id }"></button>
								<span class="vote_count">${ comment.voteCount }</span>
								<button class="vote_btn vote_down_btn" data-vtype="story" data-vid="${comment.id }"></button>
							</div>
							<div class="post_item_content">
								<div class="comment_content">${ comment.content }</div>
								
								<div class="post_item_footer">
									<div class="footer_more_info">
										<span class="footer_info_item footer_submit_time" title="${comment.submittedDate}"><span>${comment.shortTime}</span></span>
									</div>
									<div class="footer_actions">
										<sec:authorize access="isAuthenticated()" var="isAuth">
											<a href="#commentform" class="btn post_action post_action_small comment_reply" data-comment-id="${comment.id }"><spring:message code="label.btn.reply"/></a>
										</sec:authorize>
										<sec:authorize access="canEditComment('${comment.author.name}')">
											<button class="btn post_action action_edit_comment" data-comment-id="${ comment.id }"><spring:message code="label.action.edit" /></button>
										</sec:authorize>
									</div>
								</div>
							</div>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</c:if>
	
	<sec:authorize access="isAuthenticated()" var="isAuth">
		<div class="new_comment post_item">
			<div class="author_info">
				<div class="author_upper">
					<a href="<c:url value='/user/${ userPanel.name}' />" class="author_avatar_container">
						<img width="60" height="60" class="user_avatar" src="${userPanel.avatar }" alt="${ userPanel.name}">
					</a>
					<div class="author_name_container">
						<a class="author_username" href="<c:url value='/user/${ userPanel.name}' />">${ userPanel.name }</a>
						<label class="author_story_points" title="<spring:message code="label.user.points" arguments="${ userPanel.name },${ userPanel.points }"/>">${ userPanel.points }</label>
					</div>
				</div>
				<div class="author_lower">
					<a href="<c:url value='/user/${userPanel.name}/story'/>" class="btn author_btn story_count_btn" title="<spring:message code='label.user.story' arguments='${userPanel.name },${userPanel.numberOfStories }'/>" ><span>${ userPanel.numberOfStories }</span></a>
					<span class="btn author_btn follower_count_btn" title="<spring:message code="label.user.followers" arguments="${ userPanel.name },${ userPanel.numberOfFollowers }"/>" >${ userPanel.numberOfFollowers }</span>
				</div>
			</div>
			<div class="post_item_inner">
				<form action="<c:url value='/comment/add' />" class="nopopup_validate" method="post" id="commentform">
					<textarea class="mys_input full_width_textarea" name="content" id="mys-input-comment-new"></textarea>
					<input type="hidden" value="${storyDetail.friendUrl}" name="storyId">
					<input type="submit" class="btn btn_important submit_comment post_action" id="submit_comment" value="<spring:message code="label.story.send.comment" />" />
				</form>
			</div>
		</div>		
	</sec:authorize>
	<c:if test="${!isAuth}">
		<div class="new_comment post_item">
			<div class="post_item_inner">
				<spring:message code="label.story.login.to.comment" arguments="${request.contextPath }/login" />
			</div>
		</div>
	</c:if>
</div>