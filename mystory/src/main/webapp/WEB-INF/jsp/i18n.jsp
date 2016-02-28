<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<body>
	<form:form>
		<table>
			<tr>
			<!-- Spring message tag for i18n -->
			<td><spring:message code="label.username" /></td>
			<td><input type="text" name="username" /></td>
			</tr>
			<tr>
				<td><spring:message code="label.password" /></td>
				<td><input type="text" name="password" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit"
					value="<spring:message code="label.login"/>" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>