package agencia_viagens.repository;

import agencia_viagens.entity.Destino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DestinoRepository extends JpaRepository<Destino, Long> {

    // Método de busca por nome ou localização, ignorando maiúsculas e minúsculas
    List<Destino> findByNomeContainingIgnoreCaseOrLocalizacaoContainingIgnoreCase(String nome, String localizacao);

}
