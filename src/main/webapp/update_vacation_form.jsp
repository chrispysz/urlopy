<%--
  Created by IntelliJ IDEA.
  User: macmini2
  Date: 09/04/2020
  Time: 11:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*" %>
<html>
<head>
    <title>Zmiana danych telefonu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link type="text/css" rel="stylesheet" href="bootstrap.min.css">
    <link rel="icon" href="images/favicon-16x16.png">
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">

            <div class="style padding: 25 px">
                <a class="navbar-brand" href="index.html">IB-GSM</a>
            </div>

        </div>
    </div>
</nav>

<div class="row form-group"></div>
<div class="row form-group"></div>
<div class="row form-group"></div>

<div class="jumbotron">
    <div class="container">
        <h1>Zmień dane telefonu</h1>

        <form action="AdminServlet" method="get">
            <input type="hidden" name="command" value="UPDATE"/>
            <input type="hidden" name="resortID" value="${RESORT.id}"/>
            <div class="form-group">
                <label for="Name">Nazwa</label>
                <input type="text" class="form-control" name="nameInput" value="${RESORT.name}"/>
            </div>
            <div class="form-group">
                <label for="State">Stan</label>
                <input type="text" class="form-control" name="stateInput" value="${RESORT.state}"/>
            </div>
            <div class="form-group">
                <label for="Summit">Góra</label>
                <input type="text" class="form-control" name="summitInput" value="${RESORT.summit}"/>
            </div>
            <div class="form-group">
                <label for="Base">Dół</label>
                <input type="text" class="form-control" name="baseInput" value="${RESORT.base}"/>
            </div>
            <div class="form-group">
                <label for="Lifts">Wyciągi</label>
                <input type="text" class="form-control" name="liftsInput" value="${RESORT.lifts}"/>
            </div>
            <div class="form-group">
                <label for="Runs">Trasy</label>
                <input type="text" class="form-control" name="runsInput" value="${RESORT.runs}"/>
            </div>
            <div class="form-group">
                <label for="Acres">Pow. całkowita</label>
                <input type="text" class="form-control" name="acresInput" value="${RESORT.acres}"/>
            </div>
            <div class="form-group">
                <label for="GreenAcres">Pow. zielonych</label>
                <input type="text" class="form-control" name="greenAcresInput" value="${RESORT.greenAcres}"/>
            </div>
            <div class="form-group">
                <label for="BlueAcres">Pow. niebieskich</label>
                <input type="text" class="form-control" name="blueAcresInput" value="${RESORT.blueAcres}"/>
            </div>
            <div class="form-group">
                <label for="BlackAcres">Pow. czarnych</label>
                <input type="text" class="form-control" name="blackAcresInput" value="${RESORT.blackAcres}"/>
            </div>
            <div class="form-group">
                <label for="Lat">Szerokość geo.</label>
                <input type="text" class="form-control" name="latInput" value="${RESORT.lat}"/>
            </div>
            <div class="form-group">
                <label for="Lon">Długość geo.</label>
                <input type="text" class="form-control" name="lonInput" value="${RESORT.lon}"/>
            </div>
            <button type="submit" class="btn btn-success">Zmień dane</button>
        </form>
    </div>
</div>

<div class="row form-group"></div>
<div class="row form-group"></div>

<div class="row">
    <div class="container-fluid">

        <div class="col-sm-9">
            <a href="AdminServlet" class="btn btn-lg btn-primary" role="button" aria-disabled="true">Wróć do zestawienia</a>
        </div>
    </div>
</div>

</body>
</html>
