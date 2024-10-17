<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.SessionRegistry" %>
<!DOCTYPE html>
<html>
<head>
    <title>Active Sessions</title>
</head>
<body>
<h2>Active Sessions</h2>
<ul>
    <c:forEach var="session" items="${SessionRegistry.sessions}">
        <li>${session.key}</li>
    </c:forEach>
</ul>
</body>
</html>