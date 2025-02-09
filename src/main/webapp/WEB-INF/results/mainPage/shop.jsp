<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shop - Rojina</title>
    <link rel="stylesheet" href="./static/css/notizie.css">
    <link rel="stylesheet" href="./static/css/navebar.css">
    <link rel="stylesheet" href="./static/css/master.css">
</head>
<body>
<%@ include file="/WEB-INF/results/utilities/navebar.jsp" %>

<section class="notizie">

    <%@ include file="/WEB-INF/results/utilities/filter.jsp" %>

    <section class="articoli" id="wrap">
        <c:forEach items="${requestScope['prodotti']}" var="prodotto">
            <div class = "articolo" id="${prodotto.id}">
                <a href="/Rojina_Review_war/getResource?type=shop&id=${prodotto.id}">
                <img src = "${prodotto.immagine}" alt = "copertina" decoding="async">
                <div class = "articolo-content">
                    <h2>${prodotto.nome}</h2>
                    <p>${prodotto.prezzo} €</p>
                    <p class="parere">${prodotto.mediaVoto}</p>
                </div>
                </a>
            </div>
        </c:forEach>
    </section>

</section>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript" src="/Rojina_Review_war/static/js/navebar.js"></script>
<script type="text/javascript" src="/Rojina_Review_war/static/js/filter.js"></script>
</body>
</html>
