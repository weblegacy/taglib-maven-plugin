
<!--
  - The MIT License
  - Copyright © 2004-2014 Fabrizio Giustina
  - Copyright © 2022-2026 Web-Legacy
  -
  - Permission is hereby granted, free of charge, to any person obtaining a copy
  - of this software and associated documentation files (the "Software"), to deal
  - in the Software without restriction, including without limitation the rights
  - to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  - copies of the Software, and to permit persons to whom the Software is
  - furnished to do so, subject to the following conditions:
  -
  - The above copyright notice and this permission notice shall be included in
  - all copies or substantial portions of the Software.
  -
  - THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  - IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  - FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  - AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  - LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  - OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  - THE SOFTWARE.
-->

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
