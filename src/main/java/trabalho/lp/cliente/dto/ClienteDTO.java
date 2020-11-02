package trabalho.lp.cliente.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import trabalho.lp.cliente.model.Cliente;
import trabalho.lp.compra.model.Compra;
import trabalho.lp.utils.Converter;

public class ClienteDTO {

	private Long id; 
	private String nome;
	private String email;
	private String permissoes;
	private String dataNascimento;
	private String telefone;
	private List<Compra> compras = new ArrayList<>();
	
	
	public ClienteDTO(Cliente cliente) {
		id = cliente.getId();
		nome = cliente.getNome();
		email = cliente.getEmail();
		permissoes = cliente.getPerfis().toString();
		
		if (cliente.getDataNascimento() != null) {
			dataNascimento = Converter.localDateParaString(cliente.getDataNascimento());
		}
		
		telefone = cliente.getTelefone();
		compras = cliente.getCompras();
	}


	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getPermissoes() {
		return permissoes;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public List<Compra> getCompras() {
		return compras;
	}
	
	
	/**
	 * Método responsável por converter um List de Cliente para List de ClienteDTO
	 * @param listaClientes : List de Cliente
	 * @return : List de ClienteDTO
	 */
	public static List<ClienteDTO> converterParaListaClienteDTO(List<Cliente> listaClientes) {
		return listaClientes.stream().map(ClienteDTO::new).collect(Collectors.toList());
	}
}
