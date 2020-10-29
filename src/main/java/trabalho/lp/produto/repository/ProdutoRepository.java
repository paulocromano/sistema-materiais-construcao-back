package trabalho.lp.produto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import trabalho.lp.produto.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	Optional<Produto> findById(Integer id);
}
