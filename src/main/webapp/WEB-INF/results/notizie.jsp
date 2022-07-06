<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Notizie - Rojina</title>
    <link rel="stylesheet" href="./css/notizie.css">
    <link rel="stylesheet" href="./css/navebar.css">
    <link rel="stylesheet" href="./css/master.css">

</head>
<body>
<%@ include file="/WEB-INF/results/navebar.jsp" %>
    <section class="notizie" id="wrap">

        <h1>Latest News</h1>

        <%@ include file="/WEB-INF/results/filterArticol.jsp" %>

        <section class="articoli">
            <c:forEach items="${applicationScope['notizie']}" var="articolo">
                    <div class = "articolo" id="${articolo.id}">
                        <a href="/Rojina_Review_war/getResource?type=notizia&id=${articolo.id}">
                        <img src = "${articolo.immagine}" alt = "copertina" decoding="async">
                        <div class = "articolo-content">
                            <h2>${articolo.titolo}</h2>
                            <p>${fn:substring(articolo.testo, 0, 50)}</p>
                        </div>
                        </a>
                    </div>
            </c:forEach>
        </section>

    </section>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript" src="/Rojina_Review_war/js/navebar.js"></script>
<script type="text/javascript" src="/Rojina_Review_war/js/filter.js"></script>
<script type="text/javascript">
    var triggered=false
    function yHandler(){
        var wrap = document.getElementById('wrap');
        var contentHeight = wrap.offsetHeight;
        var yOffset = window.pageYOffset;
        var y = yOffset + window.innerHeight;
        document.getElementById('size').innerHTML = y +"|"+ contentHeight;
        if(y > contentHeight) {
            if(triggered)
                return;
            triggered=true;
            var a = document.getElementsByClassName('articolo');
            var l = a[a.length-1].getAttribute("id");
            var p = document.getElementById('pButton').innerHTML;
            var t = document.getElementById('tButton').innerHTML;
            var o = document.getElementById('sButton').innerHTML;
            var r = "no";
            $(document).ready(function () {
                $.getJSON({
                    url: "/Rojina_Review_war/news",
                    type: "post",
                    data: {"lastID":l,"reset":r,"piattaforma" : p,"tipologia": t,"ordine" : o},
                    error: function (xhr, status, error) {
                        //alert("error");
                    },
                    success: function (data) {
                        var articoli = document.getElementsByClassName('articoli')[0];
                        var newA ="";
                        for (d in data) {
                            var articolo = data[d];
                            var titolo = articolo.titolo;
                            var id = articolo["id"];
                            var immagine= articolo.immagine;
                            var testo = articolo.testo;
                            var a =
                                "<div class = \"articolo\" id=\""+id+"\">" +
                                "<a href='/Rojina_Review_war/getResource?type=notizia&id="+ id +"'>"+
                                "<img src = '" +immagine +"' alt =\"copertina\" decoding=\"async\">" +
                                "<div class = \"articolo-content\">" +
                                "<h2>" + titolo + "</h2>" +
                                "<p>" + testo + "</p>" +
                                "</div>" +
                                "</div>" +
                                "</a>";
                            newA += a;
                        }
                        articoli.innerHTML = articoli.innerHTML + newA;
                    }
                });
            });
            triggered=false;
        }
    }

    window.onscroll = yHandler;

    function filter() {
        var p = document.getElementById('pButton').innerHTML.toString();
        var t = document.getElementById('tButton').innerHTML.toString();
        var o = document.getElementById('sButton').innerHTML.toString();
        var r = "yes";
        $(document).ready(function () {
            $.getJSON({
                url: "/Rojina_Review_war/news",
                type: "post",
                data: {"reset": r, "piattaforma": p, "tipologia": t, "ordine": o},
                error: function (xhr, status, error) {
                    //alert("error");
                },
                success: function (data) {

                    var articoli = document.getElementsByClassName('articoli')[0];
                    var newA = "";
                    for (d in data) {
                        var articolo = data[d];
                        var titolo = articolo.titolo;
                        var id = articolo["id"];
                        var immagine = articolo.immagine;
                        var testo = articolo.testo;
                        var a =
                            "<div class = \"articolo\" id=\""+id+"\">" +
                            "<a href='/Rojina_Review_war/getResource?type=notizia&id=" + id + "'>" +
                            "<img src = '" + immagine + "' alt =\"copertina\" decoding=\"async\">" +
                            "<div class = \"articolo-content\">" +
                            "<h2>" + titolo + "</h2>" +
                            "<p>" + testo + "</p>" +
                            "</div>" +
                            "</div>" +
                            "</a>";
                        newA += a;
                    }
                    articoli.innerHTML = newA;
                }
            });
        });
    }
</script>
</body>
</html>
