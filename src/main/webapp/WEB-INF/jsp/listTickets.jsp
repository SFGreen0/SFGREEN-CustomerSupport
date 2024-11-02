<%@ taglib uri="http://jakarta.apache.org/jstl/core" prefix="c" %>
<html>
<head>
    <title>All Tickets</title>
</head>
<body>
<h2>Ticket List</h2>
<ul>
    <c:forEach var="ticket" items="${tickets}">
        <li>
            <a href="tickets/${ticket.id}">  <!-- Assuming `id` is the property name -->
                Ticket ID: ${ticket.id} - Title: ${ticket.subject}
            </a>
        </li>
    </c:forEach>
</ul>
</body>
</html>