<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Area utente</title>
    <link rel="stylesheet" href=".css/master.css">
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
<c:set var="utente" scope="page" value="${sessionScope['utente']}" />
<%@ include file="/WEB-INF/results/utente/userArea.jsp" %>
<%@ page import="rojinaReview.model.beans.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Utente u = (Utente) session.getAttribute("utente");%>
<section>
<h1>Aggiorna i tuoi dati:</h1>
<br>
<table>
    <tr>
        <th>
            <p>Email</p>
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
        <th>
            <p>Età</p>
        </th>
    </tr>
    <tr>
        <form id="userUpdate" method="POST" action="./userUpdateData">
            <td>
                <input type="text" value="<%=u.getEmail()%>" name="email">
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
                <input type="text" value="<%=u.getEta()%>" name="eta">
            </td>
            <td>
                <input type="button" value="Aggiorna" onclick="info()">
            </td>
        </form>
    </tr>
</table>
</section>
<script>
    function info() {
        let resoult = confirm("Cambiando i seguenti dati sarai disconnesso, Sei sicuro? \n(Potrai riloggare subito dopo!!)");
        if (resoult === true) {
            document.getElementById("userUpdate").submit();
        }
    }
</script>
</body>
</html>

