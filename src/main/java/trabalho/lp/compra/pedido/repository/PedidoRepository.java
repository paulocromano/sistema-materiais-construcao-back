package trabalho.lp.compra.pedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import trabalho.lp.compra.pedido.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
