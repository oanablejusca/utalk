<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
<link rel="stylesheet" href="./aboutus.css">

<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% Map<String,String> contributors = new HashMap<String,String>();
contributors.put("Marinel A.", "github.com/amarieimarinel97 / marinel.amariei97@yahoo.com");
contributors.put("Oana B.", "github.com/oanablejusca / oana.blejusca97@gmail.com");
contributors.put("Denis C.", "github.com/MarianDenis / denis.cobanu97@yahoo.com");%>


<%out.println("<p id=\"aboutus-title\">uTalk About Us</p>"); %>
<%out.println("<p>uTalk is a sideproject we were forced to do for our IT course trough which we <br>try to understand deeper how these big social media platforms work.</p>"); %>
<%out.println("<p>Here you can see a list with contributors of this project and their contact info."); %>
<%for (Map.Entry<String, String> entry : contributors.entrySet()) {
	out.println("<p id=\"contrib-name\">" + entry.getKey() + "</p>"
+" <p id=\"contrib-desc\"> - Contact:\t" + entry.getValue()+"</p><br>");
} %>


</body>
</html>