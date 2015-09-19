package cs.grupoeliecer.historia.clinica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegistroMedico {


	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	private String idRegistro_Medico;
	private String fecha_Registro;
	private String idUsuario;
	private String idHistoria_Clinica;
	private String diagnostico;
	private String tratamiento;


	public RegistroMedico() {
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

	public String getIdRegistro_Medico() {
		return idRegistro_Medico;
	}

	public void setIdRegistro_Medico(String idRegistro_Medico) {
		this.idRegistro_Medico = idRegistro_Medico;
	}

	public String getFecha_Registro() {
		return fecha_Registro;
	}

	public void setFecha_Registro(String fecha_Registro) {
		this.fecha_Registro = fecha_Registro;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getIdHistoria_Clinica() {
		return idHistoria_Clinica;
	}

	public void setIdHistoria_Clinica(String idHistoria_Clinica) {
		this.idHistoria_Clinica = idHistoria_Clinica;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}


	/**
	 * Obtiene Registro médico de un paciente
	 * @param idPaciente
	 */
	public void loadDataByPaciente(String idPaciente){
		HistoriaC historiaClinica=new HistoriaC();
		historiaClinica.loadDataByPaciente(idPaciente);
		String idHistoriaClinica=historiaClinica.getIdHistoriaClinica();
		if(idHistoriaClinica!=null){
			try{
				statement = connect.createStatement();
				resultSet = statement.executeQuery("SELECT * FROM CSHistoriasClinicas.Registro_Medico WHERE Historia_Clinica_idHistoria_Clinica = '" + idHistoriaClinica + "'");
				if(resultSet.next()){
					this.diagnostico=resultSet.getString("Diagnostico");
					this.tratamiento=resultSet.getString("Ttratamiento");
					this.fecha_Registro=resultSet.getString("Fecha_Registro");
					this.idHistoria_Clinica=idHistoriaClinica;
				}
				//connect.close();
			} catch(SQLException e){
				e.printStackTrace();
			} 
		}
	}

	/**
	 * Obtiene información con información de diagnósticos de pacientes
	 * @param columnas
	 * @return
	 */
	public Object[][] getTableDiagnosticosByPaciente(int columnas){
		Object[][] data = new Object[1][columnas];
		int i=0;
		try{
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT  "+
					"rm.idRegistro_Medico, "+
					"rm.Fecha_Registro, "+
					"rm.Historia_Clinica_idHistoria_Clinica, "+
					"rm.Diagnostico, "+
					"rm.Tratamiento, "+
					"p.Apellidos, "+
					"p.Nombres "+
					"FROM CSHistoriasClinicas.Registro_Medico rm "+
					"JOIN Historia_Clinica hc ON hc.idHistoria_Clinica = rm.Historia_Clinica_idHistoria_Clinica "+
					"JOIN Paciente p ON p.idPaciente = hc.Paciente_idPaciente");
			resultSet.last(); 				
			int total = resultSet.getRow();
			resultSet.beforeFirst();
			data = new Object[total][columnas];
			while(resultSet.next()){
				data[i][0]=resultSet.getString("Fecha_Registro");
				data[i][1]=resultSet.getString("Apellidos");
				data[i][2]=resultSet.getString("Nombres");
				data[i][3]=resultSet.getString("Diagnostico");
				i++;
			}
		} catch(SQLException e){
			e.printStackTrace();
		} 
		return data;
	}

	/**
	 * Obtiene arreglo con información de tratamientos de pacientes
	 * @param columnas
	 * @return
	 */
	public Object[][] getTableTratamientos(int columnas){
		Object[][] data = new Object[1][columnas];
		int i=0;
		try{
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT  "+
					"rm.idRegistro_Medico, "+
					"rm.Fecha_Registro, "+
					"rm.Historia_Clinica_idHistoria_Clinica, "+
					"rm.Diagnostico, "+
					"rm.Tratamiento, "+
					"p.Apellidos, "+
					"p.Nombres "+
					"FROM CSHistoriasClinicas.Registro_Medico rm "+
					"JOIN Historia_Clinica hc ON hc.idHistoria_Clinica = rm.Historia_Clinica_idHistoria_Clinica "+
					"JOIN Paciente p ON p.idPaciente = hc.Paciente_idPaciente");
			resultSet.last(); 				
			int total = resultSet.getRow();
			resultSet.beforeFirst();
			data = new Object[total][columnas];
			while(resultSet.next()){
				data[i][0]=resultSet.getString("Fecha_Registro");
				data[i][1]=resultSet.getString("Apellidos");
				data[i][2]=resultSet.getString("Nombres");
				data[i][3]=resultSet.getString("Tratamiento");
				i++;
			}

		} catch(SQLException e){
			e.printStackTrace();
		} 

		return data;
	}

}
