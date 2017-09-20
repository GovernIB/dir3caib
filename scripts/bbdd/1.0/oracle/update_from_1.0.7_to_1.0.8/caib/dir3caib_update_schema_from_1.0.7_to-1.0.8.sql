create table DIR_SINCRONIZACION (
        CODIGO number(19,0) not null,
        ESTADO number(19,0),
        FECHAFIN timestamp,
        FECHAIMPORTACION timestamp,
        FECHAINICIO timestamp,
        TIPO varchar2(255 char)
    );

alter table DIR_SINCRONIZACION add constraint DIR_SINCRONIZACION_pk primary key (CODIGO);
grant select,insert,delete,update on DIR_SINCRONIZACION to www_dir3caib;