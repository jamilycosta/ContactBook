package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
	
	private Connection con;
	
	public UsuarioDAO() {
		con = new ConnectionFactory().getConnection();
	}
	
	public void cadastrar(Usuario usuario) {
		String sql = "INSERT INTO usuarios(nome, cpf, email, senha) "
				+ "VALUES(?, ?, ?, ?);";
		
		try {
			PreparedStatement prepStat = con.prepareStatement(sql);
			
			prepStat.setString(1, usuario.getNome());
			prepStat.setString(2, usuario.getCpf());
			prepStat.setString(3, usuario.getEmail());
			prepStat.setString(4, usuario.getSenha());
			
			prepStat.execute();
			prepStat.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void atualizar(Usuario usuario) {
		String sql = "UPDATE usuarios SET nome = ?, cpf = ?, email = ?, senha = ? "
				+ "WHERE cpf = ?;";
		
		try {
			PreparedStatement prepStat = con.prepareStatement(sql);
			
			prepStat.setString(1, usuario.getNome());
			prepStat.setString(2, usuario.getCpf());
			prepStat.setString(3, usuario.getEmail());
			prepStat.setString(4, usuario.getSenha());
			prepStat.setString(5, usuario.getCpf());
			
			prepStat.execute();
			prepStat.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void deletar(String cpfUsuario) {
		String sql = "DELETE FROM usuarios WHERE cpf = ?;";
		
		try {
			PreparedStatement prepStat = con.prepareStatement(sql);
			
			prepStat.setString(1, cpfUsuario);
			
			prepStat.execute();
			prepStat.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public String consultar(String cpfUsuario, String senhaUsuario) {
		
		String sql = "SELECT cpf FROM usuarios WHERE cpf = '" + cpfUsuario + "' AND senha = '" 
				+ senhaUsuario + "';";
		
		try {
			ResultSet resultado = null;

			PreparedStatement prepStat = con.prepareStatement(sql);
			
			resultado = prepStat.executeQuery();
			
			int cpf = 1;
			
			if(resultado != null){
				while(resultado.next()){
					return resultado.getString(cpf);
				}
			}
			
			resultado.close();
			prepStat.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return "";
	}
}
