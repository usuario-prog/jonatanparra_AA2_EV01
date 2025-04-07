<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Editar Registro</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">
    <h1 class="mb-4">Editar Registro</h1>
    
    <form action="<c:url value='/crud/edit'/>" method="POST">
        <input type="hidden" name="id" value="${registro.id}">
        
        <div class="mb-3">
            <label for="nombre" class="form-label">Nombre:</label>
            <input type="text" class="form-control" id="nombre" name="nombre" 
                   value="${registro.nombre}" required>
        </div>
        
        <div class="mb-3">
            <label for="descripcion" class="form-label">Descripción:</label>
            <textarea class="form-control" id="descripcion" name="descripcion" 
                      rows="3">${registro.descripcion}</textarea>
        </div>
        
        <button type="submit" class="btn btn-primary">Actualizar</button>
        <a href="<c:url value='/crud/list'/>" class="btn btn-secondary">Cancelar</a>
    </form>
</body>
</html>