package trabalho.lp.cliente.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import trabalho.lp.cliente.enums.PerfilCliente
;
import trabalho.lp.cliente.repository.ClienteRepository;
import trabalho.lp.compra.pedido.model.Pedido;
import trabalho.lp.exception.service.ObjectNotFoundException;
import trabalho.lp.utils.Converter;
@Entity
@Table(name = "cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	private String nome;
	
	@Column(unique = true)
	private String email;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "perfil")
	private Set<Integer> perfis = new HashSet<>();
	
	@JsonIgnore
	private String senha;
	
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;
	
	private String telefone;
	
	@JsonIgnore
	@OneToMany(mappedBy="cliente", cascade = CascadeType.ALL)
	private List<Pedido> pedidos = new ArrayList<>();
	
	
	public Cliente() {
		
	}

	/**
	 * Construtor de Cliente
	 * @param nome : String
	 * @param email : String
	 * @param dataNascimento : String
	 * @param senha : String
	 */
	public Cliente(String nome, String email, String dataNascimento, String senha) {
		this.nome = nome;
		this.email = email;
		adicionarPerfil(PerfilCliente.CLIENTE);
		this.dataNascimento = Converter.stringParaLocalDate(dataNascimento);
		this.senha = senha;
	}

	
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public Set<PerfilCliente> getPerfis() {
		return perfis.stream().map(perfil -> PerfilCliente.converterParaEnum(perfil)).collect(Collectors.toSet());
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}
	
	public void adicionarPerfil(PerfilCliente perfil) {
		this.perfis.add(perfil.getCodigo());
	}
	
	public void adicionarPedido(Pedido pedido) {
		pedidos.add(pedido);
	}
	
	/**
	 * Método responsável por verificar se o Cliente existe
	 * @param clienteRepository : ClienteRepository
	 * @param id : Long
	 * @return Cliente - Caso o cliente exista
	 */
	public static final Cliente existeCliente(ClienteRepository clienteRepository, Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		
		if (cliente.isEmpty()) {
			throw new ObjectNotFoundException("Cliente não encontrado!");
		}
		
		return cliente.get();
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
