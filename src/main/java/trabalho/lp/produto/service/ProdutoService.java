package trabalho.lp.produto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import trabalho.lp.exception.service.DataIntegrityException;
import trabalho.lp.exception.service.ObjectNotFoundException;
import trabalho.lp.produto.dto.ProdutoDTO;
import trabalho.lp.produto.form.AtualizarProdutoFORM;
import trabalho.lp.produto.form.ProdutoFORM;
import trabalho.lp.produto.model.Produto;
import trabalho.lp.produto.repository.ProdutoRepository;


@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	/**
	 * Método responsável por listar todos os Produtos
	 * @return ResponseEntity - List de ProdutoDTO
	 */
	public ResponseEntity<List<ProdutoDTO>> listarTodosProdutos() {
		return ResponseEntity.ok().body(ProdutoDTO.converterParaListaProdutoDTO(produtoRepository.findAll()));
	}
	
	
	/**
	 * Método responsável por cadastrar um Produto
	 * @param produtoFORM : ProdutoFORM
	 * @return ResponseEntity - Void
	 */
	public ResponseEntity<Void> cadastrarProduto(ProdutoFORM produtoFORM) { 
		produtoRepository.save(produtoFORM.converterParaProduto());

		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por atualizar as informações de um Produto
	 * @param id : Long
	 * @param atualizarProdutoFORM : AtualizarProdutoFORM
	 * @return ResponseEntity - Void
	 */
	public ResponseEntity<Void> atualizarProduto(Long id, AtualizarProdutoFORM atualizarProdutoFORM) {
		Optional<Produto> produtoBuscado = produtoRepository.findById(id);
		
		if (produtoBuscado.isEmpty()) {
			throw new ObjectNotFoundException("Produto informado não existe!");
		}
		
		atualizarProdutoFORM.atualizarProduto(produtoBuscado.get());
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por remover um Produto
	 * @param id : Integer
	 * @return ResponseEntity - Void
	 */
	public ResponseEntity<Void> removerProduto(Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		
		if (produto.isEmpty()) {
			throw new ObjectNotFoundException("Produto informado não existe!");
		}
		
		try {
			produtoRepository.deleteById(id);;
		}
		catch (RuntimeException e) {
			throw new DataIntegrityException("Não foi possível remover! Clientes compraram este Produto.");
		}
		
		return ResponseEntity.ok().build();
	}
}
