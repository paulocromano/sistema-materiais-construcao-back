package trabalho.lp.usuario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import trabalho.lp.cliente.model.Cliente;
import trabalho.lp.cliente.repository.ClienteRepository;
import trabalho.lp.security.UsuarioSecurity;


@Service
public class UsuarioDetailsService implements UserDetailsService {
	
	@Autowired
	private ClienteRepository clienteRepository;

	
	/**
	 * Método responsável por receber um Usuário e retornar o UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = (clienteRepository.findByEmail(email)).get();
		
		if (cliente == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new UsuarioSecurity(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
	}
}
