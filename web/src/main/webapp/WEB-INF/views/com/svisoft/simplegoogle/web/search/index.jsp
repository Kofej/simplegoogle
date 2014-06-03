<%@ page contentType="text/html" pageEncoding="UTF-8" %>
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
</body>
</html>