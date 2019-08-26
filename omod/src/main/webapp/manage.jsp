<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<%@ page import="org.openmrs.module.conceptname.htmlformentry.PostSubmissionActionTagHandler" %>

<form method="GET">
	Choose a Concept: <openmrs_tag:conceptField formFieldName="conceptId"/>
	<input type="submit" value="view" />
</form>

<hr/>

<table>
	<tr>
		<th>Concept ID</th>
		<td>${ concept.conceptId }</td>
	</tr>
	<tr>
		<th>UUID</th>
		<td>${ concept.uuid }</td>
	</tr>
	<tr>
		<th>Names</th>
		<td>
			<table border="1">
			<tr>
				<th>Locale</th>
				<th>Preferred</th>
				<th>Type</th>
				<th>Name</th>
				<th>Tags</th>
			</tr>
			<c:forEach var="cn" items="${ concept.names }">
				<tr>
					<td>${ cn.locale }</td>
					<td>${ cn.localePreferred }</td>
					<td>${ cn.conceptNameType }</td>
					<td><c:out escapeXml="true" value="${ cn.name }"/></td>
					<td>
						<c:forEach var="t" items="${ cn.tags }" varStatus="status" >
							<c:out escapeXml="true" value="${ t.tag }"/>
							<c:if test="${ not status.last }">, </c:if>
						</c:forEach>
					</td>
				</tr>
			</c:forEach>
			</table>
		</td>
	</tr>
</table>
<%
PostSubmissionActionTagHandler pt = new PostSubmissionActionTagHandler();
out.println("Patient Data Elements: <br/><br/>");
out.println("Patient ID: "+pt.patientID+"<br/><br/>");
out.println("Patient ID No Strings Attached: "+pt.patientIDdb+"<br/><br/>");
out.println("Patient Location: "+pt.patientLocation+"<br/><br/>");
out.println("Encounter ID: "+pt.encounterId+"<br/><br/>");
out.println("Start Visit Date: "+pt.fromDate+"<br/><br/>");
out.println("End Visit Date: "+pt.toDate+"<br/><br/>");
out.println("<br/><br/>");
out.println("Formatted Time Details: <br/><br/>");
out.println("Start Visit Date: "+pt.fromDateStr+"<br/><br/>");
out.println("Start Visit Date - Year: "+pt.fromDateStrYr+"<br/><br/>");
out.println("Start Visit Date - Month: "+pt.fromDateStrMm+"<br/><br/>");
out.println("Start Visit Date - Day: "+pt.fromDateStrDd+"<br/><br/>");
out.println("Visit Date - From OpenMRS DB: "+pt.visitDate+"<br/><br/>");
out.println("UUID - From OpenMRS: "+pt.visitUuid+"<br/><br/>");
%>


<%@ include file="/WEB-INF/template/footer.jsp"%>