<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
<link rel="stylesheet" href="./sitemap.css">
<meta charset="ISO-8859-1">
<title>Site Map</title>
</head>
<body>
<% Map<String,String> pages = new HashMap<String,String>(); 
pages.put("Profile", "Here you can edit your profile, change your password or update your photo.");
pages.put("Newsfeed", "You can find here your friends' posts.");
pages.put("Friends", "Here you can see your friends list and search for other friends.");
pages.put("Contact", "This page contains the information you need in order to find us.");
pages.put("Sitemap", "Here you can find the description of each page of the site.");
pages.put("About Us", "A list of contributors and their contact info can be found here.");
%>

<%out.println("<p id=\"sitemap-title\">uTalk Site Map</p>"); %>
<%for (Map.Entry<String, String> entry : pages.entrySet()) {
	out.println("<p id=\"page-name\">" + entry.getKey() + "</p> <p id=\"page-desc\"> - Description:\t" + entry.getValue()+"</p><br>");
} %>

</body>
</html>