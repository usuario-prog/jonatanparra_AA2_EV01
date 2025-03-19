package javaApplication1;

import java.sql.*;

public class SearchJava {

    public static void main(String[] args) {
        // Declaración de variables
        String usuario = "root";
        String password = "admin";
        String url = "jdbc:mysql://localhost:3306/prueva_php";
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conectar a la base de datos
            conexion = DriverManager.getConnection(url, usuario, password);

            // Definir la consulta SQL para buscar un usuario por nombre o email
            String sql = "SELECT * FROM USUARIOS WHERE NOMBRE = ? OR EMAIL = ?";
            preparedStatement = conexion.prepareStatement(sql);

            // Parámetros de búsqueda (puedes cambiar estos valores según lo que quieras buscar)
            String nombreABuscar = "ABC";
            String emailABuscar = "ABC@gmail.com";

            // Establecer los parámetros en la consulta
            preparedStatement.setString(1, nombreABuscar);
            preparedStatement.setString(2, emailABuscar);

            // Ejecutar la consulta
            rs = preparedStatement.executeQuery();

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
                if (preparedStatement != null) preparedStatement.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos.");
                e.printStackTrace();
            }
        }
    }
}