package trabalho.lp.produto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import trabalho.lp.produto.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
