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
