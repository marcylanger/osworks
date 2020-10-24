package com.langer.osworks.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langer.osworks.domain.model.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {

	List<Cliente> findByNomeContaining(String partOfNome);
	
	List<Cliente> findByNome(String nome);
	
	Cliente findByEmail(String email);
}
