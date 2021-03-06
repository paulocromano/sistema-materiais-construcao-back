package trabalho.lp.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import trabalho.lp.usuario.dto.CredenciasDTO;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	
	private JWTUtil jwtUtil;


	/**
	 * Construtor
	 * @param authenticationManager : AuthenticationManager
	 * @param jwtUtil : JWTUtil
	 */
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	
	
	/**
	 * Método responsável por tentar autenticar a requisição
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return Authentication
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try {
			CredenciasDTO credenciais = new ObjectMapper()
					.readValue(request.getInputStream(), CredenciasDTO.class);
			
			UsernamePasswordAuthenticationToken autenticacaoToken = new UsernamePasswordAuthenticationToken(
					credenciais.getEmail(), credenciais.getSenha(), new ArrayList<>());
			
			Authentication autenticacao = authenticationManager.authenticate(autenticacaoToken);
			
			return autenticacao;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * Método responsável por executar uma ação caso a autenticação resulte em sucesso
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @param chain : FilterChain
	 * @param authResult : Authentication
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
				Authentication authResult) throws IOException, ServletException {
			
		UsuarioSecurity usuario = ((UsuarioSecurity) authResult.getPrincipal());
		String token = jwtUtil.generateToken(usuario);
		
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
	}
}
