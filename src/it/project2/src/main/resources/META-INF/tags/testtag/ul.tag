
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

<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page">

  <jsp:directive.attribute name="collection" required="true"
    type="java.lang.Object" />
  <jsp:directive.attribute name="property" required="false" description="test description
&lt;strong>strong&lt;/strong>
and
newline" />
  <jsp:directive.tag description="test description
&lt;strong>strong&lt;/strong>
and
newline" example="test example
&lt;strong>strong&lt;/strong>
and
newline" display-name="test display name" />


  <c:if test="${!empty(collection)}">
    <ul>
      <c:forEach items="${collection}" var="item">
        <li>${item}</li>
      </c:forEach>
    </ul>
  </c:if>

</jsp:root>
