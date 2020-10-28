package trabalho.lp.cliente.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import trabalho.lp.cliente.dto.ClienteDTO;
import trabalho.lp.cliente.enums.PerfilCliente;
import trabalho.lp.cliente.form.AtualizarClienteFORM;
import trabalho.lp.cliente.form.ClienteFORM;
import trabalho.lp.cliente.model.Cliente;
import trabalho.lp.cliente.repository.ClienteRepository;
import trabalho.lp.exception.service.DataIntegrityException;
import trabalho.lp.exception.service.ObjectNotFoundException;
import trabalho.lp.utils.VerificarUsuario;


@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	/**
	 * Método responsável por listar todos os Clientes
	 * @return ResponseEntity - List ClienteDTO
	 */
	public ResponseEntity<List<ClienteDTO>> listarTodosClientes() {
		return ResponseEntity.ok().body(ClienteDTO.converterParaListaClienteDTO(clienteRepository.findAll()));
	}
	
	
	/**
	 * Método responsável por buscar o Cliente pelo ID 
	 * @param id : Long
	 * @return ResponseEntity - ClienteDTO
	 */
	public ResponseEntity<ClienteDTO> buscarClientePorID(Long id) {
		VerificarUsuario.usuarioTemPermissao(id);
		Cliente cliente = Cliente.existeCliente(clienteRepository, id);
		
		return ResponseEntity.ok().body(new ClienteDTO(cliente));
	}
	
	
	/**
	 * Método responsável por cadastrar um novo Cliente
	 * @param clienteFORM : ClienteFORM
	 * @return ResponseEntity - Void
	 */
	public ResponseEntity<Void> cadastrarCliente(ClienteFORM clienteFORM) {
		Cliente cliente = clienteFORM.converterParaCliente(bCryptPasswordEncoder);

		try {
			clienteRepository.save(cliente);
		}
		catch (RuntimeException e) {
			throw new DataIntegrityException("Email indisponível!");
		}
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por atualizar os dados cadastrais de um Cliente
	 * @param id : Long
	 * @param atualizarClienteFORM : AtualizarClienteFORM
	 * @return ResponseEntity - Void
	 */
	public ResponseEntity<Void> atualizarCliente(Long id, AtualizarClienteFORM atualizarClienteFORM) {
		VerificarUsuario.usuarioEValido();
		
		Optional<Cliente> cliente = clienteRepository.findById(id);
		
		if (cliente.isEmpty()) {
			throw new ObjectNotFoundException("Cliente não encontrado!");
		}
		
		atualizarClienteFORM.atualizarCliente(cliente.get(), bCryptPasswordEncoder);
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por adicionar permissão a um Cliente
	 * @param idCliente : Long
	 * @return ResponseEntity - Void
	 */
	public ResponseEntity<Void> adicionarPermissaoParaCliente(Long idCliente) {
		VerificarUsuario.usuarioEValido();
		verificarUsuarioParaDarPermissao(idCliente);
		
		Cliente cliente = clienteRepository.getOne(idCliente);
		cliente.adicionarPerfil(PerfilCliente.ADMIN);
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por remover um Cliente
	 * @param id : Long
	 * @return ResponseEntity - Void
	 */
	public ResponseEntity<Void> removerCliente(Long id) {
		VerificarUsuario.usuarioTemPermissao(id);
		Cliente.existeCliente(clienteRepository, id);
		
		clienteRepository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por verificar se o Usuário já possui permissão de ADMIN
	 * @param idCliente : Long
	 */
	private void verificarUsuarioParaDarPermissao(Long idCliente) {		
		Cliente cliente = Cliente.existeCliente(clienteRepository, idCliente);
		
		if (cliente.getPerfis().contains(PerfilCliente.ADMIN)) {
			throw new IllegalArgumentException("O Cliente informado já possui permissão de Administrador!");
		}
	}
}
