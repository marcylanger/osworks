CREATE TABLE ordem_servico (
	id bigint NOT NULL auto_increment,
	descricao text not null,
	preco decimal(10,2) not null,
	status varchar(20) not null,
	data_abertura datetime not null,
	data_finalizacao datetime,
	cliente_id bigint not null,
	
	primary key (id)
);

alter table ordem_servico add constraint fk_ordem_servico_cliente foreign key (cliente_id) references cliente (id);