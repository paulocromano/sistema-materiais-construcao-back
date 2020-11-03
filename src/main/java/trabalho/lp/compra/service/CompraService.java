package trabalho.lp.compra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import trabalho.lp.cliente.model.Cliente;
import trabalho.lp.cliente.repository.ClienteRepository;
import trabalho.lp.compra.dto.CompraDTO;
import trabalho.lp.compra.form.CompraFORM;
import trabalho.lp.compra.model.Compra;
import trabalho.lp.compra.repository.CompraRepository;
import trabalho.lp.exception.service.ObjectNotFoundException;
import trabalho.lp.produto.model.Produto;
import trabalho.lp.produto.repository.ProdutoRepository;
import trabalho.lp.security.UsuarioSecurity;
import trabalho.lp.utils.VerificarUsuario;


@Service
public class CompraService {

	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	public ResponseEntity<List<CompraDTO>> listarTodasCompras() {
		return ResponseEntity.ok().body(CompraDTO.converterParaListaCompraDTO(compraRepository.findAll()));
	}
	
	
	public ResponseEntity<List<CompraDTO>> listarComprasCliente() {
		UsuarioSecurity usuario = VerificarUsuario.usuarioEValido();
		VerificarUsuario.usuarioTemPermissao(usuario.getId());
		
		return ResponseEntity.ok().body(CompraDTO.converterParaListaCompraDTO(compraRepository.findByCliente_Id(usuario.getId())));
	}
	
	public ResponseEntity<Double> totalComprasCliente() {
		UsuarioSecurity usuario = VerificarUsuario.usuarioEValido();
		VerificarUsuario.usuarioTemPermissao(usuario.getId());
		
		List<Compra> comprasCliente = compraRepository.findByCliente_Id(usuario.getId());
		
		return ResponseEntity.ok().body(comprasCliente
				.stream()
				.mapToDouble(compra -> compra.getPreco() * compra.getQuantidade())
				.sum());
	}
	
	
	public ResponseEntity<Void> comprarProduto(Long idProduto, CompraFORM compraFORM) {
		UsuarioSecurity usuario = VerificarUsuario.usuarioEValido();
		
		Optional<Cliente> cliente = clienteRepository.findById(usuario.getId());
		Optional<Produto> produto = produtoRepository.findById(idProduto);
		
		Integer estoqueRestante = validacoesCompra(idProduto, cliente, produto, compraFORM);
		produto.get().setEstoque(estoqueRestante);
		
		compraRepository.save(compraFORM.converterParaCompra(cliente.get(), produto.get()));
		
		return ResponseEntity.ok().build();
	}
	
	
	private Integer validacoesCompra(Long idProduto, Optional<Cliente> cliente, Optional<Produto> produto, CompraFORM compraFORM) {
		if (idProduto == null) {
			throw new NullPointerException("O ID do Produto não pode ser nulo!");
		}
		
		if (cliente.isEmpty()) {
			throw new ObjectNotFoundException("Cliente não encontrado!");
		}
		
		if (produto.isEmpty()) {
			throw new ObjectNotFoundException("Produto não encontrado!");
		}
		
		Integer estoqueRestante = produto.get().getEstoque() - compraFORM.getQuantidade();
		if (estoqueRestante < 0) {
			throw new IllegalArgumentException("A quantidade requisitada excedeu o Estoque do Produto!");
		}
		
		return estoqueRestante;
	}
}
