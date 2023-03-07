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
import med.voll.api.domain.dto.DadosCadastroPaciente;
import med.voll.api.domain.dto.DadosDetalhamentoPaciente;
import med.voll.api.domain.dto.DadosListagemPaciente;
import med.voll.api.domain.dto.DadosUpdatePaciente;
import med.voll.api.domain.models.Paciente;
import med.voll.api.domain.repositories.PacienteRepository;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

	@Autowired
	private PacienteRepository repository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoPaciente> cadastrar(@RequestBody DadosCadastroPaciente dados, UriComponentsBuilder uriB) {
		Paciente p = new Paciente(dados);
		repository.save(p);
		
		var uri = uriB.path("/pacientes/{id}").buildAndExpand(p.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(p));
	}
	
	@GetMapping
	public ResponseEntity<Page<DadosListagemPaciente>> listaPacientes(@PageableDefault(size = 10, sort = "nome") Pageable paginacao) {
		var page = repository.findAll(paginacao).map(DadosListagemPaciente::new);
		return ResponseEntity.ok(page);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<DadosDetalhamentoPaciente> atualizarPaciente(@RequestBody @Valid DadosUpdatePaciente dados, @PathVariable Long id) {
		Paciente p = repository.getReferenceById(id);
		p.atualizarInformaçoes(dados);
		
		return ResponseEntity.ok(new DadosDetalhamentoPaciente(p));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> desativarPaciente(@PathVariable Long id) {
		Paciente p = repository.getReferenceById(id);
		p.desativar();
		
		return ResponseEntity.noContent().build();
	}
}
