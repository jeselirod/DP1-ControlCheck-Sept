<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('ADMIN')">
<jstl:if test="${not empty error}">
<b style="color:red;"><spring:message code="presentation.error" /></b>
</jstl:if>
	<display:table pagesize="5" name="presentations" id="row"
		requestURI="presentation/administrator/list.do">

		<display:column titleKey="tutorial.title">
			<jstl:out value="${row.title}"></jstl:out>
		</display:column>

		<display:column titleKey="tutorial.speaker">
			<jstl:out value="${row.speaker}"></jstl:out>
		</display:column>

		<display:column titleKey="tutorial.duration">
			<jstl:out value="${row.duration}"></jstl:out>
		</display:column>

		<display:column titleKey="tutorial.schedule">
			<jstl:out value="${row.schedule}"></jstl:out>
		</display:column>

		<display:column titleKey="tutorial.room">
			<jstl:out value="${row.room}"></jstl:out>
		</display:column>

		<display:column titleKey="tutorial.show">
			<a href="presentation/administrator/show.do?presentationId=${row.id}"><spring:message
					code="tutorial.show" /></a>
		</display:column>
		<display:column titleKey="tutorial.edit">
			<a href="presentation/administrator/edit.do?presentationId=${row.id}"><spring:message
					code="tutorial.edit" /></a>
		</display:column>
	</display:table>

	<input type="button" name="create"
		value="<spring:message code="presentation.create" />"
		onclick="javascript: relativeRedir('presentation/administrator/create.do');" />
</security:authorize>