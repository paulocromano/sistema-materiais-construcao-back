package trabalho.lp.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	
	/**
	 * Método responsável por gerar um Token
	 * @param usuario : UsuarioSecurity
	 * @return String - Token
	 */
	public String generateToken(UsuarioSecurity usuario) {
		String permissoes = usuario.getAuthorities().toString();
		
		return Jwts.builder()
				.setSubject(usuario.getUsername())
				.claim("id", usuario.getId())
				.claim("permissoes", permissoes)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
	
	
	public Boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		
		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	public String getUsername(String token) {
		Claims claims = getClaims(token);
		
		return (claims != null) ? claims.getSubject() : null;
	}


	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}
		catch (Exception e) {
			return null;
		}
	}
}
