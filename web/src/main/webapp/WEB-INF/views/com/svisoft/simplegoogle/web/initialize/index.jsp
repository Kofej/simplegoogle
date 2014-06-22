<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.svisoft.simplegoogle.web.initialize.InitializeUrl" %>
<%@ page import="com.svisoft.simplegoogle.web.search.SearchUrl" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" type="text/css" href="<c:url value='/css/simplegoogle/initialize/index.css'/>"/>
</head>
<body>
<div class="container">
  <div class="f-logo"></div>
  <div class="f-index-content">
    <form action="<%=InitializeUrl.INDEX_URL%>" >
      <table class="inv">
        <tbody>
        <tr>
          <td class="inv border index-url-container">
            <input class="index-url" name="url" type="text" title="URL to index" value="<c:out value="${url}"/>" />
          </td>
          <td class="inv border index-depth-container">
            <input class="index-depth" name="depth" value="1" title="Index depth" />
          </td>
          <td class="inv index-submit-container">
            <input class="index-submit" type="submit" value=" " />
          </td>
        </tr>
        <tr>
          <td>
            <a href="<%=SearchUrl.SEARCH_URL%>">Go to search.</a>
          </td>
        </tr>
        </tbody>
      </table>
    </form>
  </div>
</div>
</body>
</html>