<%@ page import="java.util.ArrayList" %>
<%@ page import="model.beans.Prodotto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% ArrayList<Prodotto> list = (ArrayList<Prodotto>)request.getAttribute("list");%>
<table>
    <tr>
        <%for(String s : Prodotto.fieldsName){%>
        <th><%=s%></th>
        <%}%>
        <th id="search" onclick="search()">🔍</th>
        <th id="add" onclick="add()">✖️</th>
    </tr>
        <%for(Prodotto a : list){%>
    <tr>
        <td>
            <%= a.getId()%>
        </td>
        <td>
            <%= a.getNome()%>
        </td>
        <td>
            <%= a.getDescrizione()%>
        </td>
        <td>
            <%= a.getDisponibilità()%>
        </td>
        <td>
            <%= a.getPrezzo()%>
        </td>
        <td>
            <%= a.getMediaVoto()%>
        </td>
        <td>
            <%= a.getNumeroVoti()%>
        </td>
        <td>
            <button>✏️ </button>
            <button>🗑️ </button>
        </td>
    </tr>

        <%}%>
