package trabalho.lp.compra.dto;

import java.util.List;
import java.util.stream.Collectors;

import trabalho.lp.cliente.model.Cliente;
import trabalho.lp.compra.model.Compra;
import trabalho.lp.produto.model.Produto;
import trabalho.lp.utils.Converter;

public class CompraDTO {

	private Long id;
	private String preco;
	private Integer quantidade;
	private Cliente cliente;
	private Produto produto;
	
	
	public CompraDTO(Compra compra) {
		id = compra.getId();
		preco = Converter.doubleParaStringComDuasCasasDecimais(compra.getPreco());
		quantidade = compra.getQuantidade();
		cliente = compra.getCliente();
		produto = compra.getProduto();
	}


	public Long getId() {
		return id;
	}

	public String getPreco() {
		return preco;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Produto getProduto() {
		return produto;
	}
	
	
	public static List<CompraDTO> converterParaListaCompraDTO(List<Compra> listaCompras) {
		return listaCompras.stream().map(CompraDTO::new).collect(Collectors.toList());
	}
}
