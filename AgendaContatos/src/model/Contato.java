package model;

public class Contato extends Pessoa{
	private String telefone;
	private String endereco;
	private String usuario_cpf;
	private int id;
	
	public Contato() {
		
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getUsuario_cpf() {
		return usuario_cpf;
	}
	
	public void setUsuario_cpf(String usuario_cpf) {
		this.usuario_cpf = usuario_cpf;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Contato [Nome = " + getNome() + ", Telefone = " + getTelefone() + ", Email = " + getEmail()
				+ ", Endereco = " + getEndereco() + "]";
	}
	
	
}
