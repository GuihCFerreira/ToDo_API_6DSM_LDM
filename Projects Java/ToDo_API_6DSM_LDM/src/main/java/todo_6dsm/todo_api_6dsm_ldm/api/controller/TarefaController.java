package todo_6dsm.todo_api_6dsm_ldm.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todo_6dsm.todo_api_6dsm_ldm.api.dto.StatusTarefaDTO;
import todo_6dsm.todo_api_6dsm_ldm.api.dto.TarefaDTO;
import todo_6dsm.todo_api_6dsm_ldm.model.entity.Tarefa;
import todo_6dsm.todo_api_6dsm_ldm.model.enums.StatusTarefa;
import todo_6dsm.todo_api_6dsm_ldm.service.TarefaService;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    @Autowired
    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    public List<Tarefa> buscarTodasTarefas() {
        return tarefaService.buscarTodasTarefas();
    }

    @GetMapping("/{id}")
    public Tarefa buscarTarefaPorId(@PathVariable Long id) {
        return tarefaService.buscarTarefaPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletarTarefaPorId(@PathVariable Long id) {
        tarefaService.deletarTarefa(id);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Tarefa> atualizarStatusTarefa(@PathVariable Long id, @RequestBody StatusTarefaDTO body) {
        return ResponseEntity.ok(tarefaService.atualizarStatusTarefa(id, body.getStatus()));
    }

    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody TarefaDTO body) {
        Tarefa tarefa = Tarefa.builder()
                .nome(body.getNome())
                .descricao(body.getDescricao())
                .observacoes(body.getObservacoes())
                .status(body.getStatus())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaService.criarTarefa(tarefa));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody TarefaDTO body) {
        return ResponseEntity.ok(tarefaService.atualizarTarefa(id, body));
    }

}
