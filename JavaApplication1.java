package javaApplication1;

import java.sql.*;

public class JavaApplication1 {

    public static void main(String[] args) {
        String usuario = "root";
        String password = "admin";
        String url = "jdbc:mysql://localhost:3306/prueva_php"; 
        Connection conexion;
        Statement statement;

        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conectar a la base de datos
            conexion = DriverManager.getConnection(url, usuario, password);
            statement = conexion.createStatement();

            // Ejecutar la consulta SQL
            String query = "INSERT INTO USUARIOS (NOMBRE, EMAIL, PASSWORD) VALUES ('ABC', 'ABC@gmail.com', 'password123')";
            statement.executeUpdate(query);

            System.out.println("Datos insertados correctamente.");

            // Cerrar recursos
            statement.close();
            conexion.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se pudo encontrar el driver de MySQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error: Problema con la conexión o la consulta SQL.");
            e.printStackTrace();
        }
    }
}
        
         
        
        
    
    

