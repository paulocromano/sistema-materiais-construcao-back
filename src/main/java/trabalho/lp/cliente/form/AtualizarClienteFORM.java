package trabalho.lp.cliente.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import trabalho.lp.cliente.model.Cliente;

public class AtualizarClienteFORM {

	private String telefone;
	
	@NotNull(message = "Senha não informada!")
	@NotEmpty(message = "O campo Senha não pode estar vazia!")
	@Size(min = 6, max = 20, message = "O campo senha deve ter entre {min} a {max} caracteres!")
	@Pattern(regexp = "((?=.*\\d)(?=.*[A-Z])(?=.*\\W).{6,20})", message = "A senha deve conter caracteres alfanuméricos, um caracter maiúsculo e um caracter especial!")
	private String senha;
	
	
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	public void atualizarCliente(Cliente cliente, BCryptPasswordEncoder bCryptPasswordEncoder) {
		cliente.setTelefone(telefone);
		cliente.setSenha(bCryptPasswordEncoder.encode(senha));
	}
}
