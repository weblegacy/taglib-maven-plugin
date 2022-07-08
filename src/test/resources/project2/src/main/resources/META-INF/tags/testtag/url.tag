<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:spring="urn:jsptld:http://www.springframework.org/tags" xmlns:c="urn:jsptld:http://java.sun.com/jsp/jstl/core"
	xmlns:fmt="urn:jsptld:http://java.sun.com/jsp/jstl/fmt" xmlns:su="urn:jsptld:stringutils" xmlns:elx="urn:jsptld:elx">
	<jsp:directive.attribute name="var" required="false" />
	<jsp:directive.attribute name="value" required="true" />
	<c:url var="tmpVar" value="${currentpage}">
		<c:param name="springaction" value="${value}" />
		<jsp:doBody />
	</c:url>
	<c:choose>
		<c:when test="${!empty(var)}">
		    <jsp:scriptlet>
			    String varName = (String)jspContext.getAttribute("var");
			    String varContent = (String)jspContext.getAttribute("tmpVar");
			    jspContext.setAttribute(varName, varContent);
		    </jsp:scriptlet>
			<!-- <c:set var="${var}" value="tmpVar" /> -->
		</c:when>
		<c:otherwise>${tmpVar}</c:otherwise>
	</c:choose>
</jsp:root>
