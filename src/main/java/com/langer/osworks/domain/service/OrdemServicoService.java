package com.langer.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langer.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.langer.osworks.domain.exception.NegocioException;
import com.langer.osworks.domain.model.Cliente;
import com.langer.osworks.domain.model.Comentario;
import com.langer.osworks.domain.model.OrdemServico;
import com.langer.osworks.domain.model.StatusOrdemServico;
import com.langer.osworks.domain.repository.IClienteRepository;
import com.langer.osworks.domain.repository.IComentarioRepository;
import com.langer.osworks.domain.repository.IOrdemServicoRepository;

@Service
public class OrdemServicoService {

	@Autowired
	private IOrdemServicoRepository ordemServicoRepository;
	
	@Autowired 
	private IClienteRepository clienteRepository;
	
	@Autowired 
	private IComentarioRepository comentarioRepository;
	
	public OrdemServico criar(OrdemServico os) {
		
		Cliente cliente = this.clienteRepository.findById(os.getCliente().getId()).orElseThrow(() -> new NegocioException("ClienteNotFound"));
		
		os.setCliente(cliente);
		os.setStatus(StatusOrdemServico.ABERTA);
		os.setDataAbertura(OffsetDateTime.now());
		
		return this.ordemServicoRepository.save(os);
	}
	
	public Comentario adicionarComentario(long ordemServicoId, String descricao) {
		OrdemServico ordemServico = this.buscar(ordemServicoId);

		Comentario comentario = new Comentario();
		comentario.setDescricao(descricao);
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setOrdemServico(ordemServico);
		
		return this.comentarioRepository.save(comentario);
	}
	
	public OrdemServico finalizar(long ordemServicoId) {
		OrdemServico ordemServico = this.buscar(ordemServicoId);
		
		ordemServico.finalizar();
		
		return this.ordemServicoRepository.save(ordemServico);
		
	}
	
	private OrdemServico buscar(long ordemServicoId) {
		return this.ordemServicoRepository.findById(ordemServicoId).orElseThrow(() -> new EntidadeNaoEncontradaException("OsNotFound"));
	}
}
