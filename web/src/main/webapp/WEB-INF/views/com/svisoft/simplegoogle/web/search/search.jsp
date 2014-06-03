<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.svisoft.simplegoogle.web.search.SearchUrl" %>
<!DOCTYPE html>
<html>
  <body>
    <div>
      <form action="<%=SearchUrl.SEARCH_URL%>">
        <label>Enter search query</label>
        <div>
          <input name="query" type="text">
          <button type="submit">Search</button>
        </div>
      </form>
    </div>
    <div>
      <c:choose>
        <c:when test="${not empty documents}">
          <c:forEach var="document" items="${documents}">
            <div>
              <div><c:out value="${document.title}" /></div>
              <a href="<c:out value="${document.id}" />" ><c:out value="${document.id}" /></a>
              &nbsp;
            </div>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <c:out value="cann't find" />
        </c:otherwise>
      </c:choose>
    </div>
  </body>
</html>