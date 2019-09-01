

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<style type="text/css">
.LESS_1_MONTH{
  background-color: Indigo ;
}
.BETWEEN_1_AND_2_MONTH{
  background-color: Green ;
}
.MORE_2_MONTH{
  background-color: LightCoral;
}
</style>

<security:authorize access="hasRole('ADMIN')">
<display:table pagesize="5" name="quolets" id="row"
requestURI="quolet/administrator/list.do" >

<display:column>
	<a href="quolet/administrator/show.do?quoletId=${row.id}"><spring:message code="quolet.show" /></a>
</display:column>
<jstl:if test="${row.numMonth ne null }">
<jstl:choose>
		<jstl:when test="${row.numMonth < 1 }">
			<jstl:set var="css" value="LESS_1_MONTH"></jstl:set>
		</jstl:when>
	
		<jstl:when test="${row.numMonth < 2 and row.numMonth >= 1}">
				<jstl:set var="css" value="BETWEEN_1_AND_2_MONTH"></jstl:set>
		</jstl:when>
	
		<jstl:when test="${row.numMonth >= 2}">
				<jstl:set var="css" value="MORE_2_MONTH"></jstl:set>
		</jstl:when>
		</jstl:choose>
		
	
</jstl:if>
<display:column property="ticker" class="${css}" titleKey="quolet.ticker" />

<jstl:choose>
	<jstl:when test="${lang eq 'en'}">
		<display:column class="${css}" property="publicationMoment" titleKey="quolet.publicationMoment" format="{0,date,yy/MM/dd hh:mm}"  />
	</jstl:when>
		
	<jstl:otherwise>
		<display:column class="${css}" property="publicationMoment" titleKey="quolet.publicationMoment" format="{0,date,dd-MM-yy hh:mm}"  />
	</jstl:otherwise>
</jstl:choose>

<display:column class="${css}" property="body" titleKey="quolet.body" />
<display:column  class="${css}" property="conference.title" titleKey="quolet.conference" />

<display:column class="${css}" titleKey="quolet.picture">
	<img width="80" height="80" src="${row.picture}">
</display:column>
<display:column class="${css}" property="xxxx" titleKey="quolet.xxxx" />


<display:column class="${css}">
	<jstl:if test="${row.draftMode eq 1}">
		<a href="quolet/administrator/edit.do?quoletId=${row.id}"><spring:message code="quolet.edit" /></a>
	</jstl:if>
</display:column>
</display:table>


<input type="button" name="create" value="<spring:message code="quolet.create" />"
			onclick="javascript: relativeRedir('quolet/administrator/create.do');" />	

</security:authorize>