package med.voll.api.domain.dto;

import jakarta.validation.constraints.NotNull;

public record DadosUpdateMedico(@NotNull Long id, String nome, String telefone, DadosCadastroEndereco endereco) {	


}
