package todo_6dsm.todo_api_6dsm_ldm.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todo_6dsm.todo_api_6dsm_ldm.api.dto.TarefaDTO;
import todo_6dsm.todo_api_6dsm_ldm.exception.ErroNaoExiste;
import todo_6dsm.todo_api_6dsm_ldm.model.entity.Tarefa;
import todo_6dsm.todo_api_6dsm_ldm.model.enums.StatusTarefa;
import todo_6dsm.todo_api_6dsm_ldm.model.repository.TarefaRepository;
import todo_6dsm.todo_api_6dsm_ldm.service.TarefaService;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaServiceImpl implements TarefaService {

    private final TarefaRepository tarefaRepository;
    Optional<Tarefa> tarefa = Optional.empty();

    @Autowired
    public TarefaServiceImpl(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    @Override
    @Transactional
    public List<Tarefa> buscarTodasTarefas() {
        return tarefaRepository.findAll();
    }

    @Override
    @Transactional
    public Tarefa buscarTarefaPorId(Long id) {
        this.tarefa = tarefaRepository.findById(id);
        if (this.tarefa.isEmpty()) throw new ErroNaoExiste("Tarefa não encontrada");
        return this.tarefa.get();
    }

    @Override
    @Transactional
    public Tarefa criarTarefa(Tarefa tarefa) {
        return this.tarefaRepository.save(tarefa);
    }

    @Override
    @Transactional
    public Tarefa atualizarTarefa(Long id, TarefaDTO tarefaDTO) {
        this.tarefa = tarefaRepository.findById(id);
        if (this.tarefa.isEmpty()) throw new ErroNaoExiste("Tarefa não encontrada");

        Tarefa task = this.tarefa.get();

        if (tarefaDTO.getNome() != null)  task.setNome(tarefaDTO.getNome());
        if (tarefaDTO.getDescricao() != null)  task.setDescricao(tarefaDTO.getDescricao());
        if (tarefaDTO.getObservacoes() != null)  task.setObservacoes(tarefaDTO.getObservacoes());
        if (tarefaDTO.getStatus() != null)  task.setStatus(tarefaDTO.getStatus());

        return this.tarefaRepository.save(task);
    }

    @Override
    @Transactional
    public void deletarTarefa(Long id) {
        this.tarefa = tarefaRepository.findById(id);
        if (this.tarefa.isEmpty()) throw new ErroNaoExiste("Tarefa não encontrada");
        this.tarefaRepository.delete(this.tarefa.get());
    }

    @Override
    @Transactional
    public Tarefa atualizarStatusTarefa(Long id, StatusTarefa status) {
        this.tarefa = tarefaRepository.findById(id);
        if (this.tarefa.isEmpty()) throw new ErroNaoExiste("Tarefa não encontrada");
        Tarefa tarefaAtualizada = this.tarefa.get();
        tarefaAtualizada.setStatus(status);
        return this.tarefaRepository.save(tarefaAtualizada);
    }
}
