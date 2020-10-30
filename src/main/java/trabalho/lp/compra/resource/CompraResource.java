package trabalho.lp.compra.resource;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import trabalho.lp.compra.dto.CompraDTO;
import trabalho.lp.compra.form.CompraFORM;
import trabalho.lp.compra.service.CompraService;


@RestController
@RequestMapping("/compra")
public class CompraResource {

	@Autowired
	private CompraService compraService;
	
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/listar-todas")
	public ResponseEntity<List<CompraDTO>> listarTodasCompras() {
		return compraService.listarTodasCompras();
	}
	
	
	@GetMapping("/cliente-listar")
	public ResponseEntity<List<CompraDTO>> listarComprasCliente() {
		return compraService.listarComprasCliente();
	}
	
	
	@PostMapping("/{idProduto}")
	public ResponseEntity<Void> comprarProduto(@PathVariable Long idProduto, @RequestBody @Valid CompraFORM compraFORM)  {
		return compraService.comprarProduto(idProduto, compraFORM);
	}
	
	
	@Transactional
	@DeleteMapping("/{idCompra}")
	public ResponseEntity<Void> removerCompra(@PathVariable Long idCompra) {
		return compraService.removerCompra(idCompra);
	}
}
