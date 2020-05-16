<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
<link rel="stylesheet" href="./contact.css">
<meta charset="ISO-8859-1">
<title>Contact</title>
</head>
<%! String date=new java.text.SimpleDateFormat("yyyy").format(new java.util.Date());%>
<%! int ourPhone=(int) (Math.random() * 100000)+100000; %>
<%! String email = "contact@utalk.com"; %>
<%! String address = "Victory Square, Bucharest, Romania"; %>
<body>
<%out.println("<p id=\"contact-title\">uTalk Contact</p>"); %>
<%out.println("<p>You can find us by: </p>"); %>
<%out.println("<p id=\"contact-email\">eMail: "); %><%=email %> <%out.println("</p>"); %>
<%out.println("<p id=\"contact-tel\">Telephone: +4 074 "); %> <%=ourPhone%> <%out.println("</p>"); %>
<%out.println("<p id=\"contact-address\">Headquarters: "); %> <%=address %> <%out.println("</p>"); %>
<%out.println("<p id=\"contact-copyrights\">All rights reserved. uTalk "); %> <%=date %> <%out.println("</p>"); %>
</body>
</html>