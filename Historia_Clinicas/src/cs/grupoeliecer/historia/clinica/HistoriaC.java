package cs.grupoeliecer.historia.clinica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HistoriaC {

	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	
	private String idHistoriaClinica;
	private String HistoriaAntecedentes;
	private String Usuario_idUsuario;
	private String Paciente_idPaciente;
	
	
	public String getIdHistoriaClinica() {
		return idHistoriaClinica;
	}

	public void setIdHistoriaClinica(String idHistoriaClinica) {
		this.idHistoriaClinica = idHistoriaClinica;
	}

	public String getHistoriaAntecedentes() {
		return HistoriaAntecedentes;
	}

	public void setHistoriaAntecedentes(String historiaAntecedentes) {
		HistoriaAntecedentes = historiaAntecedentes;
	}

	public String getUsuario_idUsuario() {
		return Usuario_idUsuario;
	}

	public void setUsuario_idUsuario(String usuario_idUsuario) {
		Usuario_idUsuario = usuario_idUsuario;
	}

	public String getPaciente_idPaciente() {
		return Paciente_idPaciente;
	}

	public void setPaciente_idPaciente(String paciente_idPaciente) {
		Paciente_idPaciente = paciente_idPaciente;
	}


	
	public HistoriaC() {
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
	
	/**
	 * Obtiene historia clínica de un paciente
	 * @param idPaciente
	 */
	public void loadDataByPaciente(String idPaciente){
		try{
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM CSHistoriasClinicas.Historia_Clinica WHERE Paciente_idPaciente = '" + idPaciente + "'");
			if(resultSet.next()){
				this.idHistoriaClinica = resultSet.getString("idHistoria_Clinica");
				this.HistoriaAntecedentes = resultSet.getString("Historia_Antecedentes");
				this.Usuario_idUsuario = resultSet.getString("Usuario_idUsuario");
				this.Paciente_idPaciente = resultSet.getString("Paciente_idPaciente");
			}
			//connect.close();
		} catch(SQLException e){
			e.printStackTrace();
		} 
	}
	
}
