package trabalho.lp.exception.model;

import java.io.Serializable;


public class FieldMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String fieldName;
	private String message;
	
	
	public FieldMessage() {
		
	}

	/**
	 * Construtor
	 * @param fieldName : String
	 * @param message : String
	 */
	public FieldMessage(String fieldName, String message) {
		this.fieldName = fieldName;
		this.message = message;
	}


	public String getFieldName() {
		return fieldName;
	}

	public String getMessage() {
		return message;
	}
}
