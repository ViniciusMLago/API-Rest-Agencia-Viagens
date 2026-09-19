package agencia_viagens.service;

import agencia_viagens.entity.Destino;
import agencia_viagens.repository.DestinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinoService {
    
    @Autowired
    private DestinoRepository destinoRepository;

    public Destino salvar(Destino destino) {
        return destinoRepository.save(destino);
    }

    public List<Destino> listarTodos() {
        return destinoRepository.findAll();
    }

    public List<Destino> pesquisar(String termo) {
        return destinoRepository.findByNomeContainingIgnoreCaseOrLocalizacaoContainingIgnoreCase(termo, termo);
    }

    public Optional<Destino> buscarPorId(Long id) {
        return destinoRepository.findById(id);
    }

    public Optional<Destino> atualizar(Long id, Destino destinoAtualizado) {
        return destinoRepository.findById(id).map(destinoExistente -> {
            destinoExistente.setNome(destinoAtualizado.getNome());
            destinoExistente.setLocalizacao(destinoAtualizado.getLocalizacao());
            destinoExistente.setDescricao(destinoAtualizado.getDescricao());
            return destinoRepository.save(destinoExistente);
        });
    }

    public Optional<Destino> avaliar(Long id, double nota) {
        return destinoRepository.findById(id).map(destino -> {
            destino.adicionarAvaliacao(nota);
            return destinoRepository.save(destino);
        });
    }

    public boolean excluir(Long id) {
        if (destinoRepository.existsById(id)) {
            destinoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}