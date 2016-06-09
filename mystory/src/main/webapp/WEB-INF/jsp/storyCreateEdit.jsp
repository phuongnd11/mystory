<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form id="story_telling_form" class="nopopup_validate" method="post" action="<c:choose><c:when test="${ not empty story.friendUrl }"><c:url value='/story/edit'/></c:when><c:otherwise><c:url value='/story/tell'/></c:otherwise></c:choose>" > 	
	<div class="content_wrapper">
		<div class="new_story_editor">
			<div class="col-md-9">
				<input type="hidden" name="id" value="${ story.friendUrl }" />
				<div class="form-group">
					<input type="text" id="story_title" maxlength="200" name="title" class="full_width_input form-control" placeholder="<spring:message code="label.story.title"/>" value="<c:out value='${story.title}'/>"/>
				</div>
				<div class="form-group">
					<c:set var="selectedCategory" value="${ not empty story.category ? story.category : param.category}" />
					<select name="category" class="form-control" >
						<c:forEach items="${ allCategories }" var="cat">
							<option value="${ fn:toUpperCase(cat.name) }" <c:if test="${ selectedCategory eq cat.name }">selected="selected"</c:if>>${ cat.displayName }</option>
						</c:forEach>
					</select>
				</div>
				<textarea class="mys_input full_width_textarea texarea_large" placeholder="<spring:message code='label.story.content'/>" name="content" rows="15" id="mys-input-story">${ story.content }</textarea>
		        <input type="hidden" id="featured_img" name="featuredImg" value="${ story.featuredImgName }" />
	        </div>
			<div class="col-md-3">
				<div class="story_edit_control">
					<fieldset>
						<legend><spring:message code="label.featured.image"/></legend>
					</fieldset>
					<div id="featured_preview" class="featured_preview">
						<c:choose>
							<c:when test="${ not empty story.featuredImg }" ><img src="<c:url value='${story.featuredImg }' />" /></c:when>
							<c:otherwise><img src="/resources/imgs/featuredDefault.png" /></c:otherwise>
						</c:choose>
					</div>
					<div class="progress_bar hide" id="fileupload_featured_progress_bar">
						<div class="progress_container">
							<div class="progress"></div>
						</div>
						<span class="progress_percent"></span>
					</div>
					<span class="btn btn_important btn_upload half_width_input_left" id="btn_edit_featured">
						<spring:message code="label.upload.btn"/>
						<input id="fileupload_featured" type="file" class="hidden_upload" name="files[]" accept="image/*" data-url="/image/upload/featured">
					</span>
					<button class="btn half_width_input_right" id="btn_remove_featured"><spring:message code="label.btn.cancel"/></button>
				</div>
				<div class="story_edit_control">
					<fieldset>
						<legend><spring:message code="label.original.story"/></legend>
					</fieldset>
					<p id="original_story_name" class="parent_topic">${ story.originalStoryTitle }</p>
					<button type="button" id="btn_choose_original" class="btn full_width_input" ><spring:message code="label.choose.original.story"/></button>
				</div>
				<div class="story_edit_control" id="story_telling_submit">
					<fieldset>
						<legend><spring:message code="label.complete"/></legend>
					</fieldset>
					<button id="story-telling_submit" class="btn btn_important half_width_input_left" onclick="$('#story_telling_form').submit();">
						<c:choose><c:when test="${ not empty story.friendUrl }"><spring:message code="label.edit.story"/></c:when><c:otherwise><spring:message code="label.create.story"/></c:otherwise></c:choose>
					</button>
					<a href="javascript:history.go(-1)" class="btn half_width_input_right"><spring:message code="label.btn.back" /></a>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="original_story_overlay" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<div class="radio">
						<label>
							<input type="radio" title="" name="originalStoryId" id="radio_parent_-1" <c:if test="${ empty story.originalStoryId }">checked</c:if> value=""/>
							<spring:message code="label.story.original.none"/>
						</label>
					</div>
					<c:forEach items="${ eligibleOriginalStory }" var="candidate">
						<div class="radio">
							<label>
								<input type="radio" title="${ candidate.title }" name="originalStoryId" id="radio_original_story_${ candidate.id }" value="${ candidate.id }" <c:if test="${ story.originalStoryId eq candidate.id }" >checked</c:if> />
								<c:out value="${ candidate.title }" />
							</label>
						</div>	
					</c:forEach>

					<input type="hidden" id="current_original_story" value="${ story.originalStoryId }"/>
				</div>
				<div class="modal-footer">
					<input type="button" id="accept_original" class="btn btn_important" value="<spring:message code="label.choose.original.story"/>" />
					<input type="button" class="btn" id="cancel_original" value="Hủy chọn" />
				</div>
			</div>
		</div>
	</div>
</form>