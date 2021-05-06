<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="UTF-8">
    <title>Admin log panel</title>
    <meta charset="utf-8">
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
                <a class="navbar-brand" href="index.html">UrloPol</a>
            </div>

        </div>
    </div>
</nav>

<div class="row form-group"></div>
<div class="row form-group"></div>
<div class="row form-group"></div>

<div class="jumbotron">
    <div class="container">
        <h1>Edytuj dane</h1>

        <form action="WorkerServlet" method="get">
            <input type="hidden" name="command" value="ADD">
            <div class="form-group">
                <label for="Start">Od</label>
                <input type="text" class="form-control" name="startDateInput" value="${VACATION.startDate}"/>
            </div>
            <div class="form-group">
                <label for="End">Do</label>
                <input type="text" class="form-control" name="endDateInput" value="${VACATION.endDate}"/>
            </div>
            <button type="submit" class="btn btn-info">Dodaj</button>
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
