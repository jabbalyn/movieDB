<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.util.List" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="movieDB.core.Actor" %>
<%@ page import="movieDB.db.UtilsSQL" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Actor Search Results</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css" >
</head>
<body>
<% 
	List<Actor> actors = (List<Actor>) request.getAttribute("actors");
	if ((actors == null) || (actors.size() == 0)) {
		out.println("No actors were found with that criteria.<br>");
	}
%>
<table border="1">
	<tr>
		<th></th>
		<th>Actor</th>
		<th>ID</th>
		<th>Born</th>
	</tr>
<% 
	for (Actor nextActor: actors) {
		out.println("<tr>");
		out.println("<td></td>"); // placeholder for person's picture
		out.println("<td>" + nextActor.getName() + "</td>");
		out.println("<td>" + nextActor.getID() + "</td>");
		out.println("<td>" + UtilsSQL.getHumanDate_DefaultStyle(nextActor.getBirthDate()) + "</td>");
		out.println("</tr>");	
	}
%>
</table>

</body>
</html>