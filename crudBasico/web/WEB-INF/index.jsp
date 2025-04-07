<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Sistema CRUD</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
        }
        .btn {
            display: inline-block;
            padding: 10px 15px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin: 10px 5px;
        }
        .btn:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Bienvenido al Sistema CRUD</h1>
        
        <div style="text-align: center; margin-top: 30px;">
            <a href="crud/list" class="btn">Acceder al CRUD</a>
            <a href="formulario.jsp" class="btn">Ir al Formulario</a>
        </div>
        
        <div style="margin-top: 40px; text-align: center;">
            <h3>Operaciones disponibles:</h3>
            <p>• Crear nuevos registros</p>
            <p>• Leer/Listar registros existentes</p>
            <p>• Actualizar registros</p>
            <p>• Eliminar registros</p>
        </div>
    </div>
</body>
</html>