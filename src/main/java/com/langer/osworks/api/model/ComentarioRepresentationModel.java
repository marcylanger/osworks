package com.langer.osworks.api.model;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotBlank;

public class ComentarioRepresentationModel {

	
	private Long id;
	
	@NotBlank
	private String descricao;
	
	private OffsetDateTime dataEnvio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public OffsetDateTime getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(OffsetDateTime dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
	
	
	
	
}
