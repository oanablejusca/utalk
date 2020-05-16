package com.utalk;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SiteInfo")
public class SiteInfo extends HttpServlet {
	   @Override
	   public void doGet(HttpServletRequest request, HttpServletResponse response)
	               throws IOException, ServletException {
	      response.setContentType("text/html;charset=UTF-8");
	      PrintWriter out = response.getWriter();
	 
	      HttpSession session = request.getSession();
	      Integer accessCount;
	      synchronized(session) {
	         accessCount = (Integer)session.getAttribute("accessCount");
	         if (accessCount == null) {
	            accessCount = 0;
	         } else {
	            accessCount = new Integer(accessCount + 1);
	         }
	         session.setAttribute("accessCount", accessCount);
	      }
	 
	      try {
	         out.println("<!DOCTYPE html>");
	         out.println("<html>");
	         out.println("<head>"
	         		+ "<link href=\"https://fonts.googleapis.com/css?family=Roboto\" rel=\"stylesheet\">"+
	        		 "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
	         out.println("<title>Session Test Servlet</title></head><body style=\"font-family: \'Roboto\'; font-size: 0.8rem;\">");
	         out.println("<p>You have accessd this site " + accessCount + " times in this session.</p>");
	         out.println("<p>(Session ID is " + session.getId() + ")</p>");
	 
	         out.println("<p>(Session creation time is " +
	               new Date(session.getCreationTime()) + ")</p>");
	         out.println("<p>(Session last access time is " +
	               new Date(session.getLastAccessedTime()) + ")</p>");
	         out.println("<p>(Session max inactive interval  is " +
	               session.getMaxInactiveInterval() + " seconds)</p>");
	         out.println("</body></html>");
	      } finally {
	         out.close();  
	      }
	   }
	}