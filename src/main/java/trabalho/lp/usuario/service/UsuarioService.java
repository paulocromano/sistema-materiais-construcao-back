package trabalho.lp.usuario.service;

import org.springframework.security.core.context.SecurityContextHolder;

import trabalho.lp.security.UsuarioSecurity;


public class UsuarioService {

	/**
	 * Método responsável por obter o Usuário logado
	 * @return UsuarioSecurity
	 */
	public static UsuarioSecurity authenticated() {
		try {
			return (UsuarioSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
		}
		catch (Exception e) {
			return null;
		}
	}
}
