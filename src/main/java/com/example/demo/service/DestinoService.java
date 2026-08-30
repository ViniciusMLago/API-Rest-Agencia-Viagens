package com.example.demo.service;

import com.example.demo.model.Destino;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DestinoService {
    
    // Armazenamento em memória
    private List<Destino> destinos = new ArrayList<>();
    private Long proximoId = 1L;

    public Destino salvar(Destino destino) {
        destino.setId(proximoId++);
        destinos.add(destino);
        return destino;
    }

    public List<Destino> listarTodos() {
        return destinos;
    }

    public List<Destino> pesquisar(String termo) {
        return destinos.stream()
                .filter(d -> d.getNome().toLowerCase().contains(termo.toLowerCase()) || 
                             d.getLocalizacao().toLowerCase().contains(termo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Optional<Destino> buscarPorId(Long id) {
        return destinos.stream().filter(d -> d.getId().equals(id)).findFirst();
    }

    public Optional<Destino> atualizar(Long id, Destino destinoAtualizado) {
        return buscarPorId(id).map(destinoExistente -> {
            destinoExistente.setNome(destinoAtualizado.getNome());
            destinoExistente.setLocalizacao(destinoAtualizado.getLocalizacao());
            destinoExistente.setDescricao(destinoAtualizado.getDescricao());
            return destinoExistente;
        });
    }

    public Optional<Destino> avaliar(Long id, double nota) {
        return buscarPorId(id).map(destino -> {
            destino.adicionarAvaliacao(nota);
            return destino;
        });
    }

    public boolean excluir(Long id) {
        return destinos.removeIf(d -> d.getId().equals(id));
    }
}