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

<div class="row form-group"></div>
<div class="row form-group"></div>
<div class="row form-group"></div>
<div class="row form-group"></div>


<div class="jumbotron jumbotron-fluid">
  <div class="container">
    <h1>Baza urlopów</h1>
  </div>
</div>

<div class="row form-group"></div>
<div class="row form-group"></div>
<div class="row form-group"></div>

<table class="table default">

  <thead>
  <tr>
    <th scope="col">ID</th>
    <th scope="col">ID pracownika</th>
    <th scope="col">Od</th>
    <th scope="col">Do</th>
    <th scope="col">Zaakceptowano</th>
  </tr>
  </thead>
  <tbody>

  <c:forEach var="tmpVacation" items="${VACATIONS_LIST}">

    <%-- definiowanie linkow--%>
    <c:url var="updateLink" value="WorkerServlet">
      <c:param name="command" value="LOAD"></c:param>
      <c:param name="vacationId" value="${tmpVacation.vacationId}"></c:param>
    </c:url>

    <c:url var="deleteLink" value="WorkerServlet">
      <c:param name="command" value="DELETE"></c:param>
      <c:param name="vacationId" value="${tmpVacation.vacationId}"></c:param>
    </c:url>

    <tr>
      <th scope="row">${tmpVacation.vacationId}</th>
      <td>${tmpVacation.userId}</td>
      <td>${tmpVacation.startDate}</td>
      <td>${tmpVacation.endDate}</td>
      <td>${tmpVacation.accepted}</td>
      <td><a href="${updateLink}">
        <button type="button" class="btn btn-success">Zmień dane</button>
      </a>
        <a href="${deleteLink}"
           onclick="if(!(confirm('Czy na pewno chcesz usunąć ten urlop?'))) return false">
          <button type="button" class="btn btn-danger">Usuń</button>
        </a></td>
    </tr>


  </c:forEach>
  </tbody>
</table>

<div class="row form-group"></div>
<div class="row form-group"></div>
<div class="row form-group"></div>

<div class="row">
  <div class="container-fluid">

    <div class="col-sm-9">
      <a href="index.html" class="btn btn-lg btn-primary" role="button" aria-disabled="true">Wróć do strony głównej</a>
    </div>
    <div class="col-sm-9">
      <p><a class="btn btn-primary btn-info" href="add_vacation_form.jsp" role="button">Dodaj urlop</a></p>
    </div>
  </div>
</div>


</body>
</html>

