package crudBasico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class formularioClientes extends JFrame {
    private JTextField txtCodigo, txtNombre, txtApellido, txtPassword;
    private JButton btnGuardar, btnBuscar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;

    public formularioClientes() {
        setTitle("CRUD de Clientes");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel de formulario
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelForm.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        panelForm.add(txtCodigo);

        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panelForm.add(txtApellido);

        panelForm.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        panelForm.add(txtPassword);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnGuardar = new JButton("Guardar");
        btnBuscar = new JButton("Buscar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Configuración de la tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");
        
        tablaUsuarios = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);

        // Eventos
        btnGuardar.addActionListener(this::guardarCliente);
        btnBuscar.addActionListener(this::buscarCliente);
        btnActualizar.addActionListener(this::actualizarCliente);
        btnEliminar.addActionListener(this::eliminarCliente);
        btnLimpiar.addActionListener(this::limpiarCampos);
        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> cargarDatosSeleccionados());

        // Diseño principal
        setLayout(new BorderLayout());
        add(panelForm, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Cargar datos iniciales
        cargarDatos();
    }

    private void guardarCliente(ActionEvent e) {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String password = txtPassword.getText();

        if (nombre.isEmpty() || apellido.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO usuarios (nombre, apellido, password) VALUES (?, ?, ?)";
        
        try (Connection conn = conexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
            
            // Obtener el ID generado
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    txtCodigo.setText(String.valueOf(generatedKeys.getInt(1)));
                }
            }
            
            JOptionPane.showMessageDialog(this, "Cliente guardado exitosamente");
            cargarDatos();
            limpiarCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarCliente(ActionEvent e) {
        String busqueda = JOptionPane.showInputDialog(this, "Ingrese nombre o apellido a buscar:");
        if (busqueda == null || busqueda.trim().isEmpty()) return;

        modeloTabla.setRowCount(0); // Limpiar tabla
        
        String sql = "SELECT * FROM usuarios WHERE nombre LIKE ? OR apellido LIKE ?";
        
        try (Connection conn = conexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + busqueda + "%");
            pstmt.setString(2, "%" + busqueda + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    modeloTabla.addRow(new Object[]{
                        rs.getInt("codigo"),
                        rs.getString("nombre"),
                        rs.getString("apellido")
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarCliente(ActionEvent e) {
        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para actualizar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int codigo = Integer.parseInt(txtCodigo.getText());
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String password = txtPassword.getText();

        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, password = ? WHERE codigo = ?";
        
        try (Connection conn = conexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setString(3, password);
            pstmt.setInt(4, codigo);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente");
                cargarDatos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el cliente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCliente(ActionEvent e) {
        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro de eliminar este cliente?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) return;

        int codigo = Integer.parseInt(txtCodigo.getText());
        String sql = "DELETE FROM usuarios WHERE codigo = ?";
        
        try (Connection conn = conexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, codigo);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente");
                cargarDatos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el cliente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        String sql = "SELECT * FROM usuarios";
        
        try (Connection conn = conexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getInt("codigo"),
                    rs.getString("nombre"),
                    rs.getString("apellido")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosSeleccionados() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            txtCodigo.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtApellido.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            
            // Obtener password de la base de datos
            int codigo = Integer.parseInt(txtCodigo.getText());
            String sql = "SELECT password FROM usuarios WHERE codigo = ?";
            
            try (Connection conn = conexionDB.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setInt(1, codigo);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        txtPassword.setText(rs.getString("password"));
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar contraseña: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos(ActionEvent e) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtPassword.setText("");
        tablaUsuarios.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            formularioClientes form = new formularioClientes();
            form.setVisible(true);
        });
    }
}










