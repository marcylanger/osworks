package com.langer.osworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.langer.osworks.domain.exception.NegocioException;
import com.langer.osworks.domain.model.Cliente;
import com.langer.osworks.domain.repository.IClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private IClienteRepository clienteRepository;
	
	@Autowired
	private MessageSource messageSource;

	public Cliente salvar(Cliente cliente) {
		Cliente clienteExistente = this.clienteRepository.findByEmail(cliente.getEmail());
		
		if(clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("EmailExist");
		}
		
		return this.clienteRepository.save(cliente);
	}
	
	public void excluir(long clienteId) {
		this.clienteRepository.deleteById(clienteId);
	}
}
