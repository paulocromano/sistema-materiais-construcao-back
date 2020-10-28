package trabalho.lp.cliente.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "perfil")
public class PerfilCliente {

	@Id
	@Column(name = "cliente_id")
	private Long clienteID;
	
	private Integer perfis;

	
	public Long getClienteID() {
		return clienteID;
	}

	public Integer getPerfis() {
		return perfis;
	}
}
