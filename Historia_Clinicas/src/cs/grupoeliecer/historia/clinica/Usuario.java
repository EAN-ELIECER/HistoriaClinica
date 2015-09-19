package cs.grupoeliecer.historia.clinica;

import java.sql.*;

public class Usuario {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public Usuario() throws SQLException {
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
	
	public boolean save(String aStrUsuario, String aStrContrasena) throws SQLException {
		
		boolean success;
		try{
			statement = connect.createStatement();
			preparedStatement = connect.prepareStatement("INSERT INTO  CSHistoriasClinicas.Usuario VALUES(default, ?, ?)");
			preparedStatement.setString(1, aStrUsuario);
		    preparedStatement.setString(2, aStrContrasena);
		    preparedStatement.executeUpdate();
		    //connect.close();
			success = true;
		} catch(SQLException e){
			e.printStackTrace();
			success = false;
		}
		return success;
	}
	
	public String getContrasena(String aStrUsuario) {
		
		String strContrasena = "";
		
		try{
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT Contrasena FROM CSHistoriasClinicas.Usuario WHERE Usuario = '" + aStrUsuario + "'");
			if(resultSet.next()){
				strContrasena = resultSet.getString(1);
			}
			connect.close();
		} catch(SQLException e){
			e.printStackTrace();
		} 
		
		return strContrasena;
	}
}
