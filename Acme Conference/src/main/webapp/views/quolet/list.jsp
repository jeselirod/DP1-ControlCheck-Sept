

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ADMIN')">
<display:table pagesize="5" name="quolets" id="row"
requestURI="quolet/administrator/list.do" >

<display:column>
	<a href="quolet/administrator/show.do?quoletId=${row.id}"><spring:message code="quolet.show" /></a>
</display:column>


<display:column property="ticker" titleKey="quolet.ticker" />
<display:column property="publicationMoment" titleKey="quolet.publicationMoment" />
<display:column property="body" titleKey="quolet.body" />
<display:column property="conference.title" titleKey="quolet.conference" />

<display:column titleKey="quolet.picture">
	<img width="80" height="80" src="${row.picture}">
</display:column>
<display:column property="xxxx" titleKey="quolet.xxxx" />
</display:table>



</security:authorize>