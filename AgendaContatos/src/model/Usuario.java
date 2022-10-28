package model;

public class Usuario extends Pessoa{
	private String cpf;
	private String senha;

	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "Usuario [Nome = " + getNome() + ", CPF/CNPJ = " + getCpf() + ", Email = " + getEmail()
				+ ", Senha = " + getSenha() + "]";
	}
	
}
