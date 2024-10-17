<%@ taglib uri="http://jakarta.apache.org/jstl/core" prefix="c" %>
<html>
<head>
    <title>View Ticket</title>
</head>
<body>
<h2>Ticket Details</h2>
<p><strong>ID:</strong> ${ticketId}</p>
<p><strong>Title:</strong> ${ticket.title}</p>
<p><strong>Description:</strong> ${ticket.description}</p>

<c:choose>
    <c:when test="${not empty ticket.attachments}">
        <h3>Attachments:</h3>
        <ul>
            <c:forEach var="attachment" items="${ticket.attachments}">
                <li>${attachment}</li>
            </c:forEach>
        </ul>
    </c:when>
    <c:otherwise>
        <p>No attachments available.</p>
    </c:otherwise>
</c:choose>
</body>
</html>