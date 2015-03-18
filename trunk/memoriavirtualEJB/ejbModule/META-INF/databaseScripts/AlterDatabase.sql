ALTER TABLE BEMPATRIMONIAL_BENSRELACIONADOS DROP CONSTRAINT FK_BEMPATRIMONIAL_BENSRELACIONADOS_BEMPATRIMONIAL_ID;
ALTER TABLE BEMPATRIMONIAL_BENSRELACIONADOS DROP CONSTRAINT FK_BEMPATRIMONIAL_BENSRELACIONADOS_BENSRELACIONADOS_ID;

ALTER TABLE BEMPATRIMONIAL_BENSRELACIONADOS ADD CONSTRAINT FK_BEMPATRIMONIAL_BENSRELACIONADOS_BEMPATRIMONIAL_ID FOREIGN KEY (BEMPATRIMONIAL_ID) REFERENCES BEMPATRIMONIAL (ID) ON DELETE CASCADE;
ALTER TABLE BEMPATRIMONIAL_BENSRELACIONADOS ADD CONSTRAINT FK_BEMPATRIMONIAL_BENSRELACIONADOS_BENSRELACIONADOS_ID FOREIGN KEY (BENSRELACIONADOS_ID) REFERENCES BEMPATRIMONIAL (ID) ON DELETE CASCADE;


--Revisão de Bens
ALTER TABLE bemPatrimonial add column revisao boolean;

CREATE SEQUENCE REVISAO_SEQ START WITH 1;
CREATE TABLE REVISAO (ID BIGINT NOT NULL, ANOTACAO VARCHAR(255), DATAREVISAO DATE, BEMPATRIMONIAL_ID BIGINT, PRIMARY KEY (ID));
ALTER TABLE REVISAO ADD CONSTRAINT FK_REVISAO_BEMPATRIMONIAL_ID FOREIGN KEY (BEMPATRIMONIAL_ID) REFERENCES BEMPATRIMONIAL (ID);=======
ALTER TABLE BEMPATRIMONIAL_BENSRELACIONADOS ADD CONSTRAINT FK_BEMPATRIMONIAL_BENSRELACIONADOS_BENSRELACIONADOS_ID FOREIGN KEY (BENSRELACIONADOS_ID) REFERENCES BEMPATRIMONIAL (ID) ON DELETE CASCADE;

--campos CLOB

-- Column: complemento
ALTER TABLE bempatrimonial DROP COLUMN complemento;
ALTER TABLE bempatrimonial ADD COLUMN complemento text;

-- Column: outrasresponsabilidades
ALTER TABLE bempatrimonial DROP COLUMN outrasresponsabilidades;
ALTER TABLE bempatrimonial ADD COLUMN outrasresponsabilidades text;

-- Column: caracteristicasfisicastecnicasexecutivas
ALTER TABLE bempatrimonial DROP COLUMN caracteristicasfisicastecnicasexecutivas;
ALTER TABLE bempatrimonial ADD COLUMN caracteristicasfisicastecnicasexecutivas text;

-- Column: caracteristicasambientais
ALTER TABLE bempatrimonial DROP COLUMN caracteristicasambientais;
ALTER TABLE bempatrimonial ADD COLUMN caracteristicasambientais text;

-- Column: meioantropico
ALTER TABLE bempatrimonial DROP COLUMN meioantropico;
ALTER TABLE bempatrimonial ADD COLUMN meioantropico text;

-- Column: outros
ALTER TABLE bempatrimonial DROP COLUMN outros;
ALTER TABLE bempatrimonial ADD COLUMN outros text;

-- Column: meioacesso
ALTER TABLE bempatrimonial DROP COLUMN meioacesso;
ALTER TABLE bempatrimonial ADD COLUMN meioacesso text;

-- Column: notas
ALTER TABLE bempatrimonial DROP COLUMN notas;
ALTER TABLE bempatrimonial ADD COLUMN notas text;

-- Column: notasestadoconservacao
ALTER TABLE bempatrimonial DROP COLUMN notasestadoconservacao;
ALTER TABLE bempatrimonial ADD COLUMN notasestadoconservacao text;

-- Column: notasusoaproveitamento
ALTER TABLE bempatrimonial DROP COLUMN notasusoaproveitamento;
ALTER TABLE bempatrimonial ADD COLUMN notasusoaproveitamento text;

-- Column: dadosdoctransacao
ALTER TABLE bempatrimonial DROP COLUMN dadosdoctransacao;
ALTER TABLE bempatrimonial ADD COLUMN dadosdoctransacao character varying(255);

-- Column: historico
ALTER TABLE bempatrimonial DROP COLUMN historico;
ALTER TABLE bempatrimonial ADD COLUMN historico text;

-- Column: instrumentopesquisa
ALTER TABLE bempatrimonial DROP COLUMN instrumentopesquisa;
ALTER TABLE bempatrimonial ADD COLUMN instrumentopesquisa text;

-- Column: conteudo
ALTER TABLE bempatrimonial DROP COLUMN conteudo;
ALTER TABLE bempatrimonial ADD COLUMN conteudo text;

-- Column: sintesehistorica
ALTER TABLE instituicao DROP COLUMN sintesehistorica;
ALTER TABLE instituicao ADD COLUMN sintesehistorica text;

-- Column: fontesinformacao
ALTER TABLE bempatrimonial_fontesinformacao DROP COLUMN fontesinformacao;
ALTER TABLE bempatrimonial_fontesinformacao ADD COLUMN fontesinformacao text;

-- Column: notaspesquisador --adicionado por inferencia
ALTER TABLE bempatrimonial_pesquisadores DROP COLUMN notaspesquisador;
ALTER TABLE bempatrimonial_pesquisadores ADD COLUMN notaspesquisador text;
