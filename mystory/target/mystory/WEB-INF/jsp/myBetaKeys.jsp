<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="content_wrapper">
	<div class="ui_block_element">
		<div>
			<button id="btn_gen_code" class="btn btn_important">Tạo Code</button>
		</div>
		<table id="table_codes" class="table-striped table-bordered table table-hover">
			<thead>
				<tr>
					<th><spring:message code="label.beta.key" /></th>
					<th><spring:message code="label.beta.key.receiver.email" /></th>
					<th><spring:message code="label.beta.key.create.date" /></th>
					<th><spring:message code="label.beta.key.status" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${codes}" var="betaCode">
					<tr class="${ betaCode.status == 'SENT' ? 'warning' : '' } ${ betaCode.status == 'TAKEN' ? 'success' : '' }">
						<td>
							${ betaCode.code }
						</td>
						<td>
							${ betaCode.invited }
						</td>
						<td>
							${ betaCode.generatedTime }
						</td>
						<td>
							<spring:message code="label.beta.key.${ betaCode.status }"/>
						</td>
					</tr>	
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>