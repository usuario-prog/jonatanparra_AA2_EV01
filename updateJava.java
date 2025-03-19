package javaApplication1;

import java.sql.*;

public class updateJava {  // Nombre de la clase coincide con el archivo
    public static void main(String[] args) {
        // Declaración de variables
        String usuario = "root";
        String password = "admin";
        String url = "jdbc:mysql://localhost:3306/prueva_php";
        Connection conexion = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conectar a la base de datos
            conexion = DriverManager.getConnection(url, usuario, password);
            statement = conexion.createStatement();

            // Actualizar un registro en la tabla USUARIOS
            String queryUpdate = "UPDATE USUARIOS SET EMAIL = 'nuevo_@gmail.com', PASSWORD = 'nueva_password' WHERE NOMBRE = 'ABC'";
            int filasActualizadas = statement.executeUpdate(queryUpdate);

            System.out.println("Filas actualizadas: " + filasActualizadas);

            // Consultar todos los registros de la tabla USUARIOS para verificar los cambios
            rs = statement.executeQuery("SELECT * FROM USUARIOS");

            // Recorrer y mostrar los resultados
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nombre = rs.getString("NOMBRE");
                String email = rs.getString("EMAIL");
                String pass = rs.getString("PASSWORD");

                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Email: " + email + ", Password: " + pass);
            }

        } catch (ClassNotFoundException e) {
            // Manejo de ClassNotFoundException (driver no encontrado)
            System.out.println("Error: No se pudo encontrar el driver de MySQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            // Manejo de SQLException (errores de conexión o consulta SQL)
            System.out.println("Error: Problema con la conexión o la consulta SQL.");
            e.printStackTrace();
        } finally {
            // Cerrar recursos en el bloque finally para asegurar que se ejecute siempre
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos.");
                e.printStackTrace();
            }
        }
    }
}