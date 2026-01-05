// aca configuraremos los botones de la interfas grafica
//lo que el usuario ve
package ui;
import service.PacienteService;
import model.Paciente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private PacienteService servicio;


    private JTextField txtNombre;
    private JTextField txtEdad;
    private JTextField txtPeso;
    private JTextField txtEstatura;


    private JTable tablaPacientes;
    private DefaultTableModel modeloTabla;

    // para saber que fila se esta editando
    private int filaSeleccionada = -1;

    public VentanaPrincipal() {
        servicio = new PacienteService();
        iniciarComponentes();
        cargarDatosEnTabla();
    }

    private void iniciarComponentes() {
        setTitle("Centro Médico VidaPlena - Administracion pacientes");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- PANEL SUPERIOR: FORMULARIO ---
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Paciente"));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Edad:"));
        txtEdad = new JTextField();
        panelFormulario.add(txtEdad);

        panelFormulario.add(new JLabel("Peso (kg):"));
        txtPeso = new JTextField();
        panelFormulario.add(txtPeso);

        panelFormulario.add(new JLabel("Estatura (m):"));
        txtEstatura = new JTextField();
        panelFormulario.add(txtEstatura);

        // los botones
        JPanel panelBotones = new JPanel(new FlowLayout());

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Agregamos botones al panel del formulario (o podrías ponerlos en un panel aparte al sur del formulario)
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);

        add(panelNorte, BorderLayout.NORTH);

        // --- PANEL CENTRAL: TABLA ---
        String[] columnas = {"Nombre", "Edad", "Peso", "Estatura", "IMC", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaPacientes = new JTable(modeloTabla);

        // cuando se hace click llama formulario
        tablaPacientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarPacienteDeTabla();
            }
        });

        add(new JScrollPane(tablaPacientes), BorderLayout.CENTER);

        // que hace cada boton

        // registra
        btnRegistrar.addActionListener(e -> registrarPaciente());

        // actualiza datos
        btnActualizar.addActionListener(e -> actualizarPaciente());

        // quita datos
        btnEliminar.addActionListener(e -> eliminarPaciente());

        // borradatos
        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }



    private void cargarDatosEnTabla() {
        // Limpiamos la tabla visual
        modeloTabla.setRowCount(0);

        // Pedimos la lista al servicio
        List<Paciente> lista = servicio.listarPacientes();

        // se llena la tabal fila por fila
        for (Paciente p : lista) {
            Object[] fila = {
                    p.getNombre(),
                    p.getEdad(),
                    p.getPeso(),
                    p.getEstatura(),
                    String.format("%.2f", p.getImc()), // Formato bonito para el número
                    p.getInterpretacion()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void registrarPaciente() {
        try {
            String nombre = txtNombre.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            double peso = Double.parseDouble(txtPeso.getText());
            double estatura = Double.parseDouble(txtEstatura.getText());

            servicio.registrarPaciente(nombre, edad, peso, estatura);

            cargarDatosEnTabla(); // colver a cargar tabla
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Paciente registrado correctamente.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifica Edad, Peso y Estatura que sean números.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarPaciente() {
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un paciente de la tabla primero.");
            return;
        }

        try {
            String nombre = txtNombre.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            double peso = Double.parseDouble(txtPeso.getText());
            double estatura = Double.parseDouble(txtEstatura.getText());

            // metodo actualizar
            servicio.actualizarPaciente(filaSeleccionada, nombre, edad, peso, estatura);

            cargarDatosEnTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Paciente actualizado.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Datos numéricos inválidos.");
        }
    }

    private void eliminarPaciente() {
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un paciente de la tabla para eliminar.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este paciente?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            servicio.eliminarPaciente(filaSeleccionada);
            cargarDatosEnTabla();
            limpiarFormulario();
        }
    }

    private void seleccionarPacienteDeTabla() {
        filaSeleccionada = tablaPacientes.getSelectedRow();

        if (filaSeleccionada != -1) {
            // tenemos los datos de la tabla
            txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtEdad.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtPeso.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtEstatura.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtEdad.setText("");
        txtPeso.setText("");
        txtEstatura.setText("");
        filaSeleccionada = -1;
        tablaPacientes.clearSelection();
    }
}