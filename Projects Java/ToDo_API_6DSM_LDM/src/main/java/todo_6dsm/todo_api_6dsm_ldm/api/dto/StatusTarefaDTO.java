package todo_6dsm.todo_api_6dsm_ldm.api.dto;

import lombok.*;
import todo_6dsm.todo_api_6dsm_ldm.model.enums.StatusTarefa;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusTarefaDTO {
    private StatusTarefa status;
}
