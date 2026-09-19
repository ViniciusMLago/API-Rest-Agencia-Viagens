package agencia_viagens.controller;

import agencia_viagens.entity.Destino;
import agencia_viagens.service.DestinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/destinos")
public class DestinoController {

    @Autowired
    private DestinoService destinoService;

    // Cadastrar destino
    @PostMapping
    public ResponseEntity<Destino> cadastrar(@RequestBody Destino destino) {
        Destino novoDestino = destinoService.salvar(destino);
        return new ResponseEntity<>(novoDestino, HttpStatus.CREATED);
    }

    // Listar todos e Pesquisar por nome ou localização
    @GetMapping
    public ResponseEntity<List<Destino>> listarOuPesquisar(@RequestParam(required = false) String termo) {
        if (termo != null && !termo.isEmpty()) {
            return ResponseEntity.ok(destinoService.pesquisar(termo));
        }
        return ResponseEntity.ok(destinoService.listarTodos());
    }

    // Visualizar detalhes
    @GetMapping("/{id}")
    public ResponseEntity<Destino> buscarPorId(@PathVariable Long id) {
        return destinoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar destino
    @PutMapping("/{id}")
    public ResponseEntity<Destino> atualizar(@PathVariable Long id, @RequestBody Destino destino) {
        return destinoService.atualizar(id, destino)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Registrar avaliação (Usa PATCH pois atualiza apenas uma parte do recurso)
    @PatchMapping("/{id}/avaliar")
    public ResponseEntity<Destino> avaliar(@PathVariable Long id, @RequestBody Map<String, Double> payload) {
        if (!payload.containsKey("nota")) {
            return ResponseEntity.badRequest().build();
        }
        return destinoService.avaliar(id, payload.get("nota"))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Excluir destino
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (destinoService.excluir(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}