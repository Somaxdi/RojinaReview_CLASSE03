<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Area videogiocatore</title>
    <link rel="stylesheet" href="./static/css/master.css">
    <style>
        section{
            margin: 25%;
            color: white;
            height: fit-content;
            padding: 2%;
            background: #24262b;
        }
        table{
            color: white;
        }
    </style>
</head>
<body>
<c:set var="videogiocatore" scope="page" value="${sessionScope['videogiocatore']}" />
<%@ include file="/WEB-INF/results/videogiocatorePages/userArea.jsp" %>
<%@ page import="rojinaReview.model.beans.Videogiocatore" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Videogiocatore u = (Videogiocatore) session.getAttribute("videogiocatore");%>
<section>
<h1>Aggiorna i tuoi dati:</h1>
<br>
<table>
    <tr>
        <th>
            <p>Email</p>
        </th>
        <th>
            <p>Password</p>
        </th>
        <th>
            <p>Nickname</p>
        </th>
        <th>
            <p>Nome</p>
        </th>
        <th>
            <p>Cognome</p>
        </th>
    </tr>
    <tr>
        <form id="userUpdate" method="POST" action="./userUpdateData">
            <td>
                <input type="text" value="<%=u.getEmail()%>" name="email">
            </td>
            <td>
                <input type="password"  name="password">
            </td>
            <td>
                <input type="text" value="<%=u.getNickname()%>" name="nickname">
            </td>
            <td>
                <input type="text" value="<%=u.getNome()%>" name="nome">
            </td>
            <td>
                <input type="text" value="<%=u.getCognome()%>" name="cognome">
            </td>
            <td>
                <input type="submit" value="Aggiorna">
            </td>
        </form>
    </tr>
</table>
</section>
</body>
</html>

