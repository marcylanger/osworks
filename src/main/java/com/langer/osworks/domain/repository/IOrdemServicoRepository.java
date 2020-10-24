package com.langer.osworks.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langer.osworks.domain.model.OrdemServico;

@Repository
public interface IOrdemServicoRepository extends JpaRepository<OrdemServico, Long> {

}
