package med.voll.api.domain.models;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.dto.DadosCadastroMedico;
import med.voll.api.domain.dto.DadosUpdateMedico;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "medicos")
@Entity(name = "Medico")
public class Medico {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private String crm;
	
	@Enumerated(EnumType.STRING)
	private Especialidade especialidade;
	
	@Embedded
	private Endereco endereco;
	
	private Boolean ativo;
	
	public Medico(DadosCadastroMedico dadosMedico) {
		this.ativo = true;
		this.nome = dadosMedico.nome();
		this.email = dadosMedico.email();
		this.telefone = dadosMedico.telefone();
		this.crm = dadosMedico.crm();
		this.especialidade = dadosMedico.especialidade();
		this.endereco = new Endereco(dadosMedico.endereco());
	}
	
	public void atualizarInformacoes(DadosUpdateMedico dados) {
		if (dados.nome() != null) {
			this.nome = dados.nome();
		}
		
		if (dados.telefone() != null) {
			this.telefone = dados.telefone();
		}
		
		if (dados.endereco() != null) {
			this.endereco.atualizarInformacoes(dados.endereco());
		}
		
	}

	public void desativar() {
		this.ativo = false;
	}

	public void reativar() {
		this.ativo = true;
	}
}
