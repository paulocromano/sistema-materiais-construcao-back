package trabalho.lp.compra.form;

import javax.validation.constraints.NotNull;

import trabalho.lp.cliente.model.Cliente;
import trabalho.lp.compra.model.Compra;
import trabalho.lp.produto.model.Produto;

public class CompraFORM {

	
	@NotNull(message = "Quantidade não informada!")
	private Integer quantidade;


	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	
	public Compra converterParaCompra(Cliente cliente, Produto produto) {
		return new Compra(produto.getPreco(), quantidade, cliente, produto);
	}
}
