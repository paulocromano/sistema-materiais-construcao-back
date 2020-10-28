package trabalho.lp.utils;

import trabalho.lp.cliente.enums.PerfilCliente;
import trabalho.lp.exception.service.AuthorizationException;
import trabalho.lp.security.UsuarioSecurity;
import trabalho.lp.usuario.service.UsuarioService;


public final class VerificarUsuario {

	
	/**
	 * Método responsável por verficar se o Usuário está logado
	 * @return UsuarioSecurity - Caso o Usuário esteja logado
	 */
	public static final UsuarioSecurity usuarioEValido() {
		UsuarioSecurity usuario = UsuarioService.authenticated();
		
		if (usuario.getId() == null) {
			throw new AuthorizationException("Acesso negado!");
		}
		
		return usuario;
	}
	
	
	/**
	 * Método responsável por verificar se o Usuário tem permissão para efetuar alguma operação de CRUD
	 * @param id : Long
	 */
	public static final void usuarioTemPermissao(Long id) {
		UsuarioSecurity usuario = UsuarioService.authenticated();
		
		if (usuario == null || !usuario.hasRole(PerfilCliente.ADMIN) && !id.equals(usuario.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}
	}
}
