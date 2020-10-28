package trabalho.lp.compra.itemPedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import trabalho.lp.compra.itemPedido.model.ItemPedido;
import trabalho.lp.compra.itemPedidoPK.model.ItemPedidoPK;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoPK> {

}
