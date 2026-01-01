<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%
    HttpSession sessionUser = request.getSession(false);
    if (sessionUser == null || sessionUser.getAttribute("role") == null
            || !"user".equals(sessionUser.getAttribute("role"))) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>User Dashboard</title>
    <style>
        body {
            font-family: Arial;
            background: #f4f6f9;
        }
        .card {
            margin: 80px auto;
            width: 350px;
            padding: 20px;
            background: white;
            text-align: center;
            border-radius: 8px;
            box-shadow: 0 0 10px gray;
        }
        a {
            display: inline-block;
            margin-top: 15px;
            background: red;
            color: white;
            padding: 8px 14px;
            text-decoration: none;
            border-radius: 4px;
        }
    </style>
</head>
<body>

<div class="card">
    <h2>Welcome User 👋</h2>
    <p>Login Successful</p>
    <p>Role: USER</p>
    <a href="logoutServlet">Logout</a>
</div>

</body>
</html>
