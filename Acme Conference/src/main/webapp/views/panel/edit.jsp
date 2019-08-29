<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<security:authorize access="hasRole('ADMIN')">

<form:form action="panel/administrator/edit.do" modelAttribute="panel">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<acme:textbox code="tutorial.title" path="title"/>
	<acme:textbox code="tutorial.speaker" path="speaker"/>
	<acme:textbox code="tutorial.duration" path="duration"/>
	<acme:textbox code="tutorial.schedule" path="schedule"/>
	<acme:textbox code="tutorial.room" path="room"/>
	<acme:textbox code="tutorial.summary" path="summary"/>
	<spring:message code="tutorial.attachmets.comment" />
	<acme:textbox code="tutorial.attachments" path="attachments"/>
	<acme:select items="${conferences}" itemLabel="title" code="tutorial.conference" path="conference"/>
	
	<br/>
	<input type="submit" name="save" 
	value="<spring:message code="tutorial.save" />" />
	
	<input type="submit" name="delete" 
	value="<spring:message code="tutorial.delete" />" />

	<input type="button" name="cancel" value="<spring:message code="tutorial.cancel" />"
			onclick="javascript: relativeRedir('panel/administrator/list.do');" />
</form:form>


</security:authorize>