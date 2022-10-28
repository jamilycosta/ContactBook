package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	private String user = "postgres";
	private String senha = "";
	private String url = "jdbc:postgresql://localhost:5432/agenda";
	private Connection con = null;

	public Connection getConnection() {
		try
		{
			this.con = (Connection)DriverManager.getConnection(url, user, senha);
			//System.out.println("Conexão realizada com sucesso.");
			return con;
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void fecharConexao(){
		try{
			if(con != null)
				con.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
