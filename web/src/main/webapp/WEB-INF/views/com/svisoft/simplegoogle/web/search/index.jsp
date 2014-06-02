<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.svisoft.simplegoogle.web.search.SearchUrl" %>
<!DOCTYPE html>
<html>
  <body>
    <form action="<%=SearchUrl.SEARCH_URL%>">
      <input name="query" type="text">

      <input type="submit" value="Search" />
    </form>
  </body>
</html>