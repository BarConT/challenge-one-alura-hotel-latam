package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import jdbc.controller.HuespedController;
import jdbc.controller.ReservaController;
import jdbc.modelo.Huesped;
import jdbc.modelo.Reserva;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.List;
import java.util.Optional;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;

	private ReservaController reservaController;
	private HuespedController huespedController;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		this.reservaController = new ReservaController();
		this.huespedController = new HuespedController();
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(328, 62, 318, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);
		
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Número de Reserva");
		modelo.addColumn("Fecha de Ingreso");
		modelo.addColumn("Fecha de Salida");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		mostrarTablaReservas();
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
		
		
		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		mostrarTablaHuespedes();
		
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuPrincipal menu = new MenuPrincipal();
				menu.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				limpiarTabla();
				if (txtBuscar.getText().equals("")) {
					mostrarTablaReservas();
					mostrarTablaHuespedes();
				} else if(contieneNumeros(txtBuscar.getText())) {
					mostrarTablaReservasPorId();
					mostrarTablaHuespedesPorId();
				} else {
					mostrarTablaHuespedesPorApellido();
				}
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int filaReserva = tbReservas.getSelectedRow();
				int filaHuesped = tbHuespedes.getSelectedRow();
				if (filaReserva>=0) {
					modificarReserva();
					limpiarTabla();
					mostrarTablaReservas();
					mostrarTablaHuespedes();
				} else if (filaHuesped>=0) {
					modificarHuesped();
					limpiarTabla();
					mostrarTablaHuespedes();
					mostrarTablaReservas();
				} else {
					JOptionPane.showMessageDialog(null, "Selecciona un registro");
				}
			}
		});
		
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int filaReserva = tbReservas.getSelectedRow();
				int filaHuesped = tbHuespedes.getSelectedRow();
				if(filaReserva>=0) {
					String mensaje = "<html>¿Desea borrar la reserva?<br>Se eliminará el húesped</html>";
					int confirmar = JOptionPane.showConfirmDialog(null, mensaje);
					if (confirmar==JOptionPane.YES_OPTION) {
						String idReserva = tbReservas.getValueAt(filaReserva, 0).toString();
						eliminarReserva(Integer.valueOf(idReserva));
						JOptionPane.showMessageDialog(contentPane, "Reserva eliminada");
						limpiarTabla();
						mostrarTablaReservas();
						mostrarTablaHuespedes();
					}
				} else if(filaHuesped>=0) {
					String mensaje = "<html>¿Desea borrar el húesped?<br>Se eliminará la reserva</html>";
					int confirmar = JOptionPane.showConfirmDialog(null, mensaje);
					if (confirmar==JOptionPane.YES_OPTION) {
						String idHuesped = tbHuespedes.getValueAt(filaHuesped, 0).toString();
						String idReserva = tbHuespedes.getValueAt(filaHuesped, 6).toString();
						eliminarHuesped(Integer.valueOf(idHuesped));
						eliminarReserva(Integer.valueOf(idReserva));
						JOptionPane.showMessageDialog(contentPane, "Húesped eliminado");
						limpiarTabla();
						mostrarTablaHuespedes();
						mostrarTablaReservas();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecciona un registro");
				}
			}
		});
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}
	
	private List<Reserva> listarReservas() {
		return this.reservaController.listar();
	}
	
	private List<Reserva> listarReservaPorId() {
		return this.reservaController.listarPorId(txtBuscar.getText());
	}
	
	private void mostrarTablaReservas() {
		List<Reserva> reservas = listarReservas();
		modelo.setRowCount(0);
		dibujarTablaReserva(reservas);
	}
		
	private void mostrarTablaReservasPorId() {
		List<Reserva> reservas = listarReservaPorId();
		dibujarTablaReserva(reservas);
	}
	
	public void dibujarTablaReserva(List<Reserva> reservas) {
		try {
			for(Reserva reserva: reservas) {
				modelo.addRow(new Object[] {
						reserva.getId(),
						reserva.getFechaE(),
						reserva.getFechaS(),
						reserva.getValor(),
						reserva.getFormaPago()
				});
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void modificarReserva() {
		Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
    	.ifPresentOrElse(fila ->{
    		LocalDate fechaE = null;
    		LocalDate fechaS = null;
    		try {
    			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    			fechaE = LocalDate.parse(modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString(), dateFormat);
    			fechaS = LocalDate.parse(modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString(), dateFormat);
    		} catch (DateTimeParseException e) {
				JOptionPane.showMessageDialog(this, String.format("Fecha incorrecta"));
			}
    		if (fechaE!=null && fechaS!=null) {
    			if (fechaE.compareTo(fechaS)>0) {
    				JOptionPane.showMessageDialog(null, "La fecha de entrada no puede ser posterior a la fecha de salida",
    						"Error en fechas", JOptionPane.ERROR_MESSAGE);
    				return;
    			}
    		}
			String valor = "$ " + calcularValorReserva(fechaE, fechaS);
    		String formaPago = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4);
    		Integer id = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(),0 ).toString());
    		if (tbReservas.getSelectedColumn()==0) {
    			JOptionPane.showMessageDialog(this, String.format("No se puede modificar el ID"));
    		} else {
    			if (fechaE!=null && fechaS!=null) {
        			this.reservaController.modificar(fechaE, fechaS, valor, formaPago, id);
            		JOptionPane.showMessageDialog(this, String.format("Registro modificado"));
        		}
    		}
    		
    	}, ()-> JOptionPane.showMessageDialog(this, "Error al registrar"));
	}
	
	public String calcularValorReserva(LocalDate fechaE, LocalDate fechaS) {
		if (fechaE!=null && fechaS!=null) {
			int dias = (int)ChronoUnit.DAYS.between(fechaE, fechaS);
			int noche = 3000;
			int valor = dias*noche;
			return Integer.toString(valor);
		} 
		return "";	
	}
	
	public void eliminarReserva(Integer id) {
		this.reservaController.eliminar(id);
	}
	
	private List<Huesped> listarHuespedes() {
		return this.huespedController.listar();
	}
	
	private List<Huesped> listarHuespedPorId() {
		return this.huespedController.listarPorId(txtBuscar.getText());
	}
	
	private List<Huesped> listarHuespedPorApellido() {
		return this.huespedController.listarPorApellido(txtBuscar.getText());
	}
	
	private void mostrarTablaHuespedes() {
		List<Huesped> huespedes = listarHuespedes();
		modeloHuesped.setRowCount(0);
		dibujarTablaHuesped(huespedes);
	}
	
	private void mostrarTablaHuespedesPorId() {
		List<Huesped> huespedes = listarHuespedPorId();
		dibujarTablaHuesped(huespedes);
	}
	
	private void mostrarTablaHuespedesPorApellido() {
		List<Huesped> huespedes = listarHuespedPorApellido();
		dibujarTablaHuesped(huespedes);
	}
	
	public void dibujarTablaHuesped(List<Huesped> huespedes) {
		try {
			for(Huesped huesped: huespedes) {
				modeloHuesped.addRow(new Object[] {
						huesped.getId(),
						huesped.getNombre(),
						huesped.getApellido(),
						huesped.getFechaNacimiento(),
						huesped.getNacionalidad(),
						huesped.getTelefono(),
						huesped.getIdReserva()
				});
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void modificarHuesped() {
		Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
    	.ifPresentOrElse(fila ->{
			String nombre = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1);
			String apellido = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2);
			LocalDate fechaNacimiento=null;
			try {
				DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				fechaNacimiento = LocalDate.parse(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3).toString(), dateFormat);
			} catch (DateTimeParseException e) {
				JOptionPane.showMessageDialog(this, String.format("Fecha incorrecta"));
			}
			String nacionalidad = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4);
			String telefono = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5);
    		Integer idReserva = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6).toString());
    		Integer id = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());	
    		if (tbHuespedes.getSelectedColumn()==0 || tbHuespedes.getSelectedColumn()==6) {
    			JOptionPane.showMessageDialog(this, String.format("No se puede modificar el ID"));
    		} else {
    			if (fechaNacimiento != null) {
        			this.huespedController.modificar(nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva, id);
            		JOptionPane.showMessageDialog(this, String.format("Registro modificado"));
        		}
    		}
    		
    	}, ()-> JOptionPane.showMessageDialog(this, "Error al registrar"));
	}
	
	public void eliminarHuesped(Integer id) {
		this.huespedController.eliminar(id);
	}
	
	private void limpiarTabla() {
		((DefaultTableModel) tbHuespedes.getModel()).setRowCount(0);
		((DefaultTableModel) tbReservas.getModel()).setRowCount(0);
	}

    private boolean contieneNumeros(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
	
//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
}
}
