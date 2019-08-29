

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('REVIWER')">
<display:table pagesize="5" name="reports" id="row"
requestURI="report/reviwer/list.do" >

<display:column property="submission.ticker" titleKey="report.submission.ticker" />
<display:column property="originalityScore" titleKey="report.originalityScore" />
<display:column property="qualityScore" titleKey="report.qualityScore" />
<display:column property="eadabilityScore" titleKey="report.eadabilityScore" />
<display:column property="decision" titleKey="report.decision" />
<display:column property="comment" titleKey="report.comments" />
<display:column>
	<a href="report/reviwer/edit.do?submissionId=${submissionId}&reportId=${row.id}"><spring:message code="report.edit" /></a>
</display:column>

</display:table>
<acme:cancel url="submission/reviwer/list.do" code="report.cancel"/>
<jstl:if test="${empty reports}">
	<input type="button" name="create" value="<spring:message code="report.create" />"
			onclick="javascript: relativeRedir('report/reviwer/create.do?submissionId=${submissionId}');" /><br>
</jstl:if>


</security:authorize>