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


<security:authorize access="isAuthenticated()">
	<form:form action="message/actor/send.do" modelAttribute="message">
	
		<jstl:if test="${not empty exception}">
			<p style="color:red"> <spring:message code="mensaje.error" /> </p>
		</jstl:if>
	
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		
		<acme:textbox code="mensaje.emailReceiver" path="emailReceiver"/>
		<acme:textbox code="mensaje.subject" path="subject"/>
		<acme:textarea code="mensaje.body" path="body"/>
		<acme:select items="${topics }" itemLabel="name" code="mensaje.topic" path="topic"/>
		
		<br/>
		<input type="submit" name="save" 
		value="<spring:message code="mensaje.save" />" />

		<input type="button" name="cancel" value="<spring:message code="mensaje.cancel" />"
				onclick="javascript: relativeRedir('message/actor/list.do');" />
	</form:form>	
	
</security:authorize>