package trabalho.lp.cliente.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import trabalho.lp.cliente.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByEmail(String email);
}
