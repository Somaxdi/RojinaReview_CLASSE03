<%--
  Created by IntelliJ IDEA.
  User: carlo
  Date: 27/06/2022
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% if(request.getSession().getAttribute("utente") != null)
    response.sendRedirect("./home");%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="./css/master.css">
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<div class="center">
    <h1>Create Account</h1>
    <form method="post" onsubmit="login()">
        <div class="form_input">
            <input type="text" name="nickname" required>
            <span></span>
            <label>Nickname</label>
        </div>
        <div class="form_input">
            <input type="email" name="email" required>
            <span></span>
            <label>Email</label>
        </div>
        <div class="form_input">
            <input type="password" name="password" required>
            <span></span>
            <label>Password</label>
        </div>
        <input type="submit" value="Create Account">
    </form>
</div>
<script>

</script>
</body>
</html>
