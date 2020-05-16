package com.utalk;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Contact")
public class Contact extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Contact() {
        super();
    }

    private static final String indexPagePath = "WebContent/contact.jsp";
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
                                                              IOException {
      response.setContentType("text/html");
      try {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource(indexPagePath);
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{resource});

        InputStream input = urlClassLoader.getResourceAsStream(indexPagePath);
        String result = input.toString();
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(result);
      } catch (Exception e) {
        response.getWriter().print(e);
      }
    }
  

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
