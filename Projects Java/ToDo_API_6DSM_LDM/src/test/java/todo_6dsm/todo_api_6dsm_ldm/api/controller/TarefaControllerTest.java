package todo_6dsm.todo_api_6dsm_ldm.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import todo_6dsm.todo_api_6dsm_ldm.api.dto.StatusTarefaDTO;
import todo_6dsm.todo_api_6dsm_ldm.api.dto.TarefaDTO;
import todo_6dsm.todo_api_6dsm_ldm.model.entity.Tarefa;
import todo_6dsm.todo_api_6dsm_ldm.model.enums.StatusTarefa;
import todo_6dsm.todo_api_6dsm_ldm.service.TarefaService;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class TarefaControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TarefaService tarefaService;

    @BeforeEach
    void limparBanco() {
        tarefaService.buscarTodasTarefas().forEach(t -> tarefaService.deletarTarefa(t.getId()));
    }

    @Test
    void buscarTodasTarefas() {
        for (int i = 0; i < 5; i++) {
            criarTarefaTeste();
        }

        ResponseEntity<Tarefa[]> response = restTemplate.getForEntity("/api/tarefas", Tarefa[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(5);
    }

    @Test
    void buscarTarefaPorId() {
        Tarefa tarefa = criarTarefaTeste();
        ResponseEntity<Tarefa> response = restTemplate.getForEntity("/api/tarefas/" + tarefa.getId(), Tarefa.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deletarTarefaPorId() {
        Tarefa tarefa = criarTarefaTeste();
        ResponseEntity<Void> response = restTemplate.exchange("/api/tarefas/" + tarefa.getId(), HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void atualizarStatusTarefa() {

        Tarefa tarefa = criarTarefaTeste();
        StatusTarefaDTO statusDTO = StatusTarefaDTO.builder()
                .status(StatusTarefa.CONCLUIDA)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<StatusTarefaDTO> request = new HttpEntity<>(statusDTO, headers);

        ResponseEntity<Tarefa> response = restTemplate.exchange("/api/tarefas/" + tarefa.getId(), HttpMethod.PATCH, request, Tarefa.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void criarTarefa() {

        TarefaDTO dto = TarefaDTO.builder()
                .nome("Estudar JUnit ")
                .descricao("Estudar testes com H2 e Spring ")
                .status(StatusTarefa.PENDENTE)
                .build();

        ResponseEntity<Tarefa> response = restTemplate.postForEntity("/api/tarefas", dto, Tarefa.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    @Test
    void atualizarTarefa() {

        Tarefa tarefa = criarTarefaTeste();

        TarefaDTO dto = TarefaDTO.builder()
                .nome("Estudar JUnit - TESTE")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TarefaDTO> request = new HttpEntity<>(dto, headers);

        ResponseEntity<Tarefa> response = restTemplate.exchange("/api/tarefas/" + tarefa.getId(), HttpMethod.PATCH, request, Tarefa.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void buscarTarefaInexistente() {
        ResponseEntity<String> response = restTemplate.exchange("/api/tarefas/999", HttpMethod.GET, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void atualizarTarefaInexistente() {
        TarefaDTO dto = TarefaDTO.builder()
                .nome("Estudar JUnit - Inexistente")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TarefaDTO> request = new HttpEntity<>(dto, headers);

        ResponseEntity<String> response = restTemplate.exchange("/api/tarefas/999", HttpMethod.PATCH, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void apagarTarefaInexistente() {
        ResponseEntity<String> response = restTemplate.exchange("/api/tarefas/999", HttpMethod.DELETE, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private Tarefa criarTarefaTeste () {

        Tarefa tarefa = Tarefa.builder()
                .nome("Estudar JUnit")
                .descricao("Estudar testes com H2 e Spring")
                .status(StatusTarefa.PENDENTE)
                .build();

        return tarefaService.criarTarefa(tarefa);
    }
}