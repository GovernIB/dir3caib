create table DIR_SINCRONIZACION (
  CODIGO int8 not null,
  ESTADO int8,
  FECHAFIN timestamp,
  FECHAIMPORTACION timestamp,
  FECHAINICIO timestamp,
  TIPO varchar(255),
  primary key (CODIGO)
);

TRUNCATE TABLE dir_unidad  CASCADE;
TRUNCATE TABLE dir_catmotivoextincion  CASCADE;
TRUNCATE TABLE dir_descarga  CASCADE;
TRUNCATE TABLE dir_historicouo  CASCADE;
TRUNCATE TABLE dir_contactoofi  CASCADE;
TRUNCATE TABLE dir_cattipocontacto  CASCADE;
TRUNCATE TABLE dir_contactouo  CASCADE;
TRUNCATE TABLE dir_historicoofi  CASCADE;
TRUNCATE TABLE dir_catjerarquiaoficina  CASCADE;
TRUNCATE TABLE dir_relacionorganizativaofi  CASCADE;
TRUNCATE TABLE dir_catprovincia  CASCADE;
TRUNCATE TABLE dir_relacionsirofi  CASCADE;
TRUNCATE TABLE dir_catservicio  CASCADE;
TRUNCATE TABLE dir_servicioofi  CASCADE;
TRUNCATE TABLE dir_oficina  CASCADE;
TRUNCATE TABLE dir_catcomunidadautonoma  CASCADE;
TRUNCATE TABLE dir_catambitoterritorial  CASCADE;
TRUNCATE TABLE dir_cattipoentidadpublica  CASCADE;
TRUNCATE TABLE dir_catpais  CASCADE;
TRUNCATE TABLE dir_cattipovia  CASCADE;
TRUNCATE TABLE dir_catentidadgeografica  CASCADE;
TRUNCATE TABLE dir_catniveladministracion  CASCADE;
TRUNCATE TABLE dir_catisla  CASCADE;
TRUNCATE TABLE dir_catlocalidad  CASCADE;
TRUNCATE TABLE dir_cattipounidadorganica  CASCADE;
TRUNCATE TABLE dir_catestadoentidad  CASCADE;
ALTER SEQUENCE DIR_SEQ_ALL RESTART WITH 1;