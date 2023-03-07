package med.voll.api.domain.dto;

import med.voll.api.domain.models.Endereco;
import med.voll.api.domain.models.Paciente;

public record DadosDetalhamentoPaciente(Long id, String nome, String cpf, String telefone, String email, Endereco endereco) {

	public DadosDetalhamentoPaciente(Paciente p) {
		this(p.getId(), p.getNome(), p.getCpf(), p.getTelefone(), p.getEmail(), p.getEndereco());
	}
}
