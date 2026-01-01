
<%
String role = (String) session.getAttribute("role");
if(role == null){
    response.sendRedirect("../login.jsp");
}
%>


<%@ page import="java.util.*,com.ems.model.Employee" %>
<%
 String role = (String)session.getAttribute("role");
%>

<link rel="stylesheet" href="../css/style.css">

<h2>Employee Management System</h2>

<% if("ADMIN".equals(role)) { %>
<form action="../EmployeeServlet" method="post">
<input name="name" placeholder="Name">
<input name="email" placeholder="Email">
<input name="role" placeholder="Role">
<input name="salary" placeholder="Salary">
<button>Add</button>
</form>
<% } %>

<table>
<tr>
<th>ID</th><th>Name</th><th>Email</th><th>Role</th><th>Salary</th>
<% if("ADMIN".equals(role)) { %><th>Action</th><% } %>
</tr>

<%
 List<Employee> list = (List<Employee>)request.getAttribute("list");
 for(Employee e:list){
%>
<tr>
<td><%=e.getId()%></td>
<td><%=e.getName()%></td>
<td><%=e.getEmail()%></td>
<td><%=e.getRole()%></td>
<td><%=e.getSalary()%></td>
<% if("ADMIN".equals(role)) { %>
<td><a href="delete?id=<%=e.getId()%>">Delete</a></td>
<% } %>
</tr>
<% } %>
</table>
