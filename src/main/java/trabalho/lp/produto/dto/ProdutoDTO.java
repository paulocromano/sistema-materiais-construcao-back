package trabalho.lp.produto.dto;

import java.util.List;
import java.util.stream.Collectors;

import trabalho.lp.produto.model.Produto;
import trabalho.lp.utils.Converter;

public class ProdutoDTO {

	private Long id;
	private String descricao;
	private String preco;
	private Integer estoque;
	
	
	public ProdutoDTO(Produto produto) {
		id = produto.getId();
		descricao = produto.getDescricao();
		preco = Converter.doubleParaStringComDuasCasasDecimais(produto.getPreco());
		estoque = produto.getEstoque();
	}


	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getPreco() {
		return preco;
	}

	public Integer getEstoque() {
		return estoque;
	}
	
	
	/**
	 * Método responsável por converter um List de Produto para List de ProdutoDTO
	 * @param listaProdutos : List de Produto
	 * @return List de ProdutoDTO
	 */
	public static List<ProdutoDTO> converterParaListaProdutoDTO(List<Produto> listaProdutos) {
		return listaProdutos.stream().map(ProdutoDTO::new).collect(Collectors.toList());
	}
}
