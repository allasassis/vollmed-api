package med.voll.api.domain.dto;

import med.voll.api.domain.models.Paciente;

public record DadosListagemPaciente(String nome, String email, String cpf) {

	public DadosListagemPaciente(Paciente p) {
		this (p.getNome(), p.getEmail(), p.getCpf());
	}
}
