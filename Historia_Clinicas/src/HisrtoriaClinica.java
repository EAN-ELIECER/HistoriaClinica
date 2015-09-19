import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Image;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import cs.grupoeliecer.historia.clinica.*;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import java.awt.ScrollPane;

public class HisrtoriaClinica {

	private JFrame frmHistoriasClinicas;
	private JTextField userField;
	private JPasswordField passwordField;
	private JTextField fieldNombres;
	private JTextField fieldApellidos;
	private JTextField fieldDocIdent;
	private JTextField fieldEdad;
	private JTextField fieldEPS;
	private JTextField fieldRH;    


	private JComboBox comboBoxHistClinica; 
	private JComboBox comboBoxRegistro;
	private JComboBox comboBoxConsultaReg;
	Paciente paciente = new Paciente();

	private JTable tablePacientes;
	private JTable tableDiagnosticos;
	private JTable tableTratamientos;

	//Titulos de columnas para las tablas
	String[] columnNamesPacientes = {"Apellidos", "Nombres", "Identificación", "Género", "Edad", "EPS"};
	String[] columnNamesTratamientos = {"Fecha","Apellidos", "Nombres", "Tratamiento"};
	String[] columnNamesDiagnosticos = {"Fecha","Apellidos", "Nombres", "Diagnóstico"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HisrtoriaClinica window = new HisrtoriaClinica();
					window.frmHistoriasClinicas.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HisrtoriaClinica() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
		//Imágenes
		Image imgBar = new ImageIcon(this.getClass().getResource("/Paciente-icon.png")).getImage();
		Image imgVolver = new ImageIcon(this.getClass().getResource("/back-icon.png")).getImage();
		ImageIcon iconVolver =new ImageIcon(imgVolver);
		Image imgOk = new ImageIcon(this.getClass().getResource("/ok-icon.png")).getImage();
		ImageIcon iconOk =new ImageIcon(imgOk);
		
		frmHistoriasClinicas = new JFrame();
		frmHistoriasClinicas.setTitle("Historias Cl\u00EDnicas - Grupo Eliecer");
		frmHistoriasClinicas.setIconImage(imgBar);
		frmHistoriasClinicas.setBounds(100, 100, 613, 373);
		frmHistoriasClinicas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHistoriasClinicas.getContentPane().setLayout(new CardLayout(0, 0));

		JPanel panelAuth = new JPanel();
		frmHistoriasClinicas.getContentPane().add(panelAuth, "name_64704576011965");
		panelAuth.setLayout(null);
		JPanel panelMain = new JPanel();
		frmHistoriasClinicas.getContentPane().add(panelMain, "name_3070730729115");
		panelMain.setLayout(null);
		JPanel panelCrearPaciente = new JPanel();
		frmHistoriasClinicas.getContentPane().add(panelCrearPaciente, "name_6451884984544");
		panelCrearPaciente.setLayout(null);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsuario.setBounds(132, 115, 79, 24);
		panelAuth.add(lblUsuario);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblContrasea.setBounds(132, 160, 107, 14);
		panelAuth.add(lblContrasea);

		userField = new JTextField();
		userField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		userField.setBounds(267, 119, 191, 20);
		panelAuth.add(userField);
		userField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordField.setBounds(267, 159, 191, 20);
		panelAuth.add(passwordField);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnCancelar.setBounds(226, 252, 89, 23);
		panelAuth.add(btnCancelar);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					Usuario user = new Usuario();
					String pwd = user.getContrasena(userField.getText());
					//String pwd = "admin";
					if(!pwd.equals(passwordField.getText())){
						JOptionPane.showMessageDialog(null, "Usuario o contraseña inválido", "Error!!", 1);
					} else{
						panelMain.setVisible(true);
						panelAuth.setVisible(false);
					}
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAceptar.setBounds(358, 252, 89, 23);
		panelAuth.add(btnAceptar);



		JLabel label = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/Historia_Clinica.jpg")).getImage();
		label.setIcon(new ImageIcon(img));
		label.setBounds(109, 63, 370, 221);
		panelMain.add(label);

		JLabel pacienteIcon = new JLabel("");
		Image img1 = new ImageIcon(this.getClass().getResource("/Paciente-icon.png")).getImage();
		pacienteIcon.setIcon(new ImageIcon(img1));
		pacienteIcon.setBounds(27, 23, 112, 128);
		panelCrearPaciente.add(pacienteIcon);

		JLabel lblNombres = new JLabel("Nombres");
		lblNombres.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNombres.setBounds(194, 34, 58, 14);
		panelCrearPaciente.add(lblNombres);

		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblApellidos.setBounds(194, 59, 58, 14);
		panelCrearPaciente.add(lblApellidos);

		JLabel lblDocumentoIdentificacinNo = new JLabel("Documento Identificaci\u00F3n No.");
		lblDocumentoIdentificacinNo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDocumentoIdentificacinNo.setBounds(194, 84, 170, 14);
		panelCrearPaciente.add(lblDocumentoIdentificacinNo);

		JLabel lblGnero = new JLabel("G\u00E9nero");
		lblGnero.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblGnero.setBounds(194, 109, 46, 14);
		panelCrearPaciente.add(lblGnero);

		JLabel lblFechaNacimiento = new JLabel("Fecha Nacimiento");
		lblFechaNacimiento.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFechaNacimiento.setBounds(194, 169, 112, 14);
		panelCrearPaciente.add(lblFechaNacimiento);

		JLabel lblEdad = new JLabel("Edad");
		lblEdad.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEdad.setBounds(194, 211, 46, 14);
		panelCrearPaciente.add(lblEdad);

		JLabel lblEps = new JLabel("E.P.S.");
		lblEps.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEps.setBounds(194, 236, 46, 14);
		panelCrearPaciente.add(lblEps);

		JLabel lblGrupoSanguneo = new JLabel("Grupo Sangu\u00EDneo");
		lblGrupoSanguneo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblGrupoSanguneo.setBounds(194, 261, 112, 14);
		panelCrearPaciente.add(lblGrupoSanguneo);

		fieldNombres = new JTextField();
		fieldNombres.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fieldNombres.setBounds(374, 32, 180, 20);
		panelCrearPaciente.add(fieldNombres);
		fieldNombres.setColumns(10);

		fieldApellidos = new JTextField();
		fieldApellidos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fieldApellidos.setBounds(374, 57, 180, 20);
		panelCrearPaciente.add(fieldApellidos);
		fieldApellidos.setColumns(10);

		fieldDocIdent = new JTextField();
		fieldDocIdent.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fieldDocIdent.setBounds(374, 82, 180, 20);
		panelCrearPaciente.add(fieldDocIdent);
		fieldDocIdent.setColumns(10);

		JRadioButton rdbtnFemenino = new JRadioButton("Femenino");
		rdbtnFemenino.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnFemenino.setBounds(374, 106, 109, 23);
		panelCrearPaciente.add(rdbtnFemenino);

		JRadioButton rdbtnMasculino = new JRadioButton("Masculino");
		rdbtnMasculino.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnMasculino.setBounds(374, 128, 109, 23);
		panelCrearPaciente.add(rdbtnMasculino);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnFemenino);
		bg.add(rdbtnMasculino);

		JComboBox comboDia = new JComboBox();
		comboDia.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		comboDia.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboDia.setBounds(374, 166, 46, 20);
		panelCrearPaciente.add(comboDia);

		JComboBox comboMes = new JComboBox();
		comboMes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboMes.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		comboMes.setBounds(437, 166, 46, 20);
		panelCrearPaciente.add(comboMes);

		JComboBox comboAnio = new JComboBox();
		comboAnio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboAnio.setModel(new DefaultComboBoxModel(new String[] {"1920", "1921", "1922", "1923", "1924", "1925", "1926", "1927", "1928", "1929", "1930", "1931", "1932", "1933", "1934", "1935", "1936", "1937", "1938", "1939", "1940", "1941", "1942", "1943", "1944", "1945", "1946", "1947", "1948", "1949", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020"}));
		comboAnio.setBounds(498, 167, 56, 20);
		panelCrearPaciente.add(comboAnio);

		fieldEdad = new JTextField();
		fieldEdad.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fieldEdad.setBounds(374, 208, 86, 20);
		panelCrearPaciente.add(fieldEdad);
		fieldEdad.setColumns(10);

		fieldEPS = new JTextField();
		fieldEPS.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fieldEPS.setBounds(374, 234, 180, 20);
		panelCrearPaciente.add(fieldEPS);
		fieldEPS.setColumns(10);

		fieldRH = new JTextField();
		fieldRH.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fieldRH.setBounds(374, 259, 86, 20);
		panelCrearPaciente.add(fieldRH);
		fieldRH.setColumns(10);

		JButton btnCancelarCP = new JButton("Cancelar");
		btnCancelarCP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMain.setVisible(true);
				panelCrearPaciente.setVisible(false);
			}
		});
		Image img2 = new ImageIcon(this.getClass().getResource("/cancel-icon.png")).getImage();
		ImageIcon iconCancelar =new ImageIcon(img2);
		btnCancelarCP.setIcon(new ImageIcon(img2));
		btnCancelarCP.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancelarCP.setBounds(194, 301, 129, 23);
		panelCrearPaciente.add(btnCancelarCP);

		JButton btnAceptarCP = new JButton("Aceptar");
		btnAceptarCP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Paciente paciente = new Paciente();
					String strGenero = "";;
					if(rdbtnFemenino.isSelected()){
						strGenero = "Femenino";
					} else if(rdbtnMasculino.isSelected()){
						strGenero = "Masculino";
					}
					paciente.save(fieldNombres.getText(), fieldApellidos.getText(), fieldDocIdent.getText(), comboDia.getSelectedItem().toString() + "/" + comboMes.getSelectedItem().toString() + "/" + comboAnio.getSelectedItem().toString(), strGenero, new Integer(fieldEdad.getText()).intValue(), fieldEPS.getText(), fieldRH.getText(), 1);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		Image img3 = new ImageIcon(this.getClass().getResource("/ok-icon.png")).getImage();
		btnAceptarCP.setIcon(new ImageIcon(img3));
		btnAceptarCP.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAceptarCP.setBounds(360, 301, 123, 23);
		panelCrearPaciente.add(btnAceptarCP);


		/*Registro médico*/
		JPanel panelRegistroMedico = new JPanel();
		frmHistoriasClinicas.getContentPane().add(panelRegistroMedico, "name_21308548467540");
		panelRegistroMedico.setLayout(null);

		JLabel lblNewLabel = new JLabel("Seleccione identificaci\u00F3n del paciente");
		lblNewLabel.setBounds(131, 11, 217, 14);
		panelRegistroMedico.add(lblNewLabel);

		comboBoxRegistro = new JComboBox();
		comboBoxRegistro.setBounds(131, 36, 136, 20);
		panelRegistroMedico.add(comboBoxRegistro);

		JLabel lblNewLabel_1 = new JLabel("Diagn\u00F3stico");
		lblNewLabel_1.setBounds(131, 80, 86, 14);
		panelRegistroMedico.add(lblNewLabel_1);

		JEditorPane editorDiagnostico = new JEditorPane();
		editorDiagnostico.setBounds(131, 105, 343, 56);
		panelRegistroMedico.add(editorDiagnostico);

		JLabel lblTratamiento = new JLabel("Tratamiento");
		lblTratamiento.setBounds(131, 177, 86, 14);
		panelRegistroMedico.add(lblTratamiento);

		JEditorPane editorTratamiento = new JEditorPane();
		editorTratamiento.setBounds(131, 204, 348, 66);
		panelRegistroMedico.add(editorTratamiento);

		JButton btnNewButton = new JButton("Guardar");
		btnNewButton.setIcon(iconOk);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String documento=comboBoxRegistro.getSelectedItem().toString();
				String diagnostico = editorDiagnostico.getText();
				String tratamiento = editorTratamiento.getText();
				if(diagnostico.equals("") || tratamiento.equals("")){
					JOptionPane.showMessageDialog(null, "Por favor ingrese diagnóstico y tratamiento", "Error!!", 1);
				}else{
					if(paciente.saveDiagnosticoTratamiento(documento, diagnostico, tratamiento)){
						JOptionPane.showMessageDialog(null, "Registro médico guardado", "Información", 1);
					}else{
						//TODO validar error si no existe historia clínica
						JOptionPane.showMessageDialog(null, "Se presentó un error al guardar el registro médico o no se ha creado la historia clínica del paciente", "Error!!", 1);
					}
				}
			}
		});
		btnNewButton.setBounds(310, 301, 115, 23);
		panelRegistroMedico.add(btnNewButton);

		JButton btnVolverRegistro = new JButton("Volver");
		btnVolverRegistro.setIcon(iconVolver);
		btnVolverRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMain.setVisible(true);
				panelRegistroMedico.setVisible(false);
			}
		});
		btnVolverRegistro.setBounds(177, 301, 115, 23);
		panelRegistroMedico.add(btnVolverRegistro);



		/*Historia Clínica*/
		JPanel panelHistoriaClinica = new JPanel();
		frmHistoriasClinicas.getContentPane().add(panelHistoriaClinica, "name_29364468640250");
		panelHistoriaClinica.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Selecione identificaci\u00F3n del paciente");
		lblNewLabel_2.setBounds(111, 25, 237, 14);
		panelHistoriaClinica.add(lblNewLabel_2);

		comboBoxHistClinica = new JComboBox();
		comboBoxHistClinica.setBounds(111, 50, 153, 20);
		panelHistoriaClinica.add(comboBoxHistClinica);

		JLabel lblAntecedentes = new JLabel("Antecedentes");
		lblAntecedentes.setBounds(111, 137, 135, 14);
		panelHistoriaClinica.add(lblAntecedentes);

		JEditorPane editorAntecedentes = new JEditorPane();
		editorAntecedentes.setBounds(110, 162, 341, 78);
		panelHistoriaClinica.add(editorAntecedentes);

		JButton btnGuardarHistoria = new JButton("Guardar");
		btnGuardarHistoria.setIcon(iconOk);
		btnGuardarHistoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String identificacion=comboBoxHistClinica.getSelectedItem().toString();
				String antecedentes = editorAntecedentes.getText();
				if(antecedentes.equals("")){
					JOptionPane.showMessageDialog(null, "Por favor ingrese antecedentes", "Error!!", 1);
				}else{
					if(paciente.saveHistoriaClinica(identificacion, antecedentes)){
						int dialogo = JOptionPane.showConfirmDialog (null, "Historia clínica guardada. \n ¿Desea crear un registro médico?","Información",JOptionPane.YES_NO_OPTION);
						if(dialogo==JOptionPane.YES_OPTION){
							panelHistoriaClinica.setVisible(false);
							panelRegistroMedico.setVisible(true);
						}
					}else{
						JOptionPane.showMessageDialog(null, "Se presentó un error al guardar la Historia clínica", "Error!!", 1);
					}
				}

			}
		});
		btnGuardarHistoria.setBounds(300, 274, 122, 23);
		panelHistoriaClinica.add(btnGuardarHistoria);

		JButton btnVolverHistoria = new JButton("Volver");
		btnVolverHistoria.setIcon(iconVolver);
		btnVolverHistoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMain.setVisible(true);
				panelHistoriaClinica.setVisible(false);
			}
		});
		btnVolverHistoria.setBounds(177, 274, 103, 23);
		panelHistoriaClinica.add(btnVolverHistoria);



		/*Diagnósticos*/
		JPanel panelDiagnosticos = new JPanel();
		frmHistoriasClinicas.getContentPane().add(panelDiagnosticos, "name_6542732257411");
		panelDiagnosticos.setLayout(null);

		tableDiagnosticos = new JTable() {			
			public boolean isCellEditable(int row, int column){  
		          return false;  
		      }
		};
		updateTableDiagnosticos();
		tableDiagnosticos.getColumnModel().getColumn(0).setPreferredWidth(88);
		tableDiagnosticos.getColumnModel().getColumn(3).setPreferredWidth(214);
		tableDiagnosticos.setPreferredScrollableViewportSize(new Dimension(500, 70));
		JScrollPane scrollPane = new JScrollPane(tableDiagnosticos);
		scrollPane.setBounds(10, 62, 577, 195);
		panelDiagnosticos.add(scrollPane);

		JButton btnVolver = new JButton("Volver");
		btnVolver.setIcon(iconVolver);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelDiagnosticos.setVisible(false);
				panelMain.setVisible(true);
			}
		});
		btnVolver.setBounds(232, 280, 137, 23);
		panelDiagnosticos.add(btnVolver);


		JLabel lblDiagnsticos = new JLabel("Consulta de Diagn\u00F3sticos");
		lblDiagnsticos.setBounds(10, 22, 234, 14);
		panelDiagnosticos.add(lblDiagnsticos);


		/*Tratamientos*/
		JPanel panelTratamientos = new JPanel();
		frmHistoriasClinicas.getContentPane().add(panelTratamientos, "name_8943794447631");
		panelTratamientos.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("Consulta de Tratamientos");
		lblNewLabel_3.setBounds(10, 21, 249, 14);
		panelTratamientos.add(lblNewLabel_3);

		tableTratamientos = new JTable() {			
			public boolean isCellEditable(int row, int column){  
		          return false;  
		      }
		};	
		updateTableTratamientos();
		tableTratamientos.getColumnModel().getColumn(0).setPreferredWidth(88);
		tableTratamientos.getColumnModel().getColumn(3).setPreferredWidth(214);
		tableTratamientos.setPreferredScrollableViewportSize(new Dimension(500, 70));
		JScrollPane scrollPaneTrat = new JScrollPane(tableTratamientos);
		scrollPaneTrat.setBounds(10, 62, 577, 195);
		panelTratamientos.add(scrollPaneTrat);

		JButton btnVolver_1 = new JButton("Volver");
		btnVolver_1.setIcon(iconVolver);
		btnVolver_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelTratamientos.setVisible(false);
				panelMain.setVisible(true);
			}
		});
		btnVolver_1.setBounds(229, 274, 150, 23);
		panelTratamientos.add(btnVolver_1);



		/*Pacientes*/
		JPanel panelConsultaPacientes = new JPanel();
		frmHistoriasClinicas.getContentPane().add(panelConsultaPacientes, "name_8943794447631");
		panelConsultaPacientes.setLayout(null);

		JLabel lblNewLabel_5 = new JLabel("Listado de Pacientes");
		lblNewLabel_5.setBounds(20, 21, 272, 14);
		panelConsultaPacientes.add(lblNewLabel_5);

		tablePacientes = new JTable() {			
			public boolean isCellEditable(int row, int column){  
		          return false;  
		      }
		};
		updateTablePacientes();
		tablePacientes.getColumnModel().getColumn(3).setPreferredWidth(50);
		tablePacientes.getColumnModel().getColumn(4).setPreferredWidth(40);
		tablePacientes.setPreferredScrollableViewportSize(new Dimension(500, 70));
		JScrollPane scrollPanePac = new JScrollPane(tablePacientes);
		scrollPanePac.setBounds(10, 62, 577, 195);
		panelConsultaPacientes.add(scrollPanePac);

		JButton btnVolver_2 = new JButton("Volver");
		btnVolver_2.setIcon(iconVolver);
		btnVolver_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelConsultaPacientes.setVisible(false);
				panelMain.setVisible(true);
			}
		});
		btnVolver_2.setBounds(237, 268, 143, 23);
		panelConsultaPacientes.add(btnVolver_2);


		//* Menú */
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 597, 21);
		panelMain.add(menuBar);

		JMenu mnAdministracin = new JMenu("Administraci\u00F3n");
		menuBar.add(mnAdministracin);

		JMenuItem mntmMdicos = new JMenuItem("M\u00E9dicos");
		mnAdministracin.add(mntmMdicos);

		JMenuItem mntmTiposRegistrosMdicos = new JMenuItem("Tipos Registros M\u00E9dicos");
		mnAdministracin.add(mntmTiposRegistrosMdicos);

		JMenuItem mntmUsuarios = new JMenuItem("Usuarios");
		mnAdministracin.add(mntmUsuarios);

		JMenu mnPacientes = new JMenu("Pacientes");
		menuBar.add(mnPacientes);

		JMenuItem mntmRegisrarPaciente = new JMenuItem("Registrar Paciente");
		mntmRegisrarPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCrearPaciente.setVisible(true);
				panelMain.setVisible(false);
			}
		});
		mnPacientes.add(mntmRegisrarPaciente);

		JMenuItem mntmListarPacientes = new JMenuItem("Listar Pacientes");
		mntmListarPacientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTablePacientes();
				panelConsultaPacientes.setVisible(true);
				panelMain.setVisible(false);
			}
		});
		mnPacientes.add(mntmListarPacientes);

		JMenu mnHistoriasClnicas = new JMenu("Historias Cl\u00EDnicas");
		menuBar.add(mnHistoriasClnicas);

		JMenuItem mntmRegistrarHistoria = new JMenuItem("Registrar Historia");
		mntmRegistrarHistoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarListas();
				panelHistoriaClinica.setVisible(true);
				panelMain.setVisible(false);
			}
		});
		mnHistoriasClnicas.add(mntmRegistrarHistoria);

		JMenuItem mntmListarHistorias = new JMenuItem("Listar Historias");
		mnHistoriasClnicas.add(mntmListarHistorias);

		JMenuItem mntmRegistroMedico = new JMenuItem("Registro M\u00E9dico");
		mntmRegistroMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cargarListas();
				panelRegistroMedico.setVisible(true);
				panelMain.setVisible(false);
			}
		});
		mnHistoriasClnicas.add(mntmRegistroMedico);

		JMenu mnConsultas = new JMenu("Consultas");
		menuBar.add(mnConsultas);

		JMenuItem mntmDiagnsticos = new JMenuItem("Diagn\u00F3sticos");
		mntmDiagnsticos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateTableDiagnosticos();
				panelDiagnosticos.setVisible(true);
				panelMain.setVisible(false);
			}
		});
		mnConsultas.add(mntmDiagnsticos);

		JMenuItem mntmTratamientos = new JMenuItem("Tratamientos");
		mntmTratamientos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTableTratamientos();
				panelTratamientos.setVisible(true);
				panelMain.setVisible(false);
			}
		});
		mnConsultas.add(mntmTratamientos);

		JMenu mnSalida = new JMenu("Salida");
		menuBar.add(mnSalida);

		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnSalida.add(mntmSalir);
		//**Menu


	}


	/**
	 * Inicialización de información
	 */
	private void cargarListas(){
		DefaultComboBoxModel<String> modelIdentificacion = new DefaultComboBoxModel(paciente.listaDocumentosPacientes().toArray());
		comboBoxHistClinica.setModel(modelIdentificacion);
		comboBoxRegistro.setModel(modelIdentificacion);
		//comboBoxConsultaReg.setModel(modelIdentificacion);
	}

	private void updateTablePacientes(){
		Object[][] dataPac = new Object[1][columnNamesPacientes.length];
		dataPac = paciente.getTablePacientes(columnNamesPacientes.length);
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNamesPacientes);
		for(Object[] obj : dataPac){
			model.addRow(obj);
		}
		tablePacientes.setModel(model);
	}

	private void updateTableDiagnosticos(){
		RegistroMedico registroMedico=new RegistroMedico();
		Object[][] dataDiag = new Object[1][columnNamesDiagnosticos.length];
		dataDiag =  registroMedico.getTableDiagnosticosByPaciente(columnNamesDiagnosticos.length);
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNamesDiagnosticos);
		for(Object[] obj : dataDiag){
			model.addRow(obj);
		}
		tableDiagnosticos.setModel(model);
	}

	private void updateTableTratamientos(){
		RegistroMedico registroMedico=new RegistroMedico();
		Object[][] dataTrat = new Object[1][columnNamesDiagnosticos.length];
		dataTrat =  registroMedico.getTableTratamientos(columnNamesDiagnosticos.length);
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNamesTratamientos);
		for(Object[] obj : dataTrat){
			model.addRow(obj);
		}
		tableTratamientos.setModel(model);
	}

}
