-- Campos estado
ALTER TABLE DIR_CATAMBITOTERRITORIAL ADD COLUMN estado varchar(2) ;
ALTER TABLE DIR_CATCOMUNIDADAUTONOMA ADD COLUMN estado varchar(2) ;
ALTER TABLE DIR_CATENTIDADGEOGRAFICA ADD COLUMN estado varchar(2);
ALTER TABLE DIR_CATESTADOENTIDAD ADD COLUMN estado varchar(2);
ALTER TABLE DIR_CATISLA ADD COLUMN estado varchar(2);
ALTER TABLE DIR_CATJERARQUIAOFICINA ADD COLUMN estado varchar(2);
ALTER TABLE DIR_CATLOCALIDAD ADD COLUMN estado varchar(2);
ALTER TABLE DIR_CATMOTIVOEXTINCION ADD COLUMN estado varchar(2);
ALTER TABLE DIR_CATNIVELADMINISTRACION ADD COLUMN estado varchar(2);
ALTER TABLE DIR_CATPAIS ADD COLUMN estado varchar(2);
ALTER TABLE DIR_CATPROVINCIA ADD COLUMN estado varchar(2);
ALTER TABLE DIR_CATTIPOCONTACTO ADD COLUMN estado varchar(2);
ALTER TABLE DIR_CATTIPOENTIDADPUBLICA ADD COLUMN estado varchar(2);
ALTER TABLE DIR_CATTIPOUNIDADORGANICA ADD COLUMN estado varchar(2);
ALTER TABLE DIR_CATTIPOVIA ADD COLUMN estado varchar(2);

--Campos nuevos (no estado)
ALTER TABLE DIR_CATSERVICIO ADD COLUMN tipo int8;

--Foreign keys estado

alter table DIR_CATAMBITOTERRITORIAL
    add constraint DIR_CAMBTER_CESTENT_FK
        foreign key (ESTADO)
            references DIR_CATESTADOENTIDAD;

alter table DIR_CATCOMUNIDADAUTONOMA
    add constraint DIR_CCOMAUT_CESTENT_FK
        foreign key (ESTADO)
            references DIR_CATESTADOENTIDAD;

alter table DIR_CATENTIDADGEOGRAFICA
    add constraint DIR_CENTGEO_CESTENT_FK
        foreign key (ESTADO)
            references DIR_CATESTADOENTIDAD;

alter table DIR_CATESTADOENTIDAD
    add constraint DIR_CESTENT_CESTENT_FK
        foreign key (ESTADO)
            references DIR_CATESTADOENTIDAD;

alter table DIR_CATISLA
    add constraint DIR_CATISLA_CESTENT_FK
        foreign key (ESTADO)
            references DIR_CATESTADOENTIDAD;

alter table DIR_CATJERARQUIAOFICINA
    add constraint DIR_CJEROFI_CESTENT_FK
        foreign key (ESTADO)
            references DIR_CATESTADOENTIDAD;

alter table DIR_CATLOCALIDAD
    add constraint DIR_CLOCAL_CESTENT_FK
        foreign key (ESTADO)
            references DIR_CATESTADOENTIDAD;

alter table DIR_CATMOTIVOEXTINCION
    add constraint DIR_CMOTEXT_CESTENT_FK
        foreign key (ESTADO)
            references DIR_CATESTADOENTIDAD;

alter table DIR_CATNIVELADMINISTRACION
    add constraint DIR_CNIVADM_CESTENT_FK
        foreign key (ESTADO)
            references DIR_CATESTADOENTIDAD;

alter table DIR_CATPAIS
    add constraint DIR_CPAIS_CESTENT_FK
        foreign key (ESTADO)
            references DIR_CATESTADOENTIDAD;

alter table DIR_CATPROVINCIA
    add constraint DIR_CPROVIN_CESTENT_FK
        foreign key (ESTADO)
            references DIR_CATESTADOENTIDAD;

alter table DIR_CATTIPOCONTACTO
    add constraint DIR_CTIPCON_CESTENT_FK
        foreign key (ESTADO)
            references DIR_CATESTADOENTIDAD;

alter table DIR_CATTIPOENTIDADPUBLICA
    add constraint DIR_CTIENPU_CESTENT_FK
        foreign key (ESTADO)
            references DIR_CATESTADOENTIDAD;

alter table DIR_CATTIPOUNIDADORGANICA
    add constraint DIR_CTIUNOR_CESTENT_FK
        foreign key (ESTADO)
            references DIR_CATESTADOENTIDAD;

alter table DIR_CATTIPOVIA
    add constraint DIR_CTIPVIA_CESTENT_FK
        foreign key (ESTADO)
            references DIR_CATESTADOENTIDAD;

-- Nuevas tablas
create table DIR_CATPODER (
      CODIGOPODER int8 not null,
      DESCRIPCIONPODER varchar(50) not null,
      ESTADO varchar(2),
      primary key (CODIGOPODER)
);

alter table DIR_CATPODER
    add constraint DIR_CPODER_CESTENT_FK
        foreign key (ESTADO)
            references DIR_CATESTADOENTIDAD;


create table DIR_CATTIPOCODFUENTEEXTERNA (
     CODIGOTIPCODFUENTEEXTERNA int8 not null,
     DESCRIPCIONTIPCODFUENTEEXTERNA varchar(30) not null,
     ESTADO varchar(2),
     primary key (CODIGOTIPCODFUENTEEXTERNA)
);

alter table DIR_CATTIPOCODFUENTEEXTERNA
    add constraint DIR_CFUEEXT_CESTENT_FK
        foreign key (ESTADO)
            references DIR_CATESTADOENTIDAD;

create table DIR_CATTIPOSERVICIO (
     CODIGOTIPOSERVICIO int8 not null,
     DESCRIPCIONTIPOSERVICIO varchar(300) not null,
     primary key (CODIGOTIPOSERVICIO)
);

create table DIR_SERVICIOUO (
    CODUNIDAD varchar(9) not null,
    CODSERVICIO int8 not null,
    primary key (CODUNIDAD, CODSERVICIO)
);

alter table DIR_SERVICIOUO
    add constraint DIR_UNI_SERV_FK
        foreign key (CODSERVICIO)
            references DIR_CATSERVICIO;

alter table DIR_SERVICIOUO
    add constraint DIR_SER_UNI_FK
        foreign key (CODUNIDAD)
            references DIR_UNIDAD;

--Servicio
alter table DIR_CATSERVICIO
    add constraint DIR_CSERVIC_CTIPSERV_FK
        foreign key (TIPO)
            references DIR_CATTIPOSERVICIO;


--Sequences
create sequence DIR_CAMBTER_SEQ;

create sequence DIR_CLOCA_SEQ;


--Indexos
create index DIR_CAMBTER_CESTENT_FK_I on DIR_CATAMBITOTERRITORIAL (ESTADO);
create index DIR_CCOMAUT_CESTENT_FK_I on DIR_CATCOMUNIDADAUTONOMA (ESTADO);
create index DIR_CENTGEO_CESTENT_FK_I on DIR_CATENTIDADGEOGRAFICA (ESTADO);
create index DIR_CESTENT_CESTENT_FK_I on DIR_CATESTADOENTIDAD (ESTADO);
create index DIR_CATISLA_CESTENT_FK_I on DIR_CATISLA (ESTADO);
create index DIR_CJEROFI_CESTENT_FK_I on DIR_CATJERARQUIAOFICINA (ESTADO);
create index DIR_CLOCAL_CESTENT_FK_I on DIR_CATLOCALIDAD (ESTADO);
create index DIR_CMOTEXT_CESTENT_FK_I on DIR_CATMOTIVOEXTINCION (ESTADO);
create index DIR_CNIVADM_CESTENT_FK_I on DIR_CATNIVELADMINISTRACION (ESTADO);
create index DIR_CPAIS_CESTENT_FK_I on DIR_CATPAIS (ESTADO);
create index DIR_CPODER_CESTENT_FK_I on DIR_CATPODER (ESTADO);
create index DIR_CPROVIN_CESTENT_FK_I on DIR_CATPROVINCIA (ESTADO);
create index DIR_CFUEEXT_CESTENT_FK_I on DIR_CATTIPOCODFUENTEEXTERNA (ESTADO);
create index DIR_CTIPCON_CESTENT_FK_I on DIR_CATTIPOCONTACTO (ESTADO);
create index DIR_CTIENPU_CESTENT_FK_I on DIR_CATTIPOENTIDADPUBLICA (ESTADO);
create index DIR_CTIUNOR_CESTENT_FK_I on DIR_CATTIPOUNIDADORGANICA (ESTADO);
create index DIR_CTIPVIA_CESTENT_FK_I on DIR_CATTIPOVIA (ESTADO);
create index DIR_CSERVIC_CTIPSERV_FK_I on DIR_CATSERVICIO (TIPO);