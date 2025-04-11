package todo_6dsm.todo_api_6dsm_ldm.service;

import todo_6dsm.todo_api_6dsm_ldm.api.dto.TarefaDTO;
import todo_6dsm.todo_api_6dsm_ldm.model.entity.Tarefa;
import todo_6dsm.todo_api_6dsm_ldm.model.enums.StatusTarefa;

import java.util.List;

public interface TarefaService {

    List<Tarefa> buscarTodasTarefas();

    Tarefa buscarTarefaPorId(Long id);

    Tarefa criarTarefa(Tarefa tarefa);

    Tarefa atualizarTarefa(Long id, TarefaDTO tarefaDTO);

    void deletarTarefa(Long id);

    Tarefa atualizarStatusTarefa(Long id, StatusTarefa status);

}
