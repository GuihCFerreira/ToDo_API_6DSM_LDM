package todo_6dsm.todo_api_6dsm_ldm.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import todo_6dsm.todo_api_6dsm_ldm.model.enums.StatusTarefa;

import java.time.LocalDateTime;

@Entity
@Table(name = "tarefas")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarefa {

    @Id
    @Column(name = "id")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusTarefa status;

    @Column(name = "observacoes")
    private String observacoes;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime data_criacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime data_atualizacao;

}
