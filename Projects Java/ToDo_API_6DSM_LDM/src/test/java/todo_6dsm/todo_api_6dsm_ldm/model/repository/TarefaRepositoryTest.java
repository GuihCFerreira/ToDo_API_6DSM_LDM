package todo_6dsm.todo_api_6dsm_ldm.model.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import todo_6dsm.todo_api_6dsm_ldm.model.entity.Tarefa;
import todo_6dsm.todo_api_6dsm_ldm.model.enums.StatusTarefa;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TarefaRepositoryTest {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void deveBuscarTarefaPorId() {
        // cenário
        Tarefa tarefa = Tarefa.builder()
                .nome("Estudar JUnit")
                .descricao("Estudar testes com H2 e Spring")
                .status(StatusTarefa.PENDENTE)
                .build();

        entityManager.persist(tarefa);

        // ação
        Optional<Tarefa> result = tarefaRepository.findById(tarefa.getId());

        // verificação
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(tarefa.getId());
    }
}