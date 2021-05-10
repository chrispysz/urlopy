<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*" %>
<html>
<head>
    <title>UrloPol</title>
</head>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
<link type="text/css" rel="stylesheet" href="bootstrap.min.css">

<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand" href="index.html">UrloPol</a>
</nav>
</br>
</br>
</br>
<%
    if (request.getAttribute("error") != null) {
        String message = (String) request.getAttribute("error");

        out.write(" Błąd! " + message);
    }else{
        String message = (String) request.getAttribute("success");

        out.write(" Sukces! " + message);
    }


%>
</body>
</html>