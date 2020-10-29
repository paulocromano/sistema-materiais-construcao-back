package trabalho.lp.produto.form;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;

import trabalho.lp.produto.model.Produto;

public class AtualizarProdutoFORM {

	@NotNull(message = "Preço não informado!")
	@NumberFormat(pattern = "#.##")
	private Double preco;
	
	@NotNull(message = "Estoque não informado!")
	@Digits(integer = 6, fraction = 0, message = "Tamanho do Estoque excedeu o limite! ({integer} digitos)")
	private Integer estoque;

	
	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}
	
	
	/**
	 * Método responsável por atualizar o Produto com os novos dados
	 * @param produto : Produto
	 */
	public void atualizarProduto(Produto produto) {
		produto.setPreco(preco);
		produto.setEstoque(estoque);
	}
}
