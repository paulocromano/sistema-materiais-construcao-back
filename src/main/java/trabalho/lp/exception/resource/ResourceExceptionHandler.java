package trabalho.lp.exception.resource;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import trabalho.lp.exception.model.StandardError;
import trabalho.lp.exception.model.ValidationError;
import trabalho.lp.exception.service.AuthorizationException;


@ControllerAdvice
public class ResourceExceptionHandler {

	
	/**
	 * Método responsável por tratar o erro de atributos que recebem um Argumento Inválido
	 * @param error : MethodArgumentNotValidException
	 * @param request : HttpServletRequest
	 * @return ResponseEntity - StandardError (Resposta com o erro personalizado)
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException error, HttpServletRequest request) {
		
		ValidationError validationError = new ValidationError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", 
				error.getMessage(), request.getRequestURI());
		
		for (FieldError fieldError : error.getBindingResult().getFieldErrors()) {
			validationError.addError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validationError);
	}
	
	
	/**
	 * Método responsável por tratar o erro ao tentar acessar uma URL inexistente
	 * @param error : HttpRequestMethodNotSupportedException
	 * @param request : HttpServletRequest
	 * @return ResponseEntity - StandardError (Resposta com o erro personalizado)
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<StandardError> httpRequestMethodNotSupported(HttpRequestMethodNotSupportedException error,  HttpServletRequest request) {
		return erroPersonalizado(error, HttpStatus.NOT_FOUND, "URL requisitada não encontrada!", request);
	}
	
	
	/**
	 * Método responsável por tratar o erro de autorização e acesso negado
	 * @param error : Exception
	 * @param request : HttpServletRequest
	 * @return ResponseEntity - StandardError (Resposta com o erro personalizado)
	 */
	@ExceptionHandler({ AuthorizationException.class, AccessDeniedException.class })
	public ResponseEntity<StandardError> authorization(Exception error,  HttpServletRequest request) {

		if (error instanceof AuthorizationException) {
			return erroPersonalizado(error, HttpStatus.FORBIDDEN, error.getMessage(), request);
		}
		
		return erroPersonalizado(error, HttpStatus.FORBIDDEN, "Acesso negado!", request);
	}
	
	
	/**
	 * Método responsável por tratar um erro
	 * @param error : Exception
	 * @param request : HttpServletRequest
	 * @param httpStatus : HttpStatus
	 * @param messageError : String
	 * @return ResponseEntity - StandardError (Reposta da requisição com o erro tratado)
	 */
	private ResponseEntity<StandardError> erroPersonalizado(Exception error, HttpStatus httpStatus, String messageError, HttpServletRequest request) {
		
		return ResponseEntity.status(httpStatus).body(new StandardError(System.currentTimeMillis(), httpStatus.value(), 
				messageError, error.getMessage(), request.getRequestURI()));
	}
}
