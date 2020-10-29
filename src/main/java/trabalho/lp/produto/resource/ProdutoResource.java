package trabalho.lp.produto.resource;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import trabalho.lp.produto.dto.ProdutoDTO;
import trabalho.lp.produto.form.AtualizarProdutoFORM;
import trabalho.lp.produto.form.ProdutoFORM;
import trabalho.lp.produto.service.ProdutoService;


@RestController
@RequestMapping("/produto")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService produtoService;

	
	/**
	 * Método responsável por chamar o serviço de listar todos os Produtos
	 * @return ResponseEntity - List de ProdutoDTO (Retorna a resposta da requisição)
	 */
	@GetMapping("/listar-todos")
	public ResponseEntity<List<ProdutoDTO>> listarTodosProdutos() {
		return produtoService.listarTodosProdutos();
	}
	
	
	/**
	 * Método responsável por chamar o serviço de cadastrar Produto
	 * @param produtoFORM : ProdutoFORM
	 * @return ResponseEntity - Void (Retorna a resposta da requisição)
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Void> cadastrarProduto(@RequestBody @Valid ProdutoFORM produtoFORM) {
		return produtoService.cadastrarProduto(produtoFORM);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de atualização de Produto
	 * @param id : Long
	 * @param atualizarProdutoFORM : AtualizarProdutoFORM
	 * @return ResponseEntity - Void (Retorna a resposta da requisição)
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizarProduto(@PathVariable Long id, @RequestBody @Valid AtualizarProdutoFORM atualizarProdutoFORM) {
		return produtoService.atualizarProduto(id, atualizarProdutoFORM);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de remoção de Produto
	 * @param id : Long
	 * @return ResponseEntity - Void (Retorna a resposta da requisição)
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removerProduto(@PathVariable Long id) {
		return produtoService.removerProduto(id);
	}
}
