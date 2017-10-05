create table DIR_SINCRONIZACION (
  CODIGO number(19,0) not null,
  ESTADO number(19,0),
  FECHAFIN timestamp,
  FECHAIMPORTACION timestamp,
  FECHAINICIO timestamp,
  TIPO varchar2(255 char)
);
alter table DIR_SINCRONIZACION add constraint DIR_SINCRONIZACION_pk primary key (CODIGO);

delete from dir_unidad;
delete from dir_catmotivoextincion;
delete from dir_descarga;
delete from dir_historicouo;
delete from dir_contactoofi;
delete from dir_cattipocontacto;
delete from dir_contactouo;
delete from dir_historicoofi;
delete from dir_catjerarquiaoficina;
delete from dir_relacionorganizativaofi;
delete from dir_catprovincia;
delete from dir_relacionsirofi;
delete from dir_catservicio;
delete from dir_servicioofi;
delete from dir_oficina;
delete from dir_catcomunidadautonoma;
delete from dir_catambitoterritorial;
delete from dir_cattipoentidadpublica;
delete from dir_catpais;
delete from dir_cattipovia;
delete from dir_catentidadgeografica;
delete from dir_catniveladministracion;
delete from dir_catisla;
delete from dir_catlocalidad;
delete from dir_cattipounidadorganica;
delete from dir_catestadoentidad;
drop SEQUENCE DIR_SEQ_ALL ;
create sequence DIR_SEQ_ALL;