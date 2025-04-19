package todo_6dsm.todo_api_6dsm_ldm.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import todo_6dsm.todo_api_6dsm_ldm.api.dto.TarefaDTO;
import todo_6dsm.todo_api_6dsm_ldm.model.entity.Tarefa;
import todo_6dsm.todo_api_6dsm_ldm.model.enums.StatusTarefa;
import todo_6dsm.todo_api_6dsm_ldm.service.TarefaService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class TarefaServiceImplTest {

    @Autowired
    private TarefaService tarefaService;

    @BeforeEach
    void limparBanco() {
        tarefaService.buscarTodasTarefas().forEach(t -> tarefaService.deletarTarefa(t.getId()));
    }

    @Test
    void buscarTodasTarefas() {
        for (int i = 0; i < 5; i++) {
            Tarefa tarefa = Tarefa.builder()
                    .nome("Estudar JUnit " + i)
                    .descricao("Estudar testes com H2 e Spring " + i)
                    .status(StatusTarefa.PENDENTE)
                    .build();
            tarefaService.criarTarefa(tarefa);
        }

        List<Tarefa> tarefas = tarefaService.buscarTodasTarefas();
        assertEquals(5, tarefas.size());
    }

    @Test
    void buscarTarefaPorId() {

        Tarefa tarefa = Tarefa.builder()
                .nome("Estudar JUnit")
                .descricao("Estudar testes com H2 e Spring")
                .status(StatusTarefa.PENDENTE)
                .build();

        tarefaService.criarTarefa(tarefa);

        Tarefa tarefaBuscada = tarefaService.buscarTarefaPorId(tarefa.getId());
        assertEquals(tarefa.getId(), tarefaBuscada.getId());
    }

    @Test
    void criarTarefa() {
        Tarefa tarefa = Tarefa.builder()
                .nome("Estudar JUnit")
                .descricao("Estudar testes com H2 e Spring")
                .status(StatusTarefa.PENDENTE)
                .build();

        tarefaService.criarTarefa(tarefa);

        Assertions.assertNotNull(tarefa.getId());
    }

    @Test
    void atualizarTarefa() {

        Tarefa tarefa = Tarefa.builder()
                .nome("Estudar JUnit")
                .descricao("Estudar testes com H2 e Spring")
                .status(StatusTarefa.PENDENTE)
                .build();

        tarefaService.criarTarefa(tarefa);

        TarefaDTO tarefaAtualizada = TarefaDTO.builder()
                .nome("Estudar JUnit - TESTE")
                .build();

        Tarefa atualizada = tarefaService.atualizarTarefa(tarefa.getId(), tarefaAtualizada);

        assertEquals("Estudar JUnit - TESTE", atualizada.getNome());
    }

    @Test
    void deletarTarefa() {

        Tarefa tarefa = Tarefa.builder()
                .nome("Estudar JUnit")
                .descricao("Estudar testes com H2 e Spring")
                .status(StatusTarefa.PENDENTE)
                .build();

        tarefaService.criarTarefa(tarefa);

        tarefaService.deletarTarefa(tarefa.getId());

        assertThrows(
                todo_6dsm.todo_api_6dsm_ldm.exception.ErroNaoExiste.class,
                () -> tarefaService.buscarTarefaPorId(tarefa.getId())
        );
    }

    @Test
    void atualizarStatusTarefa() {
        Tarefa tarefa = Tarefa.builder()
                .nome("Estudar JUnit")
                .descricao("Estudar testes com H2 e Spring")
                .status(StatusTarefa.PENDENTE)
                .build();

        tarefaService.criarTarefa(tarefa);

        Tarefa atualizada = tarefaService.atualizarStatusTarefa(tarefa.getId(), StatusTarefa.CONCLUIDA);

        assertEquals(StatusTarefa.CONCLUIDA, atualizada.getStatus());
    }

    @Test
    void buscarTarefaInexistente() {
        assertThrows(
                todo_6dsm.todo_api_6dsm_ldm.exception.ErroNaoExiste.class,
                () -> tarefaService.buscarTarefaPorId(999L)
        );
    }

    @Test
    void atualizarTarefaInexistente() {
        TarefaDTO tarefaDTO = TarefaDTO.builder()
                .nome("Estudar JUnit - TESTE")
                .build();

        assertThrows(
                todo_6dsm.todo_api_6dsm_ldm.exception.ErroNaoExiste.class,
                () -> tarefaService.atualizarTarefa(999L, tarefaDTO)
        );
    }

}