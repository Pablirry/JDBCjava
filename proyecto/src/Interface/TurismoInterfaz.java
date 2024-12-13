package Interface;

import Model.*;
import Persistence.GestorJDBC;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class TurismoInterfaz {
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/turismo";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    
	private JFrame frame;
	private JTable tableDestinos;
	private JTable tableActividades;
	private DefaultTableModel modelDestinos;
	private DefaultTableModel modelActividades;
	private GestorJDBC dbManager;

	public TurismoInterfaz() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            dbManager = new GestorJDBC();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        frame = new JFrame("Gestión de Turismo");
        frame.setBounds(100, 100, 900, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mostrarPantallaDeInicioSesion();

    }

    private void mostrarPantallaDeInicioSesion() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        loginPanel.setBackground(new Color(50, 50, 50));

        JLabel usernameLabel = new JLabel("Usuario:");
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(passwordField, gbc);

        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBackground(new Color(70, 130, 180));
        btnLogin.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        loginPanel.add(btnLogin, gbc);

        JButton btnRegister = new JButton("Registrarse");
        btnRegister.setBackground(new Color(34, 139, 34));
        btnRegister.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 3;
        loginPanel.add(btnRegister, gbc);

        btnLogin.addActionListener(e -> {
            String usuario = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (validarUsuario(usuario, password)) {
                loginPanel.setVisible(false);
                mostrarInterfazDeGestion();
            } else {
                JOptionPane.showMessageDialog(frame, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRegister.addActionListener(e -> mostrarRegistroUsuario());

        frame.add(loginPanel);
        frame.setVisible(true);
    }

    private void mostrarRegistroUsuario() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] fields = {
                "Usuario:", usernameField,
                "Contraseña:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(frame, fields, "Registrar Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String usuario = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (usuario.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Los campos no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            registrarUsuario(usuario, password);
        }
    }

    private void registrarUsuario(String usuario, String password) {
        try {
            Class.forName(DB_DRIVER);

            try (Connection connection = DriverManager.getConnection(DB_URL, "root", "root")) {
                String checkQuery = "SELECT * FROM usuarios WHERE usuario = ?";
                try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                    checkStmt.setString(1, usuario);
                    ResultSet rs = checkStmt.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(frame, "El usuario ya existe. Intente con otro nombre de usuario.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                String hashedPassword = cifrarContrasena(password);

                String query = "INSERT INTO usuarios (usuario, contrasena) VALUES (?, ?)";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setString(1, usuario);
                    stmt.setString(2, hashedPassword);
                    stmt.executeUpdate();

                    JOptionPane.showMessageDialog(frame, "Usuario registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error al registrar usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Driver de base de datos no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String cifrarContrasena(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al cifrar la contraseña: " + e.getMessage());
        }
    }

    private boolean validarUsuario(String usuario, String password) {
        try {
            Class.forName(DB_DRIVER);

            try (Connection connection = DriverManager.getConnection(DB_URL, "root", "root")) {
                String query = "SELECT contrasena FROM usuarios WHERE usuario = ?";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setString(1, usuario);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        String storedPassword = rs.getString("contrasena");
                        String hashedInputPassword = cifrarContrasena(password);

                        if (storedPassword.equals(hashedInputPassword)) {
                            return true;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error de conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Driver de base de datos no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private void mostrarInterfazDeGestion() {
        frame.getContentPane().removeAll();

        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem menuCerrarSesion = new JMenuItem("Cerrar Sesión");
        JMenuItem menuSalir = new JMenuItem("Salir");

        menuCerrarSesion.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.setJMenuBar(null);
            mostrarPantallaDeInicioSesion();
        });

        menuSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(menuCerrarSesion);
        menuArchivo.add(menuSalir);
        menuBar.add(menuArchivo);

        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem menuAcerca = new JMenuItem("Acerca de");
        menuAcerca.addActionListener(e -> mostrarAcercaDe());
        menuAyuda.add(menuAcerca);
        menuBar.add(menuAyuda);

        frame.setJMenuBar(menuBar);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel panelDestinos = crearPanelDestinos();
        JPanel panelActividades = crearPanelActividades();

        tabbedPane.addTab("Destinos", null, panelDestinos, "Gestionar Destinos");
        tabbedPane.addTab("Actividades", null, panelActividades, "Gestionar Actividades");

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);

        cargarDatos();
    }

    private JPanel crearPanelDestinos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modelDestinos = new DefaultTableModel(new String[]{"Nombre", "Descripción", "Región", "Clima"}, 0);
        tableDestinos = new JTable(modelDestinos);

        // Add sorting functionality
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelDestinos);
        tableDestinos.setRowSorter(sorter);

        JScrollPane scrollDestinos = new JScrollPane(tableDestinos);
        panel.add(scrollDestinos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JTextField filtroField = new JTextField(15);
        JButton btnFiltrar = createStyledButton("Filtrar", "filter");
        JButton btnAgregar = createStyledButton("Agregar", "add");
        JButton btnEliminar = createStyledButton("Eliminar", "delete");

        panelBotones.add(new JLabel("Filtro:"));
        panelBotones.add(filtroField);
        panelBotones.add(btnFiltrar);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);

        btnFiltrar.addActionListener(e -> filtrarDestinos(filtroField.getText()));
        btnAgregar.addActionListener(e -> agregarDestino());
        btnEliminar.addActionListener(e -> eliminarDestino());

        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelActividades() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modelActividades = new DefaultTableModel(new String[]{"Nombre", "Tipo", "Precio", "Duración", "Dificultad"}, 0);
        tableActividades = new JTable(modelActividades);

        // Add sorting functionality
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelActividades);
        tableActividades.setRowSorter(sorter);

        JScrollPane scrollActividades = new JScrollPane(tableActividades);
        panel.add(scrollActividades, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JTextField filtroField = new JTextField(15);
        JButton btnFiltrar = createStyledButton("Filtrar", "filter");
        JButton btnAgregar = createStyledButton("Agregar", "add");
        JButton btnEliminar = createStyledButton("Eliminar", "delete");

        panelBotones.add(new JLabel("Filtro:"));
        panelBotones.add(filtroField);
        panelBotones.add(btnFiltrar);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);

        btnFiltrar.addActionListener(e -> filtrarActividades(filtroField.getText()));
        btnAgregar.addActionListener(e -> agregarActividad());
        btnEliminar.addActionListener(e -> eliminarActividad());

        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createStyledButton(String text, String iconName) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        return button;
    }

    private void mostrarAcercaDe() {
        JOptionPane.showMessageDialog(frame, "Gestión de Turismo v1.0\nDesarrollado por Pablo", "Acerca de", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cargarDatos() {
        try {
            List<Destino> destinos = dbManager.listarDestinos();
            modelDestinos.setRowCount(0);
            for (Destino destino : destinos) {
                modelDestinos.addRow(new Object[]{destino.getNombre(), destino.getDescripcion(), destino.getRegion(), destino.getClima()});
            }

            List<Actividad> actividades = dbManager.listarActividades();
            modelActividades.setRowCount(0);
            for (Actividad actividad : actividades) {
                modelActividades.addRow(new Object[]{actividad.getNombre(), actividad.getTipo(), actividad.getPrecio(), actividad.getDuracion(), actividad.getDificultad()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

	private void filtrarDestinos(String filtro) {
		try {
			List<Destino> destinos = dbManager.listarDestinos();
			modelDestinos.setRowCount(0);
			for (Destino destino : destinos) {
				if (destino.getNombre().toLowerCase().contains(filtro.toLowerCase())
						|| destino.getRegion().toLowerCase().contains(filtro.toLowerCase())) {
					modelDestinos.addRow(new Object[] { destino.getNombre(), destino.getDescripcion(),
							destino.getRegion(), destino.getClima() });
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void filtrarActividades(String filtro) {
		try {
			List<Actividad> actividades = dbManager.listarActividades();
			modelActividades.setRowCount(0);
			for (Actividad actividad : actividades) {
				if (actividad.getNombre().toLowerCase().contains(filtro.toLowerCase())
						|| actividad.getTipo().toLowerCase().contains(filtro.toLowerCase())) {
					modelActividades.addRow(new Object[] { actividad.getNombre(), actividad.getTipo(),
							actividad.getPrecio(), actividad.getDuracion(), actividad.getDificultad() });
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void agregarDestino() {
		JTextField nombreField = new JTextField();
		JTextField descripcionField = new JTextField();
		JTextField regionField = new JTextField();
		JTextField climaField = new JTextField();

		Object[] fields = { "Nombre:", nombreField, "Descripción:", descripcionField, "Región:", regionField, "Clima:",
				climaField };

		int option = JOptionPane.showConfirmDialog(frame, fields, "Agregar Destino", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			try {
				Destino destino = new Destino(nombreField.getText(), descripcionField.getText(), regionField.getText(),
						climaField.getText(), List.of());
				dbManager.insertarDestino(destino);
				cargarDatos();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void eliminarDestino() {
		int selectedRow = tableDestinos.getSelectedRow();
		if (selectedRow >= 0) {
			String nombre = (String) modelDestinos.getValueAt(selectedRow, 0);
			try {
				dbManager.eliminarDestino(nombre);
				cargarDatos();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(frame, "Seleccione un destino para eliminar.");
		}
	}

	private void agregarActividad() {
		JTextField nombreField = new JTextField();
		JTextField tipoField = new JTextField();
		JTextField precioField = new JTextField();
		JTextField duracionField = new JTextField();
		JTextField dificultadField = new JTextField();

		Object[] fields = { "Nombre:", nombreField, "Tipo:", tipoField, "Precio:", precioField, "Duración:",
				duracionField, "Dificultad:", dificultadField };

		int option = JOptionPane.showConfirmDialog(frame, fields, "Agregar Actividad", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			try {
				Actividad actividad = new Actividad(nombreField.getText(), tipoField.getText(),
						Double.parseDouble(precioField.getText()), Integer.parseInt(duracionField.getText()),
						dificultadField.getText());
				dbManager.insertarActividad(actividad);
				cargarDatos();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void eliminarActividad() {
		int selectedRow = tableActividades.getSelectedRow();
		if (selectedRow >= 0) {
			String nombre = (String) modelActividades.getValueAt(selectedRow, 0);
			try {
				dbManager.eliminarActividad(nombre);
				cargarDatos();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(frame, "Seleccione una actividad para eliminar.");
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(TurismoInterfaz::new);
	}

}
