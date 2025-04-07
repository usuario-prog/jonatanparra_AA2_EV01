<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Listado de Registros</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">
    <h1 class="mb-4">Listado de Registros</h1>
    
    <a href="<c:url value='/crud/create'/>" class="btn btn-primary mb-3">Nuevo Registro</a>
    
    <table class="table table-striped">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${listaRegistros}" var="registro">
                <tr>
                    <td>${registro.id}</td>
                    <td>${registro.nombre}</td>
                    <td>${registro.descripcion}</td>
                    <td>
                        <a href="<c:url value='/crud/edit?id=${registro.id}'/>" class="btn btn-warning btn-sm">Editar</a>
                        <a href="<c:url value='/crud/delete?id=${registro.id}'/>" class="btn btn-danger btn-sm">Eliminar</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <c:if test="${not empty mensaje}">
        <div class="alert alert-info mt-3">${mensaje}</div>
    </c:if>
</body>
</html>