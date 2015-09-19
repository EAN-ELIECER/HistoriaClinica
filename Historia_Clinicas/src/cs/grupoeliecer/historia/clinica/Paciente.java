package cs.grupoeliecer.historia.clinica;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Paciente {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private String idPaciente;
	private String strNombres;
	private String strApellidos;
	private String strIdentificacion;
	private String strFechaNacimiento;
	private String strGenero;
	private int intEdad;
	private String strEPS;
	private String strGrupoRH;

	public Paciente(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			//connect = DriverManager.getConnection("jdbc:mysql://cscicg.cy2i8gkfguzu.us-west-2.rds.amazonaws.com/CSHistoriasClinicas?user=Administrador&password=grupoeliecer2015");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/CSHistoriasClinicas?user=root&password=");
		} catch(SQLException e){
			e.printStackTrace();
		} catch(ClassNotFoundException ex){
			ex.printStackTrace();
		}
	}

	public boolean save(String aStrNombres, String aStrApellidos, String aStrIdent, String aStrFechaNac, String aStrGenero, int aIntEdad, String aStrEps, String aStrGrupoRH, int aIntIdUser) throws SQLException {

		boolean success;
		try{
			statement = connect.createStatement();
			preparedStatement = connect.prepareStatement("INSERT INTO  CSHistoriasClinicas.Paciente VALUES(default, ?,?,?,?,?,?,?,?,?)");
			preparedStatement.setString(1, aStrNombres);
			preparedStatement.setString(2, aStrApellidos);
			preparedStatement.setString(3, aStrIdent);
			preparedStatement.setString(4, aStrGenero);
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date myDate = formatter.parse(aStrFechaNac);
			java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
			preparedStatement.setDate(5, sqlDate);
			preparedStatement.setInt(6, aIntEdad);
			preparedStatement.setString(7, aStrEps);
			preparedStatement.setString(8, aStrGrupoRH);
			preparedStatement.setInt(9, aIntIdUser);
			preparedStatement.executeUpdate();
			connect.close();
			success = true;
		} catch(SQLException | ParseException e){
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	public String getNombres(){
		return this.strNombres;
	}

	public String getApellidos(){
		return this.strApellidos;
	}

	public String getNumIdentificacion(){
		return this.strIdentificacion;
	}

	public String getGenero(){
		return this.strGenero;
	}

	public String getFechaNacimiento(){
		return this.strFechaNacimiento;
	}

	public int getEdad(){
		return this.intEdad;
	}

	public String getEPS(){
		return this.strEPS;
	}

	public String getGrupoRH(){
		return this.strGrupoRH;
	}


	public void loadData(String aStrNumIdent){

		try{
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM CSHistoriasClinicas.Paciente WHERE Identificacion = '" + aStrNumIdent + "'");
			if(resultSet.next()){
				this.idPaciente = resultSet.getString("idPaciente");
				this.strNombres = resultSet.getString("Nombres");
				this.strApellidos = resultSet.getString("Apellidos");
				this.strIdentificacion = resultSet.getString("Identificacion");
				this.strGenero = resultSet.getString("Genero");
				this.strFechaNacimiento = resultSet.getDate("Fecha_Nacimiento").toString();
				this.intEdad = resultSet.getInt("Edad_Paciente");
				this.strEPS = resultSet.getString("EPS");
				this.strGrupoRH = resultSet.getString("Grupo_Sanguineo");
			}
			//connect.close();
		} catch(SQLException e){
			e.printStackTrace();
		} 

	}

	/**
	 * Obtiene listado de identificaciones de los pacientes
	 * @return
	 */
	public ArrayList<String> listaDocumentosPacientes(){
		ArrayList<String> listadoDocumentos = new ArrayList<String>();	
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT Identificacion FROM CSHistoriasClinicas.Paciente");
			while(resultSet.next()){
				listadoDocumentos.add(resultSet.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listadoDocumentos;		
	}


	/**
	 * Guarda historia clínica del paciente
	 * @param identificacion
	 * @param antecedentes
	 * @return
	 */
	public boolean saveHistoriaClinica(String identificacion, String antecedentes){
		Boolean save=false;
		try {
			loadData(identificacion);
			if(this.idPaciente!=null){
				preparedStatement=connect.prepareStatement("INSERT INTO CSHistoriasClinicas.Historia_Clinica VALUES (default, ?, 1, ?) ");
				preparedStatement.setString(1, antecedentes);
				preparedStatement.setString(2, this.idPaciente);
				preparedStatement.execute();
				save=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return save;		
	}


	/**
	 * Guarda registro médico (diagnóstico y tratamiento) del paciente
	 * @param identificacion
	 * @param diagnostico
	 * @param tratamiento
	 * @return
	 */
	public boolean saveDiagnosticoTratamiento(String identificacion, String diagnostico, String tratamiento){
		Boolean save=false;
		String idHistoriaClinica;
		try {
			loadData(identificacion);
			if(this.idPaciente!=null){
				HistoriaC historiaClinica = new HistoriaC();
				historiaClinica.loadDataByPaciente(this.idPaciente);
				idHistoriaClinica=historiaClinica.getIdHistoriaClinica();
				if(idHistoriaClinica!=null){
					preparedStatement=connect.prepareStatement("INSERT INTO CSHistoriasClinicas.Registro_Medico VALUES (default, NOW(), 1, 1, ?, 0, 1, 1, ?, ?) ");
					preparedStatement.setString(1, idHistoriaClinica);
					preparedStatement.setString(2, diagnostico);
					preparedStatement.setString(3, tratamiento);
					preparedStatement.execute();
					save=true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return save;		
	}
	
	/**
	 * Obtiene tabla de pacientes
	 * @param columnas
	 * @return
	 */
	public Object[][] getTablePacientes(int columnas){
		Object[][] data = new Object[1][columnas];
		int i=0;
			try{
				statement = connect.createStatement();
				resultSet = statement.executeQuery("SELECT * FROM CSHistoriasClinicas.Paciente");
				resultSet.last(); 				
				int total = resultSet.getRow();
				resultSet.beforeFirst();
				data = new Object[total][columnas];
				while(resultSet.next()){
					data[i][0]=resultSet.getString("Apellidos");
					data[i][1]=resultSet.getString("Nombres");
					data[i][2]=resultSet.getString("Identificacion");
					data[i][3]=resultSet.getString("Genero");
					data[i][4]=resultSet.getString("Edad_Paciente");
					data[i][5]=resultSet.getString("EPS");
					i++;
				}
			
			} catch(SQLException e){
				e.printStackTrace();
			} 
	
		return data;
	}
	
}
