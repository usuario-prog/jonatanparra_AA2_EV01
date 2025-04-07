<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
    <div class="alert alert-danger">
        <h1>Error</h1>
        <p>Ha ocurrido un error en la aplicación</p>
        
        <c:if test="${not empty error}">
            <p><strong>Detalles:</strong> ${error}</p>
        </c:if>
        
        <c:if test="${not empty exception}">
            <p><strong>Excepción:</strong> ${exception.message}</p>
            <pre><c:forEach items="${exception.stackTrace}" var="trace">${trace}
</c:forEach></pre>
        </c:if>
    </div>
    
    <a href="<c:url value='/crud/list'/>" class="btn btn-primary">Volver al inicio</a>
</body>
</html>