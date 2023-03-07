package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.DadosCadastroMedico;
import med.voll.api.domain.dto.DadosDetalhamentoMedico;
import med.voll.api.domain.dto.DadosListagemMedico;
import med.voll.api.domain.dto.DadosUpdateMedico;
import med.voll.api.domain.models.Medico;
import med.voll.api.domain.repositories.MedicoRepository;

@RestController
@RequestMapping("medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

	@Autowired
	private MedicoRepository repository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoMedico> cadastrar(@RequestBody @Valid DadosCadastroMedico dadosMedico, UriComponentsBuilder uriBuilder) {
		Medico medico = new Medico(dadosMedico);
		repository.save(medico);
	
		var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
	}
	
	@GetMapping
	public ResponseEntity<Page<DadosListagemMedico>> listaMedicos(@PageableDefault(size = 10, sort = "id") Pageable paginacao) {
		var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
		return ResponseEntity.ok(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoMedico> detalharMedico(@PathVariable Long id) {
		Medico medico = repository.getReferenceById(id);

		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoMedico> updateMedico(@RequestBody @Valid DadosUpdateMedico dadosMedico) {
		Medico medico = repository.getReferenceById(dadosMedico.id());
		medico.atualizarInformacoes(dadosMedico);
	
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<?> reativarMedico(@PathVariable Long id) {
		Medico medico = repository.getReferenceById(id);
		medico.reativar();
		
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deleteMedico(@PathVariable Long id) {
		Medico medico = repository.getReferenceById(id);
		medico.desativar();
		return ResponseEntity.noContent().build();
	}
} 			
