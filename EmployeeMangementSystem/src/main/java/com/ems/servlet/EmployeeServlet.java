package com.ems.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.ems.dao.EmployeeDAO;
import com.ems.model.Employee;

public class EmployeeServlet extends HttpServlet {

 EmployeeDAO dao = new EmployeeDAO();
 
 protected void doGet(HttpServletRequest req, HttpServletResponse res)
	        throws IOException, ServletException {

	    System.out.println("EmployeeServlet called");

	    try {
	        req.setAttribute("list", dao.getAll());
	        req.getRequestDispatcher("jsp/employee.jsp")
	           .forward(req, res);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}



 protected void doPost(HttpServletRequest req, HttpServletResponse res)
 throws IOException {
  try {
   Employee e = new Employee();
   e.setName(req.getParameter("name"));
   e.setEmail(req.getParameter("email"));
   e.setRole(req.getParameter("role"));
   e.setSalary(Double.parseDouble(req.getParameter("salary")));
   dao.save(e);
   res.sendRedirect("EmployeeServlet");
  } catch(Exception ex) { ex.printStackTrace(); }
 }
}
