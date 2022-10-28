package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.Main.Chave;

public class ContatoDAO {
	
	private Connection con;
	
	public ContatoDAO() {
		con = new ConnectionFactory().getConnection();
	}
	
	public void cadastrar(Contato contato) {
		String sql = "INSERT INTO contatos(nome, telefone, email, endereco, usuario_cpf) "
				+ "VALUES(?, ?, ?, ?, ?);";
		
		try {
			PreparedStatement prepStat = con.prepareStatement(sql);
			
			prepStat.setString(1, contato.getNome());
			prepStat.setString(2, contato.getTelefone());
			prepStat.setString(3, contato.getEmail());
			prepStat.setString(4, contato.getEndereco());
			prepStat.setString(5, contato.getUsuario_cpf());
			
			prepStat.execute();
			prepStat.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void atualizar(Contato contato) {
		String sql = "UPDATE contatos SET nome = ?, telefone = ?, email = ?, endereco = ? "
				+ "WHERE id = ?;";
		
		try {
			PreparedStatement prepStat = con.prepareStatement(sql);
			
			prepStat.setString(1, contato.getNome());
			prepStat.setString(2, contato.getTelefone());
			prepStat.setString(3, contato.getEmail());
			prepStat.setString(4, contato.getEndereco());
			prepStat.setInt(5, contato.getId());
			
			prepStat.execute();
			prepStat.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void deletar(int id) {
		String sql = "DELETE FROM contatos WHERE id = ?;";
		
		try {
			PreparedStatement prepStat = con.prepareStatement(sql);
			
			prepStat.setInt(1, id);
			
			prepStat.execute();
			prepStat.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public List<Contato> consultar(String nomeContato) {
		List<Contato> contatos = new ArrayList<>();
		
		String sql = "SELECT * FROM contatos WHERE nome LIKE '%" + nomeContato + "%' AND usuario_cpf = '"
				+ Chave.getChave() + "';";
		
		try {
			PreparedStatement prepStat = con.prepareStatement(sql);
			
			ResultSet resultado = prepStat.executeQuery();
			
			while(resultado.next()) {
				Contato contato = new Contato();
				
				contato.setId(resultado.getInt("id"));
				contato.setNome(resultado.getString("nome"));
				contato.setTelefone(resultado.getString("telefone"));
				contato.setEmail(resultado.getString("email"));
				contato.setEndereco(resultado.getString("endereco"));
				contato.setUsuario_cpf(Chave.getChave());
				
				contatos.add(contato);
			}
			
			resultado.close();
			prepStat.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return contatos;
	}
	
}
