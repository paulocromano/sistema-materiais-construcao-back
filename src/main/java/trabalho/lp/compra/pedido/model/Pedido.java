package trabalho.lp.compra.pedido.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import trabalho.lp.cliente.model.Cliente;
import trabalho.lp.compra.itemPedido.model.ItemPedido;

@Entity
@Table(name = "pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "data_hora_pedido")
	private String dataHoraPedidoFinalizado;
	
	@Column(name = "preco_total")
	private Double total;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@JsonIgnore
	@OneToMany(mappedBy="id.pedido", cascade = CascadeType.MERGE)
	private Set<ItemPedido> itens = new HashSet<>();

	
	public Pedido() {

	}
	
	public Pedido(Cliente cliente) {
		this.cliente = cliente;
		total = 0.0D;
	}


	public Long getId() {
		return id;
	}

	public String getDataHoraPedidoFinalizado() {
		return dataHoraPedidoFinalizado;
	}
	
	public Double getTotal() {
		return total;
	}


	public Cliente getCliente() {
		return cliente;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}	
	
	public void setTotal(Double total) {
		this.total = total;
	}
	
	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}
	
	
	/**
	 * Método responsável por adicionar um Produto ao Pedido e atualizar o valor total
	 * @param itemPedido : ItemPedido
	 */
	public void adicionarItemPedido(ItemPedido itemPedido) {
		itens.add(itemPedido);
		total += itemPedido.getPreco() * itemPedido.getQuantidade();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
