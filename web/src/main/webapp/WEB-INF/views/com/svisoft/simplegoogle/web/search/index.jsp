<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.svisoft.simplegoogle.web.search.SearchUrl" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" type="text/css" href="<c:url value='/css/simplegoogle/search/index.css'/>"/>
</head>
<body>
  <div class="container">
    <div class="f-logo"></div>
    <div class="f-search-content">
      <form action="<%=SearchUrl.SEARCH_URL%>" >
        <table class="inv">
          <tbody>
            <tr>
              <td class="inv border search-input-container">
                <input class="search-input" name="query" type="text" title="Query to search" />
              </td>
              <td class="inv search-submit-container">
                <input class="search-submit" type="submit" value=" " />
              </td>
            </tr>
          </tbody>
        </table>
      </form>
    </div>
  </div>
</body>
</html>