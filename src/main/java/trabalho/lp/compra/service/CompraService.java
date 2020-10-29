package trabalho.lp.compra.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import trabalho.lp.compra.dto.CompraDTO;
import trabalho.lp.compra.repository.CompraRepository;
import trabalho.lp.security.UsuarioSecurity;
import trabalho.lp.utils.VerificarUsuario;


@Service
public class CompraService {

	@Autowired
	private CompraRepository compraRepository;
	
	
	public ResponseEntity<List<CompraDTO>> listarTodasCompras() {
		return ResponseEntity.ok().body(CompraDTO.converterParaListaCompraDTO(compraRepository.findAll()));
	}
	
	
	public ResponseEntity<List<CompraDTO>> listarComprasCliente() {
		UsuarioSecurity usuario = VerificarUsuario.usuarioEValido();
		VerificarUsuario.usuarioTemPermissao(usuario.getId());
		
		return ResponseEntity.ok().body(CompraDTO.converterParaListaCompraDTO(compraRepository.findByCliente_Id(usuario.getId())));
	}
}
