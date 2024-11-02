<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Ticket</title>
</head>
<body>
<h2>Create a New Ticket</h2>
<form action="TicketServlet" method="post">
    <label for="title">Title:</label>
    <input type="text" id="title" name="title" required><br><br>

    <label for="description">Description:</label>
    <textarea id="description" name="description" required></textarea><br><br>

    <input type="submit" value="Submit Ticket">
</form>
</body>
</html>