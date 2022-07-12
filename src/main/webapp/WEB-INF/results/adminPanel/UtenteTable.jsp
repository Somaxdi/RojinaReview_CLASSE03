<%@ page import="java.util.ArrayList" %>
<%@ page import="model.beans.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% ArrayList< Utente > list = (ArrayList<Utente>)request.getAttribute("list");%>
<table>
    <tr>
        <%for(String s : Utente.fieldsName){%>
        <th><%=s%></th>
        <%}%>
        <th id="search" onclick="search()">🔍</th>
        <th id="add" onclick="add()">✖️</th>
    </tr>
        <%for(Utente a : list){%>
    <tr>
        <td>
            <%= a.getEmail()%>
        </td>
        <td>
            <%= a.getNickname()%>
        </td>
        <td>
            <%= a.getPassword()%>
        </td>
        <td>
            <%= a.getNome()%>
        </td>
        <td>
            <%= a.getCognome()%>
        </td>
        <td>
            <%= a.getEta()%>
        </td>
        <td>
            <button>✏️ </button>
            <button>🗑️ </button>
        </td>
    </tr>

        <%}%>

