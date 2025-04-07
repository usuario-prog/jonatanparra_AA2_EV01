package crudBasico;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CrudServlet", urlPatterns = {"/crud", "/crud/*"})
public class CrudServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo(); // Obtiene la parte después de /crud/
        
        try {
            switch (action) {
                case "/create":
                    // Lógica para crear
                    break;
                case "/edit":
                    // Lógica para editar
                    break;
                case "/delete":
                    // Lógica para eliminar
                    break;
                case "/list":
                default:
                    // Lógica para listar (página principal)
                    request.getRequestDispatcher("/WEB-INF/views/listar.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            // Manejo de errores
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}