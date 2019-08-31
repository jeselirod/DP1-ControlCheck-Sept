

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

<jstl:choose>
	<jstl:when test="${lang eq 'en'}">
		<display:column class="${css}" property="publicationMoment" titleKey="quolet.publicationMoment" format="{0,date,yy/MM/dd hh:mm}"  />
	</jstl:when>
		
	<jstl:otherwise>
		<display:column class="${css}" property="publicationMoment" titleKey="quolet.publicationMoment" format="{0,date,dd-MM-yy hh:mm}"  />
	</jstl:otherwise>
</jstl:choose>

<display:column property="body" titleKey="quolet.body" />
<display:column property="conference.title" titleKey="quolet.conference" />

<display:column titleKey="quolet.picture">
	<img width="80" height="80" src="${row.picture}">
</display:column>
<display:column property="xxxx" titleKey="quolet.xxxx" />


<display:column>
	<jstl:if test="${row.draftMode eq 1}">
		<a href="quolet/administrator/edit.do?quoletId=${row.id}"><spring:message code="quolet.edit" /></a>
	</jstl:if>
</display:column>
</display:table>


<input type="button" name="create" value="<spring:message code="quolet.create" />"
			onclick="javascript: relativeRedir('quolet/administrator/create.do');" />	

</security:authorize>