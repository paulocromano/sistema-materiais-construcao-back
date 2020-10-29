package trabalho.lp.produto.form;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

import trabalho.lp.produto.model.Produto;

public class ProdutoFORM {

	@NotNull(message = "Campo Produto não informado!")
	@NotEmpty(message = "O campo Produto não pode estar vazio!")
	@Size(min = 3, max = 40, message = "O campo nome deve ter entre {min} a {max} caracteres!")
	private String descricao;
	
	@NotNull(message = "Preço não informado!")
	@NumberFormat(pattern = "#.##")
	private Double preco;
	
	@NotNull(message = "Estoque não informado!")
	@Digits(integer = 6, fraction = 0, message = "Tamanho do Estoque excedeu o limite! ({integer} digitos)")
	private Integer estoque;

	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

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
	 * Método responsável por converter ProdutoFORM para Produto
	 * @return Produto - Produto convertido
	 */
	public Produto converterParaProduto() {
		return new Produto(descricao, preco, estoque);
	}
}
