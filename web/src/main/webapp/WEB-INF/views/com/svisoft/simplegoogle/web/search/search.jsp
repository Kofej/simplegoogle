<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <body>
    <c:forEach var="document" items="${documents}">
      <c:out value="${document.id}" />
    </c:forEach>
  </body>
</html>