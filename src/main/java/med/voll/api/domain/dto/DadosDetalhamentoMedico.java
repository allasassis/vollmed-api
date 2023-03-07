package med.voll.api.domain.dto;

import med.voll.api.domain.models.Endereco;
import med.voll.api.domain.models.Especialidade;
import med.voll.api.domain.models.Medico;

public record DadosDetalhamentoMedico(Long id, String nome, String email, String crm, Especialidade especialidade, Endereco endereco) {

	public DadosDetalhamentoMedico(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade(), medico.getEndereco());
	}
}
