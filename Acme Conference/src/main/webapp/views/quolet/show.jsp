<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<security:authorize access="hasRole('ADMIN')">
<b><spring:message code="quolet.ticker" /> : </b> <jstl:out value="${quolet.ticker}"></jstl:out> <br/>
<jstl:if test="${lang eq 'es' }">
	<b><spring:message code="quolet.publicationMoment" />:</b>
		<fmt:formatDate value="${quolet.publicationMoment}" pattern="dd-MM-yy HH:mm" />
		<br />
	</jstl:if>
	
	<jstl:if test="${lang eq 'en' }">
	<b><spring:message code="quolet.publicationMoment" />:</b>
		<fmt:formatDate value="${quolet.publicationMoment}" pattern="yy/MM/dd HH:mm" />
		<br />
	</jstl:if>

<b><spring:message code="quolet.body" /> : </b> <jstl:out value="${quolet.body}"></jstl:out> <br/>
<b><spring:message code="quolet.picture" /> : </b> <br/> <img width="150" height="150" src="${quolet.picture}"> <br/>
<b><spring:message code="quolet.xxxx" /> : </b> <jstl:out value="${quolet.xxxx}"></jstl:out> <br/>

<jstl:if test="${quolet.draftMode eq 0 }">
	<b><spring:message code="quolet.draftMode" /> : </b><spring:message code="submission.draftMode.0" /><br/>
</jstl:if>
<jstl:if test="${quolet.draftMode eq 1 }">
	<b><spring:message code="quolet.draftMode" /> : </b><spring:message code="submission.draftMode.1" /><br/>
</jstl:if>

<h3><spring:message code="submission.conference" /></h3>
<b><spring:message code="submission.conference.title" /> : </b> <jstl:out value="${conference.title}"></jstl:out> <br/>
<b><spring:message code="submission.conference.acronym" /> : </b> <jstl:out value="${conference.acronym}"></jstl:out> <br/>
<b><spring:message code="submission.conference.venue" /> : </b> <jstl:out value="${conference.venue}"></jstl:out> <br/>
<b><spring:message code="submission.conference.submissionDeadline" /> : </b> <jstl:out value="${conference.submissionDeadline}"></jstl:out> <br/>
<b><spring:message code="submission.conference.notificacionDeadline" /> : </b> <jstl:out value="${conference.notificacionDeadline}"></jstl:out> <br/>
<b><spring:message code="submission.conference.cameraDeadline" /> : </b> <jstl:out value="${conference.cameraDeadline}"></jstl:out> <br/>
<b><spring:message code="submission.conference.startDate" /> : </b> <jstl:out value="${conference.startDate}"></jstl:out> <br/>
<b><spring:message code="submission.conference.endDate" /> : </b> <jstl:out value="${conference.endDate}"></jstl:out> <br/>
<b><spring:message code="submission.conference.summary" /> : </b> <jstl:out value="${conference.summary}"></jstl:out> <br/>
<b><spring:message code="submission.conference.fee" /> : </b> <jstl:out value="${conference.fee}"></jstl:out> <br/>

<jstl:if test="${conference.finalMode eq 0 }">
	<b><spring:message code="submission.conference.finalMode" /> : </b><spring:message code="submission.conference.finalMode.0" /><br/>
</jstl:if>
<jstl:if test="${conference.finalMode eq 1 }">
	<b><spring:message code="submission.conference.finalMode" /> : </b><spring:message code="submission.conference.finalMode.1" /><br/>
</jstl:if>

<acme:cancel url="quolet/administrator/list.do" code="quolet.cancel"/>
</security:authorize>