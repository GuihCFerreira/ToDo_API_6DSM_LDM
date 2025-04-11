package todo_6dsm.todo_api_6dsm_ldm.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import todo_6dsm.todo_api_6dsm_ldm.model.entity.Tarefa;

import java.util.Optional;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    Optional<Tarefa> findById(Long id);

}
