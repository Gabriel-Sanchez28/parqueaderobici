package edu.unilibre.interaccion;

import edu.unilibre.datos.Color;
import edu.unilibre.datos.Pago;
import edu.unilibre.datos.Propietario;
import edu.unilibre.datos.RegistroPago;
import edu.unilibre.gestion.GestorParqueadero;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class Gui extends JFrame {
    private JPanel Ventana;
    private JLabel titulo;
    private JLabel subTitulo;
    private JButton RegistrarBicicletaButton;
    private JButton sacarBicicletaButton;
    private JButton generarReporteButton;
    private JButton salida;

    // --- Carta "REGISTRAR" ---
    private JPanel panelRegistrar;
    private JTextField campoSerial;
    private JComboBox<Color> comboColor;
    private JTextField campoNombre;
    private JTextField campoIdentificacion;
    private JButton btnConfirmarRegistro;
    private JButton btnVolverDeRegistrar;

    // --- Carta "SACAR" ---
    private JPanel panelSacar;
    private JTextField campoIdRegistrada;
    private JComboBox<Pago> comboPago;
    private JButton btnConfirmarSalida;
    private JButton btnVolverDeSacar;

    // --- Carta "REPORTE" ---
    private JPanel panelReporte;
    private JTextArea areaReporte;
    private JButton btnVolverDeReporte;

    // ============================================================
    private final GestorParqueadero gestor = new GestorParqueadero();

    private static final String MENU = "MENU";
    private static final String REGISTRAR = "REGISTRAR";
    private static final String SACAR = "SACAR";
    private static final String REPORTE = "REPORTE";


    public Gui() {
        super("Menú Parqueadero de Bicicletas");

        // Construccion de toda la interfaz visual
        construirCartasDeReferencia();

        // Configuracion la ventana principal
        setContentPane(Ventana);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Inicializamos las listas
        comboColor.setModel(new DefaultComboBoxModel(Color.values()));
        comboPago.setModel(new DefaultComboBoxModel(Pago.values()));

        // Navegación entre cartas
        RegistrarBicicletaButton.addActionListener(e -> mostrarCarta(REGISTRAR));
        sacarBicicletaButton.addActionListener(e -> mostrarCarta(SACAR));
        generarReporteButton.addActionListener(e -> {
            areaReporte.setText(gestor.generarReporte());
            mostrarCarta(REPORTE);
        });
        salida.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Gracias por usar nuestros servicios",
                    "Salir", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            System.exit(0);
        });
        btnVolverDeRegistrar.addActionListener(e -> mostrarCarta(MENU));
        btnVolverDeSacar.addActionListener(e -> mostrarCarta(MENU));
        btnVolverDeReporte.addActionListener(e -> mostrarCarta(MENU));

        // confirmacion accion
        btnConfirmarRegistro.addActionListener(e -> registrarBicicleta());
        btnConfirmarSalida.addActionListener(e -> sacarBicicleta());
    }

    //cambio de carta
    private void mostrarCarta(String nombre) {
        CardLayout cl = (CardLayout) Ventana.getLayout();
        cl.show(Ventana, nombre);
    }

    private void registrarBicicleta() {
        int serial;
        int id;

        try {
            serial = Integer.parseInt(campoSerial.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un serial válido",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Color color = (Color) comboColor.getSelectedItem();
        String nombre = campoNombre.getText().trim();

        try {
            id = Integer.parseInt(campoIdentificacion.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese una identificación válida",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Propietario propietario = new Propietario(id, nombre);
        boolean agregar = gestor.adicionarBicicleta(serial, color, propietario.recibirIdentificacion(), propietario.recibirNombre());

        if (!agregar) {
            JOptionPane.showMessageDialog(this, "¡No se pudo agregar la bicicleta, intenta de nuevo!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "¡Bicicleta registrada exitosamente!",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);

        // Limpiar campos
        campoSerial.setText("");
        campoNombre.setText("");
        campoIdentificacion.setText("");
        mostrarCarta(MENU);
    }

    private void sacarBicicleta() {
        int idRegistrada;

        try {
            idRegistrada = Integer.parseInt(campoIdRegistrada.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese una identificación válida",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Pago pago = (Pago) comboPago.getSelectedItem();
        RegistroPago sacar = gestor.registrarSalida(idRegistrada, pago);

        if (sacar == null) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error, verifica los datos ingresados",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        mostrarFactura(sacar);
        campoIdRegistrada.setText("");
        mostrarCarta(MENU);
    }

    private void mostrarFactura(RegistroPago registro) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder factura = new StringBuilder();
        factura.append("Nombre Propietario : ").append(registro.recibirNombre()).append("\n");
        factura.append("Identificación     : ").append(registro.recibiSerial()).append("\n");
        factura.append("Hora de Entrada    : ").append(registro.recibirHoraEntrada().format(formato)).append("\n");
        factura.append("Hora de Salida     : ").append(registro.recibirHoraSalida().format(formato)).append("\n");
        factura.append("Método de Pago     : ").append(registro.recibirTipoPago()).append("\n");
        factura.append("Valor a Pagar      : $").append(registro.recibirValorPagar());

        JTextArea area = new JTextArea(factura.toString());
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));

        // Centrar el cuadro de diálogo de la factura
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Factura de Pago", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Construye toda la interfaz visual de forma programática.
     * Esto reemplaza por completo la necesidad del archivo .form
     * y del método $$$setupUI$$$() de IntelliJ.
     */
    private void construirCartasDeReferencia() {
        Ventana = new JPanel(new CardLayout());

        // ==========================================
        // 1. PANEL MENU
        // ==========================================
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        titulo = new JLabel("Sistema Parqueadero de Bicicletas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        subTitulo = new JLabel("Menú Principal", SwingConstants.CENTER);
        subTitulo.setFont(new Font("Arial", Font.PLAIN, 18));
        subTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 40, 0));

        RegistrarBicicletaButton = new JButton("1. Registrar una bicicleta");
        RegistrarBicicletaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        RegistrarBicicletaButton.setMaximumSize(new Dimension(350, 45));
        RegistrarBicicletaButton.setFont(new Font("Arial", Font.PLAIN, 16));

        sacarBicicletaButton = new JButton("2. Sacar bicicleta");
        sacarBicicletaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sacarBicicletaButton.setMaximumSize(new Dimension(350, 45));
        sacarBicicletaButton.setFont(new Font("Arial", Font.PLAIN, 16));

        generarReporteButton = new JButton("3. Generar reporte");
        generarReporteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        generarReporteButton.setMaximumSize(new Dimension(350, 45));
        generarReporteButton.setFont(new Font("Arial", Font.PLAIN, 16));

        salida = new JButton("4. Salir");
        salida.setAlignmentX(Component.CENTER_ALIGNMENT);
        salida.setMaximumSize(new Dimension(350, 45));
        salida.setFont(new Font("Arial", Font.PLAIN, 16));

        panelMenu.add(titulo);
        panelMenu.add(subTitulo);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        panelMenu.add(RegistrarBicicletaButton);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 15)));
        panelMenu.add(sacarBicicletaButton);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 15)));
        panelMenu.add(generarReporteButton);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 15)));
        panelMenu.add(salida);

        // ==========================================
        // 2. PANEL REGISTRAR
        // ==========================================
        panelRegistrar = new JPanel(new GridLayout(8, 1, 10, 15));
        panelRegistrar.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        panelRegistrar.add(new JLabel("Serial de la bicicleta:"));
        campoSerial = new JTextField();
        panelRegistrar.add(campoSerial);

        panelRegistrar.add(new JLabel("Color:"));
        comboColor = new JComboBox<>();
        panelRegistrar.add(comboColor);

        panelRegistrar.add(new JLabel("Nombre del propietario:"));
        campoNombre = new JTextField();
        panelRegistrar.add(campoNombre);

        panelRegistrar.add(new JLabel("Identificación:"));
        campoIdentificacion = new JTextField();
        panelRegistrar.add(campoIdentificacion);

        btnConfirmarRegistro = new JButton("Confirmar Registro");
        panelRegistrar.add(btnConfirmarRegistro);

        btnVolverDeRegistrar = new JButton("Volver al Menú");
        panelRegistrar.add(btnVolverDeRegistrar);

        // ==========================================
        // 3. PANEL SACAR
        // ==========================================
        panelSacar = new JPanel(new GridLayout(6, 1, 10, 15));
        panelSacar.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        panelSacar.add(new JLabel("Identificación del propietario registrado:"));
        campoIdRegistrada = new JTextField();
        panelSacar.add(campoIdRegistrada);

        panelSacar.add(new JLabel("Método de pago:"));
        comboPago = new JComboBox<>();
        panelSacar.add(comboPago);

        btnConfirmarSalida = new JButton("Confirmar Salida");
        panelSacar.add(btnConfirmarSalida);

        btnVolverDeSacar = new JButton("Volver al Menú");
        panelSacar.add(btnVolverDeSacar);

        // ==========================================
        // 4. PANEL REPORTE
        // ==========================================
        panelReporte = new JPanel(new BorderLayout(10, 15));
        panelReporte.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        areaReporte = new JTextArea();
        areaReporte.setEditable(false);
        areaReporte.setFont(new Font("Monospaced", Font.PLAIN, 13));
        panelReporte.add(new JScrollPane(areaReporte), BorderLayout.CENTER);

        btnVolverDeReporte = new JButton("Volver al Menú");
        JPanel panelBotonReporte = new JPanel();
        panelBotonReporte.add(btnVolverDeReporte);
        panelReporte.add(panelBotonReporte, BorderLayout.SOUTH);

        // ==========================================
        // 5. AGREGAR PANELES AL CARDLAYOUT
        // ==========================================
        Ventana.add(panelMenu, MENU);
        Ventana.add(panelRegistrar, REGISTRAR);
        Ventana.add(panelSacar, SACAR);
        Ventana.add(panelReporte, REPORTE);
    }

    public static void main(String[] args) {
        // Ejecutar la interfaz en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            try {
                // Intenta usar el look and feel del sistema para que se vea moderno
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Gui().setVisible(true);
        });
    }
}