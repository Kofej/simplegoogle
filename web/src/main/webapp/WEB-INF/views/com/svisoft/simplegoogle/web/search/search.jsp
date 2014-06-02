<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <body>
    <div>
      <c:forEach var="document" items="${documents}">
        <div><c:out value="${document.title}" /></div>
        <a href="<c:out value="${document.id}" />" ><c:out value="${document.id}" /></a>
      </c:forEach>
    </div>
  </body>
</html>