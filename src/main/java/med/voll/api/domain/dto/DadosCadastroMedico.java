package med.voll.api.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.models.Especialidade;

public record DadosCadastroMedico(@NotBlank String nome,@NotBlank @Email String email,@NotBlank String telefone, @NotBlank @Pattern(regexp = "\\d{4,6}") String crm,@NotNull Especialidade especialidade,@NotNull @Valid DadosCadastroEndereco endereco) {

}
