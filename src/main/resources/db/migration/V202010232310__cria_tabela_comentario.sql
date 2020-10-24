CREATE TABLE comentario (
	id bigint NOT NULL auto_increment,
	descricao text not null,
	data_envio datetime,
	ordem_servico_id bigint not null,
	
	primary key (id)
);

alter table comentario add constraint fk_comentario_ordem_servico foreign key (ordem_servico_id) references ordem_servico (id); 