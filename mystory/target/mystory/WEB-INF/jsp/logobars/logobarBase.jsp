<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="logo_bar">
	<div class="container">
		<div class="menu_wrapper col-md-12">
			<tiles:insertAttribute name="logobarExtensions" />
		</div>
	</div>
</div>