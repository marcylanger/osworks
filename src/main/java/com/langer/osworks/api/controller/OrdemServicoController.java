package com.langer.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.langer.osworks.api.model.ComentarioRepresentationModel;
import com.langer.osworks.api.model.OrdemServicoRepresentationModel;
import com.langer.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.langer.osworks.domain.model.Comentario;
import com.langer.osworks.domain.model.OrdemServico;
import com.langer.osworks.domain.repository.IOrdemServicoRepository;
import com.langer.osworks.domain.service.OrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {
	
	@Autowired
	private OrdemServicoService ordemServicoService;
	
	@Autowired
	private IOrdemServicoRepository ordemServicoRepository;

	@Autowired
	private ModelMapper mapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoRepresentationModel criar(@Valid @RequestBody OrdemServico ordemServico) {
		return this.toModel(this.ordemServicoService.criar(ordemServico));
	}

	@GetMapping
	public List<OrdemServicoRepresentationModel> listar(){
		return this.toCollectionModel(this.ordemServicoRepository.findAll());
	}
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoRepresentationModel> buscar(@PathVariable Long ordemServicoId){
		
		Optional<OrdemServico> ordemServico = this.ordemServicoRepository.findById(ordemServicoId);
		
		if(ordemServico.isPresent()) {
			OrdemServicoRepresentationModel osModel = this.toModel(ordemServico.get());
			return ResponseEntity.ok(osModel);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{ordemServicoId}/finalizacao")
	private OrdemServicoRepresentationModel finalizar(@PathVariable Long ordemServicoId) {
		return this.toModel(this.ordemServicoService.finalizar(ordemServicoId));
	}
	
	private OrdemServicoRepresentationModel toModel(OrdemServico ordemServico) {
		return mapper.map(ordemServico, OrdemServicoRepresentationModel.class);
	}
	
	private List<OrdemServicoRepresentationModel> toCollectionModel(List<OrdemServico> ordemServicoList) {
		return ordemServicoList.stream().map(ordemServico -> toModel(ordemServico)).collect(Collectors.toList());
	}
	
	/*
	 * =============== COMENTÁRIOS ==================
	 */
	
	@PostMapping("/{ordemServicoId}/comentarios")
	private ComentarioRepresentationModel adicionarComentario(@PathVariable Long ordemServicoId, @Valid @RequestBody ComentarioRepresentationModel comentario) {
		return this.toComentarioModel(this.ordemServicoService.adicionarComentario(ordemServicoId, comentario.getDescricao()));
	}
	
	@GetMapping("/{ordemServicoId}/comentarios")
	private List<ComentarioRepresentationModel> listarComentarios(@PathVariable Long ordemServicoId){
		OrdemServico ordemServico = this.ordemServicoRepository.findById(ordemServicoId).orElseThrow( () -> new EntidadeNaoEncontradaException("OsNotFound"));
		
		return this.toCollectionComentarioModel(ordemServico.getComentarios());
	}
	
	private ComentarioRepresentationModel toComentarioModel(Comentario comentario) {
		return mapper.map(comentario, ComentarioRepresentationModel.class);
	}
	
	private List<ComentarioRepresentationModel> toCollectionComentarioModel(List<Comentario> comentarioList) {
		return comentarioList.stream().map(comentario -> toComentarioModel(comentario)).collect(Collectors.toList());
	}
}
