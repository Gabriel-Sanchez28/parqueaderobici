package edu.unilibre.interaccion;

import edu.unilibre.datos.Color;
import edu.unilibre.datos.Pago;
import edu.unilibre.datos.Propietario;
import edu.unilibre.datos.RegistroPago;
import edu.unilibre.gestion.GestorParqueadero;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;

/**
 * Interfaz gráfica del Parqueadero de Bicicletas.
 * Reutiliza exactamente la misma lógica de negocio (GestorParqueadero)
 * que la versión de consola implementada en la clase Ventana.
 */
public class ventanarpueba extends JFrame {

    // Se usa un único gestor durante toda la sesión, igual que en Ventana
    private final GestorParqueadero gestor = new GestorParqueadero();

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel panelContenedor = new JPanel(cardLayout);

    // Nombres de las "tarjetas" del CardLayout
    private static final String MENU = "MENU";
    private static final String REGISTRAR = "REGISTRAR";
    private static final String SACAR = "SACAR";
    private static final String REPORTE = "REPORTE";

    public ventanarpueba() {
        super("Menu Parqueadero de Bicicletas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 420);
        setLocationRelativeTo(null);
        setResizable(false);

        panelContenedor.add(crearPanelMenu(), MENU);
        panelContenedor.add(crearPanelRegistrar(), REGISTRAR);
        panelContenedor.add(crearPanelSacar(), SACAR);
        panelContenedor.add(crearPanelReporte(), REPORTE);

        add(panelContenedor);
        cardLayout.show(panelContenedor, MENU);
    }

    // ------------------------------------------------------------------
    // 1. MENU PRINCIPAL
    // ------------------------------------------------------------------
    private JPanel crearPanelMenu() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Menu Parqueadero de Bicicletas", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton btnRegistrar = new JButton("1. Registrar una bicicleta");
        JButton btnSacar = new JButton("2. Sacar bicicleta");
        JButton btnReporte = new JButton("3. Generar reporte");
        JButton btnSalir = new JButton("4. Salir");

        btnRegistrar.addActionListener(e -> {
            limpiarPanelRegistrar();
            cardLayout.show(panelContenedor, REGISTRAR);
        });
        btnSacar.addActionListener(e -> {
            limpiarPanelSacar();
            cardLayout.show(panelContenedor, SACAR);
        });
        btnReporte.addActionListener(e -> {
            actualizarReporte();
            cardLayout.show(panelContenedor, REPORTE);
        });
        btnSalir.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Gracias por usar nuestros servicios",
                    "Salir", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            System.exit(0);
        });

        botones.add(btnRegistrar);
        botones.add(btnSacar);
        botones.add(btnReporte);
        botones.add(btnSalir);

        panel.add(botones, BorderLayout.CENTER);
        return panel;
    }

    // ------------------------------------------------------------------
    // 2. REGISTRAR BICICLETA  (equivalente al case 1 de Ventana)
    // ------------------------------------------------------------------
    private JTextField campoSerial;
    private JComboBox<Color> comboColor;
    private JTextField campoNombre;
    private JTextField campoIdentificacion;

    private JPanel crearPanelRegistrar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Registrar una bicicleta", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(4, 2, 8, 8));
        campoSerial = new JTextField();
        comboColor = new JComboBox<>(Color.values());
        campoNombre = new JTextField();
        campoIdentificacion = new JTextField();

        formulario.add(new JLabel("Serial de la bicicleta:"));
        formulario.add(campoSerial);
        formulario.add(new JLabel("Color de la bicicleta:"));
        formulario.add(comboColor);
        formulario.add(new JLabel("Nombre del propietario:"));
        formulario.add(campoNombre);
        formulario.add(new JLabel("Numero de identificacion:"));
        formulario.add(campoIdentificacion);

        panel.add(formulario, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout());
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnVolver = new JButton("Volver al menu");

        btnRegistrar.addActionListener(e -> registrarBicicleta());
        btnVolver.addActionListener(e -> cardLayout.show(panelContenedor, MENU));

        botones.add(btnRegistrar);
        botones.add(btnVolver);
        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    private void limpiarPanelRegistrar() {
        campoSerial.setText("");
        campoNombre.setText("");
        campoIdentificacion.setText("");
        comboColor.setSelectedIndex(0);
    }

    private void registrarBicicleta() {
        int serial;
        int id;

        // Validacion del serial (equivalente a teclado.nextInt())
        try {
            serial = Integer.parseInt(campoSerial.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un serial valido",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Color color = (Color) comboColor.getSelectedItem();

        String nombre = campoNombre.getText().trim();

        // Validacion de la identificacion
        try {
            id = Integer.parseInt(campoIdentificacion.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese una identificacion valida",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Misma logica de negocio que en Ventana.java
        Propietario propietario = new Propietario(id, nombre);
        boolean agregar = gestor.adicionarBicicleta(serial, color, propietario.recibirIdentificacion());

        if (!agregar) {
            JOptionPane.showMessageDialog(this, "!No se pudo agregar la bicicleta, intenta de nuevo¡",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "!Bicicleta registrada exitosamente¡",
                "Exito", JOptionPane.INFORMATION_MESSAGE);
        limpiarPanelRegistrar();
        cardLayout.show(panelContenedor, MENU);
    }

    // ------------------------------------------------------------------
    // 3. SACAR BICICLETA  (equivalente al case 2 de Ventana)
    // ------------------------------------------------------------------
    private JTextField campoIdRegistrada;
    private JComboBox<Pago> comboPago;

    private JPanel crearPanelSacar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Sacar bicicleta", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(2, 2, 8, 8));
        campoIdRegistrada = new JTextField();
        comboPago = new JComboBox<>(Pago.values());

        formulario.add(new JLabel("Numero de identificacion:"));
        formulario.add(campoIdRegistrada);
        formulario.add(new JLabel("Metodo de pago:"));
        formulario.add(comboPago);

        panel.add(formulario, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout());
        JButton btnSacar = new JButton("Sacar bicicleta");
        JButton btnVolver = new JButton("Volver al menu");

        btnSacar.addActionListener(e -> sacarBicicleta());
        btnVolver.addActionListener(e -> cardLayout.show(panelContenedor, MENU));

        botones.add(btnSacar);
        botones.add(btnVolver);
        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    private void limpiarPanelSacar() {
        campoIdRegistrada.setText("");
        comboPago.setSelectedIndex(0);
    }

    private void sacarBicicleta() {
        int idRegistrada;

        try {
            idRegistrada = Integer.parseInt(campoIdRegistrada.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese una identificacion valida",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Pago pago = (Pago) comboPago.getSelectedItem();

        // Misma logica de negocio que en Ventana.java
        RegistroPago sacar = gestor.registrarSalida(idRegistrada, pago);

        if (sacar == null) {
            JOptionPane.showMessageDialog(this, "Ocurrio un error, verifica los datos ingresados",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        mostrarFactura(sacar);
        limpiarPanelSacar();
        cardLayout.show(panelContenedor, MENU);
    }

    // Muestra la factura con el mismo contenido que RegistroPago.imprimirFactura()
    private void mostrarFactura(RegistroPago registro) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder factura = new StringBuilder();
        factura.append("-------------------------------------------------\n");
        factura.append("|              FACTURA DE PARQUEADERO           |\n");
        factura.append("-------------------------------------------------\n");
        factura.append("Nombre Propietario : ").append(registro.recibirNombre()).append("\n");
        factura.append("Identificación     : ").append(registro.recibiSerial()).append("\n");
        factura.append("Hora de Entrada    : ").append(registro.recibirHoraEntrada().format(formato)).append("\n");
        factura.append("Hora de Salida     : ").append(registro.recibirHoraSalida().format(formato)).append("\n");
        factura.append("Método de Pago     : ").append(registro.recibirTipoPago()).append("\n");
        factura.append("Valor a Pagar      : $").append(registro.recibirValorPagar()).append("\n");
        factura.append("-------------------------------------------------\n");
        factura.append("¡Bicicleta retirada y registrada con éxito!");

        JTextArea areaFactura = new JTextArea(factura.toString());
        areaFactura.setEditable(false);
        areaFactura.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, areaFactura, "Factura", JOptionPane.PLAIN_MESSAGE);
    }

    // ------------------------------------------------------------------
    // 4. GENERAR REPORTE  (equivalente al case 3 de Ventana)
    // ------------------------------------------------------------------
    private JTextArea areaReporte;

    private JPanel crearPanelReporte() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Reporte del parqueadero", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(titulo, BorderLayout.NORTH);

        areaReporte = new JTextArea();
        areaReporte.setEditable(false);
        areaReporte.setFont(new Font("Monospaced", Font.PLAIN, 13));
        panel.add(new JScrollPane(areaReporte), BorderLayout.CENTER);

        JButton btnVolver = new JButton("Volver al menu");
        btnVolver.addActionListener(e -> cardLayout.show(panelContenedor, MENU));

        JPanel botones = new JPanel(new FlowLayout());
        botones.add(btnVolver);
        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    private void actualizarReporte() {
        // Misma logica de negocio que en Ventana.java
        String reporte = gestor.generarReporte();
        areaReporte.setText(reporte);
    }

    // ------------------------------------------------------------------
    // Punto de entrada
    // ------------------------------------------------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ventanarpueba ventana = new ventanarpueba();
            ventana.setVisible(true);
        });
    }
}
