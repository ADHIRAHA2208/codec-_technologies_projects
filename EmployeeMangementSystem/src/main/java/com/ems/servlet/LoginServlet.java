package com.ems.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Admin login
        if ("admin".equals(username) && "admin123".equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("role", "admin");
            response.sendRedirect("adminDashboard.jsp");
        }
        // User login
        else if ("user".equals(username) && "user123".equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("role", "user");
            response.sendRedirect("userDashboard.jsp");
        }
        // Invalid login
        else {
            response.sendRedirect("login.jsp?error=invalid");
        }
    }
}
