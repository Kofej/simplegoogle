<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.svisoft.simplegoogle.web.search.SearchUrl" %>
<!DOCTYPE html>
<html>
<head>
  <link rel="shortcut icon" type="image/x-icon" href="<c:url value='/favicon.ico' />" />
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

  <link rel="stylesheet" type="text/css" href="<c:url value='/css/simplegoogle/search/search.css'/>"/>
</head>
<body>
  <table class="container">
    <tbody>
      <tr>
        <td>
          <div class="f-logo-content"></div>
          <div class="f-search-content">
            <form action="<%=SearchUrl.SEARCH_URL%>">
              <table class="inv">
                <tbody>
                <tr>
                  <td class="inv border search-input-container">
                    <input class="search-input" name="query" type="text" title="Query to search" value="<c:out value="${query}"/>" />
                  </td>
                  <td class="inv search-submit-container">
                    <input class="search-submit" type="submit" value=" " />
                  </td>
                </tr>
                </tbody>
              </table>
            </form>
          </div>
        </td>
      </tr>
    </tbody>
  </table>
  <div class="search-result-container">
    <c:if test="${not empty documents}">
      <ol>
        <c:forEach var="document" items="${documents}">
          <li class="search-result-document">
            <h3 class="search-result-title">
              <a href="<c:out value="${document.id}" />" >
                <c:out value="${document.title}"/>
              </a>
            </h3>
            <div class="search-result-url">
              <cite><c:out value="${document.id}" /></cite>
              <div class="search-result-text">
                <c:out value="${document.text}"/>
              </div>
            </div>
            <div>
              &nbsp;
            </div>
          </li>
        </c:forEach>
      </ol>
    </c:if>
  </div>
</body>
</html>