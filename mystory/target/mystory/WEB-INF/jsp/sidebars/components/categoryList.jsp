<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="sidebar_controls categories_container">
	<fieldset>
		<legend><spring:message code="label.category"/></legend>
	</fieldset>
	<ul id="categories_list" class="categories_list">
		<c:forEach items="${menuCategories}" var="category" varStatus="status">
			<li class="category_item">
				<a href="/tag/${category.name}" <c:if test="${ category.selected }">class="selected"</c:if>>
					<span>${category.displayName }</span>
				</a>
			</li>
		</c:forEach>
	</ul>
</div>