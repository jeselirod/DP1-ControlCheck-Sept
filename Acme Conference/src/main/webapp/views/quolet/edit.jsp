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
	<form:form action="quolet/administrator/edit.do" modelAttribute="quolet">
	

		<jstl:if test="${not empty exception}">
			<p style="color:red"> <spring:message code="quolet.error" /> </p>
		</jstl:if>

		<form:hidden path="id"/>
		<form:hidden path="version"/>
		
		<acme:select items="${conferences }" itemLabel="title" code="quolet.conference" path="conference"/>
		<acme:textarea code="quolet.body" path="body"/>
		<acme:textbox code="quolet.picture" path="picture"/>
		<acme:textbox code="quolet.xxxx" path="xxxx"/>
		<form:label path="draftMode"><spring:message code="quolet.draftMode" />:</form:label>
		<form:select path="draftMode">
			<form:option value="1" label="Draft mode" />	
			<form:option value="0" label="Save mode" />		
		</form:select>
	<form:errors path="draftMode"/>
		
		<br/>
		
		<input type="submit" name="save" value="<spring:message code="quolet.save" />" />
		<input type="button" name="cancel" value="<spring:message code="quolet.cancel" />"
				onclick="javascript: relativeRedir('quolet/administrator/list.do');" />
		<jstl:if test="${quolet.id ne 0 }">	
			<input type="submit" name="delete" value="<spring:message code="quolet.delete" />" />
		</jstl:if>
	</form:form>	
	
</security:authorize>