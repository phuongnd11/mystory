<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/template" id="mailTemplate">
<table id="table_messages" class="message_table table-striped table table-hover">
	<tbody>
		{{#messages}}
			<tr {{^read}}class="message_not_read"{{/read}}>
				<td>{{fromTo}}</td>
				<td>{{title}}</td>
				<td>{{formatedTime}}</td>
				<td class="hide">{{submittedTime}}</td>
			</tr>
			{{/messages}}
			{{^messages}}
				<tr class="no_message">
					<td><spring:message code="label.mailbox.empty"/></td>
				</tr>
			{{/messages}}
	</tbody>
</table>
</script>