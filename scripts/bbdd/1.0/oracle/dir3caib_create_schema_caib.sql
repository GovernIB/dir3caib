
    create table DIR_CATAMBITOTERRITORIAL (
        CODIGOAMBITO varchar2(2 char) not null,
        DESCRIPCIONAMBITO varchar2(30 char) not null,
        NIVELADMINISTRACION number(19,0)
    );

    create table DIR_CATCOMUNIDADAUTONOMA (
        CODIGOCOMUNIDAD number(19,0) not null,
        C_CODIGO_DIR2 number(19,0),
        C_COMUNIDAD_RPC varchar2(2 char),
        DESCRIPCIONCOMUNIDAD varchar2(50 char) not null,
        PAIS number(19,0)
    );

    create table DIR_CATENTIDADGEOGRAFICA (
        CODIGOENTIDADGEOGRAFICA varchar2(2 char) not null,
        DESCRIPCIONENTIDADGEOGRAFICA varchar2(50 char) not null
    );

    create table DIR_CATESTADOENTIDAD (
        CODIGOESTADOENTIDAD varchar2(2 char) not null,
        DESCRIPCIONESTADOENTIDAD varchar2(50 char) not null
    );

    create table DIR_CATISLA (
        CODIGOISLA number(19,0) not null,
        DESCRIPCIONISLA varchar2(50 char) not null,
        PROVINCIA number(19,0)
    );

    create table DIR_CATJERARQUIAOFICINA (
        CODIGOJERARQUIAOFICINA number(19,0) not null,
        DESCRIPCIONJERARQUIAOFICINA varchar2(50 char) not null
    );

    create table DIR_CATLOCALIDAD (
        CODIGOLOCALIDAD number(19,0) not null,
        DESCRIPCIONLOCALIDAD varchar2(50 char),
        ENTIDADGEOGRAFICA varchar2(2 char),
        PROVINCIA number(19,0)
    );

    create table DIR_CATMOTIVOEXTINCION (
        CODIGOMOTIVOEXTINCION varchar2(3 char) not null,
        DESCRIPCIONMOTIVOEXTINCION varchar2(400 char) not null
    );

    create table DIR_CATNIVELADMINISTRACION (
        CODIGONIVELADMINISTRACION number(19,0) not null,
        DESCRIPCIONNIVELADMINISTRACION varchar2(300 char) not null
    );

    create table DIR_CATPAIS (
        CODIGOPAIS number(19,0) not null,
        ALFA2PAIS varchar2(2 char),
        ALFA3PAIS varchar2(3 char),
        DESCRIPCIONPAIS varchar2(50 char) not null
    );

    create table DIR_CATPROVINCIA (
        CODIGOPROVINCIA number(19,0) not null,
        DESCRIPCIONPROVINCIA varchar2(50 char) not null,
        COMUNIDADAUTONOMA number(19,0)
    );

    create table DIR_CATSERVICIO (
        CODSERVICIO number(19,0) not null,
        DESCSERVICIO varchar2(300 char) not null
    );

    create table DIR_CATTIPOCONTACTO (
        CODIGOTIPOCONTACTO varchar2(2 char) not null,
        DESCRIPCIONTIPOCONTACTO varchar2(30 char) not null
    );

    create table DIR_CATTIPOENTIDADPUBLICA (
        CODIGOTIPOENTIDADPUBLICA varchar2(2 char) not null,
        DESCRIPCIONTIPOENTIDADPUBLICA varchar2(50 char) not null
    );

    create table DIR_CATTIPOUNIDADORGANICA (
        CODIGOTIPOUNIDADORGANICA varchar2(3 char) not null,
        DESCRIPCIONTIPOUNIDADORGANICA varchar2(100 char) not null
    );

    create table DIR_CATTIPOVIA (
        CODIGOTIPOVIA number(19,0) not null,
        DESCRIPCIONTIPOVIA varchar2(50 char) not null
    );

    create table DIR_CONTACTOOFI (
        CODCONTACTO number(19,0) not null,
        VALORCONTACTO varchar2(100 char) not null,
        VISIBILIDAD number(1,0),
        CODOFICINA varchar2(9 char),
        TIPOCONTACTO varchar2(2 char)
    );

    create table DIR_CONTACTOUO (
        CODCONTACTO number(19,0) not null,
        VALORCONTACTO varchar2(100 char) not null,
        VISIBILIDAD number(1,0),
        TIPOCONTACTO varchar2(2 char),
        CODUNIDAD varchar2(9 char)
    );

    create table DIR_DESCARGA (
        CODIGO number(19,0) not null,
        FECHAFIN varchar2(255 char),
        FECHAIMPORTACION varchar2(255 char),
        FECHAINICIO varchar2(255 char),
        TIPO varchar2(255 char)
    );

    create table DIR_HISTORICOOFI (
        CODANTERIOR varchar2(9 char) not null,
        CODULTIMA varchar2(9 char) not null
    );

    create table DIR_HISTORICOUO (
        CODANTERIOR varchar2(9 char) not null,
        CODULTIMA varchar2(9 char) not null
    );

    create table DIR_OFICINA (
        CODIGO varchar2(9 char) not null,
        CODPOSTAL varchar2(14 char),
        COMPLEMENTO varchar2(300 char),
        DENOMINACION varchar2(300 char),
        DIASSINHABILES varchar2(400 char),
        DIREXTRANJERA varchar2(200 char),
        DIRECCIONOBSERVACIONES varchar2(400 char),
        FECHAALTAOFICIAL date,
        FECHAANULACION date,
        FECHAEXTINCION date,
        FECHAIMPORTACION date,
        HORARIOANTENCION varchar2(400 char),
        LOCEXTRANJERA varchar2(40 char),
        NOMBREVIA varchar2(300 char),
        NUMVIA varchar2(20 char),
        OBSERVACIONES varchar2(400 char),
        CODCOMUNIDAD number(19,0),
        CODOFIRESPONSABLE varchar2(9 char),
        CODPAIS number(19,0),
        CODUORESPONSABLE varchar2(9 char),
        ESTADO varchar2(2 char),
        CODPROVINCIA number(19,0),
        CODENTGEOGRAFICA varchar2(2 char),
        CODLOCALIDAD number(19,0),
        NIVELADMINISTRACION number(19,0),
        TIPOOFICINA number(19,0),
        TIPOVIA number(19,0)
    );

    create table DIR_RELACIONORGANIZATIVAOFI (
        CODUNIDAD varchar2(9 char),
        CODOFICINA varchar2(9 char),
        ESTADO varchar2(2 char)
    );

    create table DIR_RELACIONSIROFI (
        CODUNIDAD varchar2(9 char),
        CODOFICINA varchar2(9 char),
        ESTADO varchar2(2 char)
    );

    create table DIR_SERVICIOOFI (
        CODOFICINA varchar2(9 char) not null,
        CODSERVICIO number(19,0) not null
    );

    create table DIR_UNIDAD (
        CODIGO varchar2(9 char) not null,
        CODAMBELM number(19,0),
        CODAMBLOCEXTRANJERA varchar2(50 char),
        CODEXTERNO varchar2(40 char),
        CODPOSTAL varchar2(14 char),
        COMPETENCIAS varchar2(400 char),
        COMPLEMENTO varchar2(300 char),
        DENOMINACION varchar2(300 char),
        DIREXTRANJERA varchar2(200 char),
        DISPOSICIONLEGAL varchar2(400 char),
        ESEDP number(1,0),
        FECHAALTAOFICIAL date,
        FECHAANULACION date,
        FECHABAJAOFICIAL date,
        FECHAEXTINCION date,
        FECHAIMPORTACION date,
        LOCEXTRANJERA varchar2(40 char),
        NIFCIF varchar2(9 char),
        NIVELJERARQUICO number(19,0),
        NOMBREVIA varchar2(300 char),
        NUMVIA varchar2(20 char),
        OBSERVBAJA varchar2(400 char),
        OBSERVGENERALES varchar2(400 char),
        OBSERVACIONES varchar2(400 char),
        SIGLAS varchar2(10 char),
        CODAMBPROVINCIA2 number(19,0),
        CODAMBENTGEOGRAFICA2 varchar2(2 char),
        CODAMBMUNICIPIO number(19,0),
        CODAMBCOMUNIDAD number(19,0),
        CODAMBENTGEOGRAFICA varchar2(2 char),
        CODAMBISLA number(19,0),
        CODAMBPAIS number(19,0),
        CODAMBPROVINCIA number(19,0),
        NIVELADMINISTRACION2 number(19,0),
        CODAMBITOTERRITORIAL varchar2(2 char),
        CODCOMUNIDAD number(19,0),
        CODEDPPRINCIPAL varchar2(9 char),
        CODPROVINCIA number(19,0),
        CODENTGEOGRAFICA varchar2(2 char),
        CODLOCALIDAD number(19,0),
        CODPAIS number(19,0),
        CODTIPOENTPUBLICA varchar2(2 char),
        CODTIPOUNIDAD varchar2(3 char),
        CODUNIDADRAIZ varchar2(9 char),
        CODUNIDADSUPERIOR varchar2(9 char),
        ESTADO varchar2(2 char),
        NIVELADMINISTRACION number(19,0),
        TIPOVIA number(19,0)
    );

    create sequence DIR_SEQ_ALL;


 -- INICI Indexes
    create index DIR_CATAMBTERR_CATNIVADM_FK_I on DIR_CATAMBITOTERRITORIAL (NIVELADMINISTRACION);
    create index DIR_CATAMBITOTERRITORIAL_PK_I on DIR_CATAMBITOTERRITORIAL (CODIGOAMBITO, NIVELADMINISTRACION);
    create index DIR_CATCOMUNAUT_CATPAIS_FK_I on DIR_CATCOMUNIDADAUTONOMA (PAIS);
    create index DIR_CATLOCAL_CATPROVIN_FK_I on DIR_CATLOCALIDAD (PROVINCIA);
    create index DIR_CATLOCAL_CATENTGEOGR_FK_I on DIR_CATLOCALIDAD (ENTIDADGEOGRAFICA);
    create index DIR_CATPROV_CATCOMUNAUT_FK_I on DIR_CATPROVINCIA (COMUNIDADAUTONOMA);
    create index RWE_CATPROVINCIA_PK_I on DIR_CATPROVINCIA (CODIGOPROVINCIA);
    create index DIR_CONTOFI_CATTIPCONT_FK_I on DIR_CONTACTOOFI (TIPOCONTACTO);
    create index DIR_OFICINA_CONTACTOSOFI_FK_I on DIR_CONTACTOOFI (CODOFICINA);
    create index DIR_UNIDAD_CONTACTOSUO_FK_I on DIR_CONTACTOUO (CODUNIDAD);
    create index DIR_CONTACUO_CATTIPOCONT_FK_I on DIR_CONTACTOUO (TIPOCONTACTO);
    create index DIR_OFICINA_OFICINA_FK_I on DIR_OFICINA (CODOFIRESPONSABLE);
    create index DIR_OFICINA_CATTIPOVIA_FK_I on DIR_OFICINA (TIPOVIA);
    create index DIR_OFICINA_CATESTADENTI_FK_I on DIR_OFICINA (ESTADO);
    create index DIR_OFICINA_CATPAIS_FK_I on DIR_OFICINA (CODPAIS);
    create index DIR_OFICINA_CATLOCAL_FK_I on DIR_OFICINA (CODPROVINCIA, CODENTGEOGRAFICA, CODLOCALIDAD);
    create index DIR_OFICINA_CATNIVELADMIN_FK_I on DIR_OFICINA (NIVELADMINISTRACION);
    create index DIR_OFICINA_UNIDAD_FK_I on DIR_OFICINA (CODUORESPONSABLE);
    create index DIR_OFICINA_CATCOMUNIAUT_FK_I on DIR_OFICINA (CODCOMUNIDAD);
    create index DIR_OFICINA_CATJERAROFI_FK_I on DIR_OFICINA (TIPOOFICINA);
    create index DIR_RELORGANOFI_CATESTENT_FK_I on DIR_RELACIONORGANIZATIVAOFI (ESTADO);
    create index DIR_OFICINA_RELORGOFI_FK_I on DIR_RELACIONORGANIZATIVAOFI (CODOFICINA);
    create index DIR_UNIDAD_RELORGOFI_FK_I on DIR_RELACIONORGANIZATIVAOFI (CODUNIDAD);
    create index DIR_OFICINA_RELSIROFI_FK_I on DIR_RELACIONSIROFI (CODOFICINA);
    create index DIR_UNIDAD_RELSIROFI_FK_I on DIR_RELACIONSIROFI (CODUNIDAD);
    create index DIR_RELSIROFI_CATESTENTI_FK_I on DIR_RELACIONSIROFI (ESTADO);
    create index DIR_UNIDAD_CATAMBITTERR_FK_I on DIR_UNIDAD (NIVELADMINISTRACION, CODAMBITOTERRITORIAL);
    create index DIR_UNIDAD_CATTIPOVIA_FK_I on DIR_UNIDAD (TIPOVIA);
    create index DIR_UNIDAD_UNIDADRAIZ_FK_I on DIR_UNIDAD (CODUNIDADRAIZ);
    create index DIR_UNIDAD_CATAMBPAIS_FK_I on DIR_UNIDAD (CODAMBPAIS);
    create index DIR_UNIDAD_MUNICIPIO_FK_I on DIR_UNIDAD (CODAMBPROVINCIA, CODAMBENTGEOGRAFICA, CODAMBMUNICIPIO);
    create index DIR_UNIDAD_CATAMBCOMAUTO_FK_I on DIR_UNIDAD (CODAMBCOMUNIDAD);
    create index DIR_UNIDAD_CATTIPENTPUBL_FK_I on DIR_UNIDAD (CODTIPOENTPUBLICA);
    create index DIR_UNIDAD_CATISLA_FK_I on DIR_UNIDAD (CODAMBISLA);
    create index DIR_UNIDAD_CATCOMUNIAUTO_FK_I on DIR_UNIDAD (CODCOMUNIDAD);
    create index DIR_UNIDAD_CATPROVINCIA_FK_I on DIR_UNIDAD (CODAMBPROVINCIA);
    create index DIR_UNIDAD_CATPAIS_FK_I on DIR_UNIDAD (CODPAIS);
    create index DIR_UNIDAD_PK_I on DIR_UNIDAD (CODIGO);
    create index DIR_UNIDAD_UNIDADEDPPRINC_FK_I on DIR_UNIDAD (CODEDPPRINCIPAL);
    create index DIR_UNIDAD_CATNIVELADMIN_FK_I on DIR_UNIDAD (NIVELADMINISTRACION);
    create index DIR_UNIDAD_CATTIPUNIORG_FK_I on DIR_UNIDAD (CODTIPOUNIDAD);
    create index DIR_UNIDAD_CATENTGEOGRAF_FK_I on DIR_UNIDAD (CODAMBENTGEOGRAFICA);
    create index DIR_UNIDAD_CATLOCAL_FK_I on DIR_UNIDAD (CODPROVINCIA, CODENTGEOGRAFICA, CODLOCALIDAD);
    create index DIR_UNIDAD_UNIDADSUPERIOR_FK_I on DIR_UNIDAD (CODUNIDADSUPERIOR);
    create index DIR_UNIDAD_CATESTENTIDAD_FK_I on DIR_UNIDAD (ESTADO);
 -- FINAL Indexes

 -- INICI PK's
    alter table DIR_CATAMBITOTERRITORIAL add constraint DIR_CATAMBITOTERRITORIAL_pk primary key (NIVELADMINISTRACION, CODIGOAMBITO);

    alter table DIR_CATCOMUNIDADAUTONOMA add constraint DIR_CATCOMUNIDADAUTONOMA_pk primary key (CODIGOCOMUNIDAD);

    alter table DIR_CATENTIDADGEOGRAFICA add constraint DIR_CATENTIDADGEOGRAFICA_pk primary key (CODIGOENTIDADGEOGRAFICA);

    alter table DIR_CATESTADOENTIDAD add constraint DIR_CATESTADOENTIDAD_pk primary key (CODIGOESTADOENTIDAD);

    alter table DIR_CATISLA add constraint DIR_CATISLA_pk primary key (CODIGOISLA);

    alter table DIR_CATJERARQUIAOFICINA add constraint DIR_CATJERARQUIAOFICINA_pk primary key (CODIGOJERARQUIAOFICINA);

    alter table DIR_CATLOCALIDAD add constraint DIR_CATLOCALIDAD_pk primary key (PROVINCIA, ENTIDADGEOGRAFICA, CODIGOLOCALIDAD);

    alter table DIR_CATMOTIVOEXTINCION add constraint DIR_CATMOTIVOEXTINCION_pk primary key (CODIGOMOTIVOEXTINCION);

    alter table DIR_CATNIVELADMINISTRACION add constraint DIR_CATNIVELADMINISTRACION_pk primary key (CODIGONIVELADMINISTRACION);

    alter table DIR_CATPAIS add constraint DIR_CATPAIS_pk primary key (CODIGOPAIS);

    alter table DIR_CATPROVINCIA add constraint DIR_CATPROVINCIA_pk primary key (CODIGOPROVINCIA);

    alter table DIR_CATSERVICIO add constraint DIR_CATSERVICIO_pk primary key (CODSERVICIO);

    alter table DIR_CATTIPOCONTACTO add constraint DIR_CATTIPOCONTACTO_pk primary key (CODIGOTIPOCONTACTO);

    alter table DIR_CATTIPOENTIDADPUBLICA add constraint DIR_CATTIPOENTIDADPUBLICA_pk primary key (CODIGOTIPOENTIDADPUBLICA);

    alter table DIR_CATTIPOUNIDADORGANICA add constraint DIR_CATTIPOUNIDADORGANICA_pk primary key (CODIGOTIPOUNIDADORGANICA);

    alter table DIR_CATTIPOVIA add constraint DIR_CATTIPOVIA_pk primary key (CODIGOTIPOVIA);

    alter table DIR_CONTACTOOFI add constraint DIR_CONTACTOOFI_pk primary key (CODCONTACTO);

    alter table DIR_CONTACTOUO add constraint DIR_CONTACTOUO_pk primary key (CODCONTACTO);

    alter table DIR_DESCARGA add constraint DIR_DESCARGA_pk primary key (CODIGO);

    alter table DIR_HISTORICOOFI add constraint DIR_HISTORICOOFI_pk primary key (CODANTERIOR, CODULTIMA);

    alter table DIR_HISTORICOUO add constraint DIR_HISTORICOUO_pk primary key (CODANTERIOR, CODULTIMA);

    alter table DIR_OFICINA add constraint DIR_OFICINA_pk primary key (CODIGO);

    alter table DIR_RELACIONORGANIZATIVAOFI add constraint DIR_RELACIONORGANIZATIVAOFI_pk primary key (CODUNIDAD, CODOFICINA);

    alter table DIR_RELACIONSIROFI add constraint DIR_RELACIONSIROFI_pk primary key (CODUNIDAD, CODOFICINA);

    alter table DIR_SERVICIOOFI add constraint DIR_SERVICIOOFI_pk primary key (CODOFICINA, CODSERVICIO);

    alter table DIR_UNIDAD add constraint DIR_UNIDAD_pk primary key (CODIGO);

 -- FINAL PK's

 -- INICI FK's

    alter table DIR_CATAMBITOTERRITORIAL 
        add constraint DIR_CATAMBTERR_CATNIVADM_FK 
        foreign key (NIVELADMINISTRACION) 
        references DIR_CATNIVELADMINISTRACION;

    alter table DIR_CATCOMUNIDADAUTONOMA 
        add constraint DIR_CATCOMUNAUT_CATPAIS_FK 
        foreign key (PAIS) 
        references DIR_CATPAIS;

    alter table DIR_CATISLA 
        add constraint DIR_CATISLA_CATPROV_FK 
        foreign key (PROVINCIA) 
        references DIR_CATPROVINCIA;

    alter table DIR_CATLOCALIDAD 
        add constraint DIR_CATLOCAL_CATPROVIN_FK 
        foreign key (PROVINCIA) 
        references DIR_CATPROVINCIA;

    alter table DIR_CATLOCALIDAD 
        add constraint DIR_CATLOCAL_CATENTGEOGR_FK 
        foreign key (ENTIDADGEOGRAFICA) 
        references DIR_CATENTIDADGEOGRAFICA;

    alter table DIR_CATPROVINCIA 
        add constraint DIR_CATPROVINC_CATCOMUNAUTO_FK 
        foreign key (COMUNIDADAUTONOMA) 
        references DIR_CATCOMUNIDADAUTONOMA;

    alter table DIR_CONTACTOOFI 
        add constraint DIR_CONTACOFI_CATTIPCONTAC_FK 
        foreign key (TIPOCONTACTO) 
        references DIR_CATTIPOCONTACTO;

    alter table DIR_CONTACTOOFI 
        add constraint DIR_OFICINA_CONTACTOSOFI_FK 
        foreign key (CODOFICINA) 
        references DIR_OFICINA;

    alter table DIR_CONTACTOUO 
        add constraint DIR_CONTACTOUO_CATTIPOCONT_FK 
        foreign key (TIPOCONTACTO) 
        references DIR_CATTIPOCONTACTO;

    alter table DIR_CONTACTOUO 
        add constraint DIR_UNIDAD_CONTACTOSUO_FK 
        foreign key (CODUNIDAD) 
        references DIR_UNIDAD;

    alter table DIR_HISTORICOOFI 
        add constraint DIR_OFI_OFI_HISTANTE_FK 
        foreign key (CODANTERIOR) 
        references DIR_OFICINA;

    alter table DIR_HISTORICOOFI 
        add constraint DIR_OFI_OFI_HISTULTI_FK 
        foreign key (CODULTIMA) 
        references DIR_OFICINA;

    alter table DIR_HISTORICOUO 
        add constraint DIR_UNI_UNI_HISTANTE_FK 
        foreign key (CODANTERIOR) 
        references DIR_UNIDAD;

    alter table DIR_HISTORICOUO 
        add constraint DIR_UNI_UNI_HISTULTI_FK 
        foreign key (CODULTIMA) 
        references DIR_UNIDAD;

    alter table DIR_OFICINA 
        add constraint DIR_OFICINA_CATTIPOVIA_FK 
        foreign key (TIPOVIA) 
        references DIR_CATTIPOVIA;

    alter table DIR_OFICINA 
        add constraint DIR_OFICINA_CATPAIS_FK 
        foreign key (CODPAIS) 
        references DIR_CATPAIS;

    alter table DIR_OFICINA 
        add constraint DIR_OFICINA_OFICINA_FK 
        foreign key (CODOFIRESPONSABLE) 
        references DIR_OFICINA;

    alter table DIR_OFICINA 
        add constraint DIR_OFICINA_CATJERARQUIAOFI_FK 
        foreign key (TIPOOFICINA) 
        references DIR_CATJERARQUIAOFICINA;

    alter table DIR_OFICINA 
        add constraint DIR_OFICINA_CATCOMUNIAUT_FK 
        foreign key (CODCOMUNIDAD) 
        references DIR_CATCOMUNIDADAUTONOMA;

    alter table DIR_OFICINA 
        add constraint DIR_OFICINA_CATNIVELADMIN_FK 
        foreign key (NIVELADMINISTRACION) 
        references DIR_CATNIVELADMINISTRACION;

    alter table DIR_OFICINA 
        add constraint DIR_OFICINA_CATESTADENTIDAD_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_OFICINA 
        add constraint DIR_OFICINA_CATLOCAL_FK 
        foreign key (CODPROVINCIA, CODENTGEOGRAFICA, CODLOCALIDAD) 
        references DIR_CATLOCALIDAD;

    alter table DIR_OFICINA 
        add constraint DIR_OFICINA_UNIDAD_FK 
        foreign key (CODUORESPONSABLE) 
        references DIR_UNIDAD;

    alter table DIR_RELACIONORGANIZATIVAOFI 
        add constraint DIR_RELORGANOFI_CATESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_RELACIONORGANIZATIVAOFI 
        add constraint DIR_UNIDAD_RELORGOFI_FK 
        foreign key (CODUNIDAD) 
        references DIR_UNIDAD;

    alter table DIR_RELACIONORGANIZATIVAOFI 
        add constraint DIR_OFICINA_RELORGOFI_FK 
        foreign key (CODOFICINA) 
        references DIR_OFICINA;

    alter table DIR_RELACIONSIROFI 
        add constraint DIR_RELSIROFI_CATESTENTI_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_RELACIONSIROFI 
        add constraint DIR_UNIDAD_RELSIROFI_FK 
        foreign key (CODUNIDAD) 
        references DIR_UNIDAD;

    alter table DIR_RELACIONSIROFI 
        add constraint DIR_OFICINA_RELSIROFI_FK 
        foreign key (CODOFICINA) 
        references DIR_OFICINA;

    alter table DIR_SERVICIOOFI 
        add constraint DIR_OFI_SERV_FK 
        foreign key (CODSERVICIO) 
        references DIR_CATSERVICIO;

    alter table DIR_SERVICIOOFI 
        add constraint DIR_SER_OFI_FK 
        foreign key (CODOFICINA) 
        references DIR_OFICINA;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_CATPROVINCIA_FK 
        foreign key (CODAMBPROVINCIA) 
        references DIR_CATPROVINCIA;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_UNIDADRAIZ_FK 
        foreign key (CODUNIDADRAIZ) 
        references DIR_UNIDAD;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_CATAMBCOMAUTO_FK 
        foreign key (CODAMBCOMUNIDAD) 
        references DIR_CATCOMUNIDADAUTONOMA;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_CATCOMUNIAUTO_FK 
        foreign key (CODCOMUNIDAD) 
        references DIR_CATCOMUNIDADAUTONOMA;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_UNIDADSUPERIOR_FK 
        foreign key (CODUNIDADSUPERIOR) 
        references DIR_UNIDAD;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_CATTIPOVIA_FK 
        foreign key (TIPOVIA) 
        references DIR_CATTIPOVIA;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_CATPAIS_FK 
        foreign key (CODPAIS) 
        references DIR_CATPAIS;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_CATENTGEOGRAF_FK 
        foreign key (CODAMBENTGEOGRAFICA) 
        references DIR_CATENTIDADGEOGRAFICA;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_CATAMBPAIS_FK 
        foreign key (CODAMBPAIS) 
        references DIR_CATPAIS;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_CATAMBITTERR_FK 
        foreign key (NIVELADMINISTRACION2, CODAMBITOTERRITORIAL) 
        references DIR_CATAMBITOTERRITORIAL;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_CATTIPENTPUBLICA_FK 
        foreign key (CODTIPOENTPUBLICA) 
        references DIR_CATTIPOENTIDADPUBLICA;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_CATISLA_FK 
        foreign key (CODAMBISLA) 
        references DIR_CATISLA;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_MUNICIPIO_FK 
        foreign key (CODAMBPROVINCIA2, CODAMBENTGEOGRAFICA2, CODAMBMUNICIPIO) 
        references DIR_CATLOCALIDAD;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_CATTIPUNIDORGAN_FK 
        foreign key (CODTIPOUNIDAD) 
        references DIR_CATTIPOUNIDADORGANICA;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_CATNIVELADMIN_FK 
        foreign key (NIVELADMINISTRACION) 
        references DIR_CATNIVELADMINISTRACION;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_CATESTENTIDAD_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_LOCDIRECCION_FK 
        foreign key (CODPROVINCIA, CODENTGEOGRAFICA, CODLOCALIDAD) 
        references DIR_CATLOCALIDAD;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_UNIDADEDPPRINC_FK 
        foreign key (CODEDPPRINCIPAL) 
        references DIR_UNIDAD;
 -- FINAL FK's

 -- INICI GRANTS
    grant select,insert,delete,update on DIR_CATAMBITOTERRITORIAL to www_dir3caib;
    grant select,insert,delete,update on DIR_CATCOMUNIDADAUTONOMA to www_dir3caib;
    grant select,insert,delete,update on DIR_CATENTIDADGEOGRAFICA to www_dir3caib;
    grant select,insert,delete,update on DIR_CATESTADOENTIDAD to www_dir3caib;
    grant select,insert,delete,update on DIR_CATISLA to www_dir3caib;
    grant select,insert,delete,update on DIR_CATJERARQUIAOFICINA to www_dir3caib;
    grant select,insert,delete,update on DIR_CATLOCALIDAD to www_dir3caib;
    grant select,insert,delete,update on DIR_CATMOTIVOEXTINCION to www_dir3caib;
    grant select,insert,delete,update on DIR_CATNIVELADMINISTRACION to www_dir3caib;
    grant select,insert,delete,update on DIR_CATPAIS to www_dir3caib;
    grant select,insert,delete,update on DIR_CATPROVINCIA to www_dir3caib;
    grant select,insert,delete,update on DIR_CATSERVICIO to www_dir3caib;
    grant select,insert,delete,update on DIR_CATTIPOCONTACTO to www_dir3caib;
    grant select,insert,delete,update on DIR_CATTIPOENTIDADPUBLICA to www_dir3caib;
    grant select,insert,delete,update on DIR_CATTIPOUNIDADORGANICA to www_dir3caib;
    grant select,insert,delete,update on DIR_CATTIPOVIA to www_dir3caib;
    grant select,insert,delete,update on DIR_CONTACTOOFI to www_dir3caib;
    grant select,insert,delete,update on DIR_CONTACTOUO to www_dir3caib;
    grant select,insert,delete,update on DIR_DESCARGA to www_dir3caib;
    grant select,insert,delete,update on DIR_HISTORICOOFI to www_dir3caib;
    grant select,insert,delete,update on DIR_HISTORICOUO to www_dir3caib;
    grant select,insert,delete,update on DIR_OFICINA to www_dir3caib;
    grant select,insert,delete,update on DIR_RELACIONORGANIZATIVAOFI to www_dir3caib;
    grant select,insert,delete,update on DIR_RELACIONSIROFI to www_dir3caib;
    grant select,insert,delete,update on DIR_SERVICIOOFI to www_dir3caib;
    grant select,insert,delete,update on DIR_UNIDAD to www_dir3caib;
    grant select on DIR_SEQ_ALL to www_dir3caib;
 -- FINAL GRANTS

