package trabalho.lp.compra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import trabalho.lp.compra.model.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {

	List<Compra> findByCliente_Id(Long id);

}
