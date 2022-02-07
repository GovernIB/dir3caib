
    create table DIR_CATAMBITOTERRITORIAL (
        ID number(19,0) not null,
        CODIGOAMBITO varchar2(2 char) not null,
        DESCRIPCIONAMBITO varchar2(30 char) not null,
        ESTADO varchar2(2 char),
        NIVELADMINISTRACION number(19,0)
    );

    create table DIR_CATCOMUNIDADAUTONOMA (
        CODIGOCOMUNIDAD number(19,0) not null,
        C_CODIGO_DIR2 number(19,0),
        C_COMUNIDAD_RPC varchar2(2 char),
        DESCRIPCIONCOMUNIDAD varchar2(50 char) not null,
        ESTADO varchar2(2 char),
        PAIS number(19,0)
    );

    create table DIR_CATENTIDADGEOGRAFICA (
        CODIGOENTIDADGEOGRAFICA varchar2(2 char) not null,
        DESCRIPCIONENTIDADGEOGRAFICA varchar2(50 char) not null,
        ESTADO varchar2(2 char)
    );

    create table DIR_CATESTADOENTIDAD (
        CODIGOESTADOENTIDAD varchar2(2 char) not null,
        DESCRIPCIONESTADOENTIDAD varchar2(50 char) not null,
        ESTADO varchar2(2 char)
    );

    create table DIR_CATISLA (
        CODIGOISLA number(19,0) not null,
        DESCRIPCIONISLA varchar2(50 char) not null,
        ESTADO varchar2(2 char),
        PROVINCIA number(19,0)
    );

    create table DIR_CATJERARQUIAOFICINA (
        CODIGOJERARQUIAOFICINA number(19,0) not null,
        DESCRIPCIONJERARQUIAOFICINA varchar2(50 char) not null,
        ESTADO varchar2(2 char)
    );

    create table DIR_CATLOCALIDAD (
        ID number(19,0) not null,
        CODIGOLOCALIDAD number(19,0) not null,
        DESCRIPCIONLOCALIDAD varchar2(50 char),
        ENTIDADGEOGRAFICA varchar2(2 char),
        ESTADO varchar2(2 char),
        PROVINCIA number(19,0)
    );

    create table DIR_CATMOTIVOEXTINCION (
        CODIGOMOTIVOEXTINCION varchar2(3 char) not null,
        DESCRIPCIONMOTIVOEXTINCION varchar2(400 char) not null,
        ESTADO varchar2(2 char)
    );

    create table DIR_CATNIVELADMINISTRACION (
        CODIGONIVELADMINISTRACION number(19,0) not null,
        DESCRIPCIONNIVELADMINISTRACION varchar2(300 char) not null,
        ESTADO varchar2(2 char)
    );

    create table DIR_CATPAIS (
        CODIGOPAIS number(19,0) not null,
        ALFA2PAIS varchar2(2 char),
        ALFA3PAIS varchar2(3 char),
        DESCRIPCIONPAIS varchar2(100 char) not null,
        ESTADO varchar2(2 char)
    );

    create table DIR_CATPODER (
        CODIGOPODER number(19,0) not null,
        DESCRIPCIONPODER varchar2(50 char) not null,
        ESTADO varchar2(2 char)
    );

    create table DIR_CATPROVINCIA (
        CODIGOPROVINCIA number(19,0) not null,
        DESCRIPCIONPROVINCIA varchar2(50 char) not null,
        COMUNIDADAUTONOMA number(19,0),
        ESTADO varchar2(2 char)
    );

    create table DIR_CATSERVICIO (
        CODSERVICIO number(19,0) not null,
        DESCSERVICIO varchar2(300 char) not null,
        TIPO number(19,0)
    );

    create table DIR_CATSERVICIOUO (
        CODSERVICIO number(19,0) not null,
        DESCSERVICIO varchar2(300 char) not null,
        TIPO number(19,0)
    );

    create table DIR_CATTIPOCODFUENTEEXTERNA (
        CODIGOTIPCODFUENTEEXTERNA number(19,0) not null,
        DESCRIPCIONTIPCODFUENTEEXTERNA varchar2(30 char) not null,
        ESTADO varchar2(2 char)
    );

    create table DIR_CATTIPOCONTACTO (
        CODIGOTIPOCONTACTO varchar2(2 char) not null,
        DESCRIPCIONTIPOCONTACTO varchar2(30 char) not null,
        ESTADO varchar2(2 char)
    );

    create table DIR_CATTIPOENTIDADPUBLICA (
        CODIGOTIPOENTIDADPUBLICA varchar2(2 char) not null,
        DESCRIPCIONTIPOENTIDADPUBLICA varchar2(100 char) not null,
        ESTADO varchar2(2 char)
    );

    create table DIR_CATTIPOSERVICIO (
        CODIGOTIPOSERVICIO number(19,0) not null,
        DESCRIPCIONTIPOSERVICIO varchar2(300 char) not null
    );

    create table DIR_CATTIPOUNIDADORGANICA (
        CODIGOTIPOUNIDADORGANICA varchar2(3 char) not null,
        DESCRIPCIONTIPOUNIDADORGANICA varchar2(100 char) not null,
        ESTADO varchar2(2 char)
    );

    create table DIR_CATTIPOVIA (
        CODIGOTIPOVIA number(19,0) not null,
        DESCRIPCIONTIPOVIA varchar2(50 char) not null,
        ESTADO varchar2(2 char)
    );

    create table DIR_CODIGOUO (
        CODUNIDADORGANICA number(19,0) not null,
        CODIGOEXTERNO varchar2(255 char),
        ESTADO varchar2(2 char),
        TIPOCODIGO number(19,0),
        CODUNIDAD varchar2(20 char)
    );

    create table DIR_CONTACTOOFI (
        CODCONTACTO number(19,0) not null,
        VALORCONTACTO varchar2(100 char) not null,
        VISIBILIDAD number(1,0),
        ESTADO varchar2(2 char),
        CODOFICINA varchar2(9 char),
        TIPOCONTACTO varchar2(2 char)
    );

    create table DIR_CONTACTOUO (
        CODCONTACTO number(19,0) not null,
        VALORCONTACTO varchar2(100 char) not null,
        VISIBILIDAD number(1,0),
        ESTADO varchar2(2 char),
        TIPOCONTACTO varchar2(2 char),
        CODUNIDAD varchar2(20 char)
    );

    create table DIR_HISTORICOOFI (
        ID number(19,0) not null,
        MOTIVORELACION varchar2(255 char),
        ESTADO varchar2(2 char),
        CODANTERIOR varchar2(9 char),
        CODULTIMA varchar2(9 char)
    );

    create table DIR_HISTORICOUO (
        ID number(19,0) not null,
        MOTIVORELACION varchar2(255 char),
        OBSERVEXTINCION varchar2(255 char),
        ESTADO varchar2(2 char),
        CODANTERIOR varchar2(20 char),
        CODULTIMA varchar2(20 char)
    );

    create table DIR_NIFCIFUO (
        ID number(19,0) not null,
        CODNIFCIF varchar2(255 char),
        NIFPRINCIPAL number(1,0),
        ESTADO varchar2(2 char),
        CODUNIDAD varchar2(20 char)
    );

    create table DIR_OFICINA (
        CODIGO varchar2(9 char) not null,
        CODPOSTAL varchar2(14 char),
        COMPLEMENTO varchar2(300 char),
        DENOMINACION varchar2(300 char),
        DENOMCOOFICIAL varchar2(300 char),
        DIASSINHABILES varchar2(400 char),
        DIREXTRANJERA varchar2(200 char),
        DIRECCIONOBSERVACIONES varchar2(400 char),
        FECHAALTAOFICIAL timestamp,
        FECHAANULACION timestamp,
        FECHAEXTINCION timestamp,
        FECHAIMPORTACION timestamp,
        FECHULTACTUALI timestamp,
        FUENTEEXTERNA varchar2(40 char),
        HORARIOANTENCION varchar2(400 char),
        IDIOMALENGUA number(10,0),
        LOCEXTRANJERA varchar2(40 char),
        NOMBREVIA varchar2(300 char),
        NUMVIA varchar2(20 char),
        OBSERVACIONES varchar2(400 char),
        CODCOMUNIDAD number(19,0),
        CODOFIRESPONSABLE varchar2(9 char),
        CODPAIS number(19,0),
        CODUORESPONSABLE varchar2(20 char),
        ESTADO varchar2(2 char),
        LOCALIDADID number(19,0),
        NIVELADMINISTRACION number(19,0),
        TIPOOFICINA number(19,0),
        TIPOVIA number(19,0)
    );

    create table DIR_RELACIONORGANIZATIVAOFI (
        ID number(19,0) not null,
        ESTADO varchar2(2 char),
        CODOFICINA varchar2(9 char) not null,
        CODUNIDAD varchar2(20 char) not null
    );

    create table DIR_RELACIONSIROFI (
        ID number(19,0) not null,
        ESTADO varchar2(2 char),
        CODOFICINA varchar2(9 char) not null,
        CODUNIDAD varchar2(20 char) not null
    );

    create table DIR_SERVICIOOFI (
        ID number(19,0) not null,
        ESTADO varchar2(2 char),
        CODOFICINA varchar2(9 char),
        CODSERVICIO number(19,0)
    );

    create table DIR_SERVICIOUO (
        ID number(19,0) not null,
        ESTADO varchar2(2 char),
        CODSERVICIO number(19,0),
        CODUNIDAD varchar2(20 char)
    );

    create table DIR_SINCRONIZACION (
        CODIGO number(19,0) not null,
        ESTADO number(19,0),
        FECHAFIN timestamp,
        FECHAIMPORTACION timestamp,
        FECHAINICIO timestamp,
        TIPO varchar2(255 char)
    );

    create table DIR_UNIDAD (
        CODIGO varchar2(20 char) not null,
        CODAMBELM number(19,0),
        CODAMBLOCEXTRANJERA varchar2(50 char),
        CODEXTERNO varchar2(40 char),
        CODPOSTAL varchar2(14 char),
        CODIGODIR3 varchar2(9 char) not null,
        COMPARTENIF number(1,0),
        COMPETENCIAS varchar2(400 char),
        COMPLEMENTO varchar2(300 char),
        DENOMCOOFICIAL varchar2(300 char),
        DENOMINACION varchar2(300 char),
        DIREXTRANJERA varchar2(200 char),
        DISPOSICIONLEGAL varchar2(400 char),
        ESEDP number(1,0),
        FECHAALTAOFICIAL timestamp,
        FECHAANULACION timestamp,
        FECHABAJAOFICIAL timestamp,
        FECHAEXTINCION timestamp,
        FECHAIMPORTACION timestamp,
        FECHULTACTUALI timestamp,
        IDIOMALENGUA number(10,0),
        LOCEXTRANJERA varchar2(120 char),
        NIFCIF varchar2(9 char),
        NIVELJERARQUICO number(19,0),
        NOMBREVIA varchar2(300 char),
        NUMVIA varchar2(20 char),
        OBSERVBAJA varchar2(400 char),
        OBSERVGENERALES varchar2(400 char),
        OBSERVACIONES varchar2(400 char),
        SIGLAS varchar2(10 char),
        VERSION number(19,0) not null,
        CODAMBLOCALIDADID number(19,0),
        CODAMBCOMUNIDAD number(19,0),
        CODAMBENTGEOGRAFICA varchar2(2 char),
        CODAMBISLA number(19,0),
        CODAMBPAIS number(19,0),
        CODAMBPROVINCIA number(19,0),
        CODAMBITOTERRITORIALID number(19,0),
        CODCOMUNIDAD number(19,0),
        CODEDPPRINCIPAL varchar2(20 char),
        CODLOCALIDADID number(19,0),
        CODPAIS number(19,0),
        CODTIPOENTPUBLICA varchar2(2 char),
        CODTIPOUNIDAD varchar2(3 char),
        CODUNIDADRAIZ varchar2(20 char),
        CODUNIDADSUPERIOR varchar2(20 char),
        ESTADO varchar2(2 char),
        NIVELADMINISTRACION number(19,0),
        PODER number(19,0),
        TIPOVIA number(19,0)
    );

    create sequence DIR_CAMBTER_SEQ;

    create sequence DIR_CLOCA_SEQ;

    create sequence DIR_CODUO_SEQ;

    create sequence DIR_CONOF_SEQ;

    create sequence DIR_CONTUO_SEQ;

    create sequence DIR_HISOFI_SEQ;

    create sequence DIR_HISTUO_SEQ;

    create sequence DIR_NIFCIF_SEQ;

    create sequence DIR_RELOFI_SEQ;

    create sequence DIR_RELSIR_SEQ;

    create sequence DIR_SERVOFI_SEQ;

    create sequence DIR_SERVUO_SEQ;

    create sequence DIR_SINC_SEQ;


 -- INICI Indexes
    create index DIR_CAMBTER_CESTENT_FK_I on DIR_CATAMBITOTERRITORIAL (ESTADO);
    create index DIR_CAMBTER_CATNIVADM_FK_I on DIR_CATAMBITOTERRITORIAL (NIVELADMINISTRACION);
    create index DIR_CATAMBITOTERRITORIAL_PK_I on DIR_CATAMBITOTERRITORIAL (CODIGOAMBITO, NIVELADMINISTRACION);
    create index DIR_CCOMAUT_CESTENT_FK_I on DIR_CATCOMUNIDADAUTONOMA (ESTADO);
    create index DIR_CCOMAUT_CATPAIS_FK_I on DIR_CATCOMUNIDADAUTONOMA (PAIS);
    create index DIR_CENTGEO_CESTENT_FK_I on DIR_CATENTIDADGEOGRAFICA (ESTADO);
    create index DIR_CESTENT_CESTENT_FK_I on DIR_CATESTADOENTIDAD (ESTADO);
    create index DIR_CATISLA_CATPROV_FK_I on DIR_CATISLA (PROVINCIA);
    create index DIR_CATISLA_CESTENT_FK_I on DIR_CATISLA (ESTADO);
    create index DIR_CJEROFI_CESTENT_FK_I on DIR_CATJERARQUIAOFICINA (ESTADO);
    create index DIR_CATLOCAL_CATPROVIN_FK_I on DIR_CATLOCALIDAD (PROVINCIA);
    create index DIR_CATLOCAL_CESTENT_FK_I on DIR_CATLOCALIDAD (ESTADO);
    create index DIR_CATLOCAL_CATENTGEOGR_FK_I on DIR_CATLOCALIDAD (ENTIDADGEOGRAFICA);
    create index DIR_CMOTEXT_CESTENT_FK_I on DIR_CATMOTIVOEXTINCION (ESTADO);
    create index DIR_CNIVADM_CESTENT_FK_I on DIR_CATNIVELADMINISTRACION (ESTADO);
    create index DIR_CPAIS_CESTENT_FK_I on DIR_CATPAIS (ESTADO);
    create index DIR_CPODER_CESTENT_FK_I on DIR_CATPODER (ESTADO);
    create index DIR_CATPROV_CCOMAUT_FK_I on DIR_CATPROVINCIA (COMUNIDADAUTONOMA);
    create index DIR_CATPROVINCIA_PK_I on DIR_CATPROVINCIA (CODIGOPROVINCIA);
    create index DIR_CATPROV_CESTENT_FK_I on DIR_CATPROVINCIA (ESTADO);
    create index DIR_CSERVIC_CTIPSERV_FK_I on DIR_CATSERVICIO (TIPO);
    create index DIR_CSERVUO_CTIPSERV_FK_I on DIR_CATSERVICIOUO (TIPO);
    create index DIR_CFUEEXT_CESTENT_FK_I on DIR_CATTIPOCODFUENTEEXTERNA (ESTADO);
    create index DIR_CTIPCON_CESTENT_FK_I on DIR_CATTIPOCONTACTO (ESTADO);
    create index DIR_CTIENPU_CESTENT_FK_I on DIR_CATTIPOENTIDADPUBLICA (ESTADO);
    create index DIR_CTIUNOR_CESTENT_FK_I on DIR_CATTIPOUNIDADORGANICA (ESTADO);
    create index DIR_CTIPVIA_CESTENT_FK_I on DIR_CATTIPOVIA (ESTADO);
    create index DIR_CODUO_CESTENT_FK_I on DIR_CODIGOUO (ESTADO);
    create index DIR_CODUO_CFUEEXT_FK_I on DIR_CODIGOUO (TIPOCODIGO);
    create index DIR_CODUO_UNIDAD_FK_I on DIR_CODIGOUO (CODUNIDAD);
    create index DIR_CONOFI_CATTIPCONT_FK_I on DIR_CONTACTOOFI (TIPOCONTACTO);
    create index DIR_CONOFI_CESTENT_FK_I on DIR_CONTACTOOFI (ESTADO);
    create index DIR_CONOFI_OFICINA_FK_I on DIR_CONTACTOOFI (CODOFICINA);
    create index DIR_CONTUO_UNIDAD_FK_I on DIR_CONTACTOUO (CODUNIDAD);
    create index DIR_CONTUO_CESTENT_FK_I on DIR_CONTACTOUO (ESTADO);
    create index DIR_CONTUO_CATTIPOCONT_FK_I on DIR_CONTACTOUO (TIPOCONTACTO);
    create index DIR_HISOFI_CESTENT_FK_I on DIR_HISTORICOOFI (ESTADO);
    create index DIR_HISOFI_OFIULT_FK_I on DIR_HISTORICOOFI (CODULTIMA);
    create index DIR_HISOFI_OFIANT_FK_I on DIR_HISTORICOOFI (CODANTERIOR);
    create index DIR_HISTUO_UNIULT_FK_I on DIR_HISTORICOUO (CODULTIMA);
    create index DIR_HISTUO_CESTENT_FK_I on DIR_HISTORICOUO (ESTADO);
    create index DIR_HISTUO_UNIANT_FK_I on DIR_HISTORICOUO (CODANTERIOR);
    create index DIR_NIFCIF_UNIDAD_FK_I on DIR_NIFCIFUO (CODUNIDAD);
    create index DIR_NIFCIF_CESTENT_FK_I on DIR_NIFCIFUO (ESTADO);
    create index DIR_OFICINA_OFICINA_FK_I on DIR_OFICINA (CODOFIRESPONSABLE);
    create index DIR_OFICINA_CATTIPOVIA_FK_I on DIR_OFICINA (TIPOVIA);
    create index DIR_OFICINA_CATPAIS_FK_I on DIR_OFICINA (CODPAIS);
    create index DIR_OFICINA_CATLOCAL_FK_I on DIR_OFICINA (LOCALIDADID);
    create index DIR_OFICINA_CATNIVELADMIN_FK_I on DIR_OFICINA (NIVELADMINISTRACION);
    create index DIR_OFICINA_UNIDAD_FK_I on DIR_OFICINA (CODUORESPONSABLE);
    create index DIR_OFICINA_CATESTENT_FK_I on DIR_OFICINA (ESTADO);
    create index DIR_OFICINA_CATCOMUNIAUT_FK_I on DIR_OFICINA (CODCOMUNIDAD);
    create index DIR_OFICINA_CATJERAROFI_FK_I on DIR_OFICINA (TIPOOFICINA);
    create index DIR_OFICINA_CFUEXT_FK_I on DIR_OFICINA (FUENTEEXTERNA);
    create index DIR_RELORGOFI_OFICINA_FK_I on DIR_RELACIONORGANIZATIVAOFI (CODOFICINA);
    create index DIR_RELORGANOFI_CATESTENT_FK_I on DIR_RELACIONORGANIZATIVAOFI (ESTADO);
    create index DIR_RELORGOFI_UNIDAD_FK_I on DIR_RELACIONORGANIZATIVAOFI (CODUNIDAD);
    create index DIR_RELSIROFI_UNIDAD_FK_I on DIR_RELACIONSIROFI (CODUNIDAD);
    create index DIR_RELSIROFI_OFICINA_FK_I on DIR_RELACIONSIROFI (CODOFICINA);
    create index DIR_RELSIROFI_CATESTENTI_FK_I on DIR_RELACIONSIROFI (ESTADO);
    create index DIR_SERVOFI_CESTENT_FK_I on DIR_SERVICIOOFI (ESTADO);
    create index DIR_SERVOFI_OFICINA_FK_I on DIR_SERVICIOOFI (CODOFICINA);
    create index DIR_SERVUO_CESTENT_FK_I on DIR_SERVICIOUO (ESTADO);
    create index DIR_SERVUO_UNIDAD_FK_I on DIR_SERVICIOUO (CODUNIDAD);
    create index DIR_UNIDAD_CATAMBITTERR_FK_I on DIR_UNIDAD (CODAMBITOTERRITORIALID);
    create index DIR_UNIDAD_CATTIPOVIA_FK_I on DIR_UNIDAD (TIPOVIA);
    create index DIR_UNIDAD_UNIDADRAIZ_FK_I on DIR_UNIDAD (CODUNIDADRAIZ);
    create index DIR_UNIDAD_CATAMBPAIS_FK_I on DIR_UNIDAD (CODAMBPAIS);
    create index DIR_UNIDAD_MUNICIPIO_FK_I on DIR_UNIDAD (CODAMBLOCALIDADID);
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
    create index DIR_UNIDAD_CATLOCAL_FK_I on DIR_UNIDAD (CODLOCALIDADID);
    create index DIR_UNIDAD_UNIDADSUPERIOR_FK_I on DIR_UNIDAD (CODUNIDADSUPERIOR);
    create index DIR_UNIDAD_CATESTENTIDAD_FK_I on DIR_UNIDAD (ESTADO);
 -- FINAL Indexes

 -- INICI PK's
    alter table DIR_CATAMBITOTERRITORIAL add constraint DIR_CATAMBITOTERRITORIAL_pk primary key (ID);

    alter table DIR_CATCOMUNIDADAUTONOMA add constraint DIR_CATCOMUNIDADAUTONOMA_pk primary key (CODIGOCOMUNIDAD);

    alter table DIR_CATENTIDADGEOGRAFICA add constraint DIR_CATENTIDADGEOGRAFICA_pk primary key (CODIGOENTIDADGEOGRAFICA);

    alter table DIR_CATESTADOENTIDAD add constraint DIR_CATESTADOENTIDAD_pk primary key (CODIGOESTADOENTIDAD);

    alter table DIR_CATISLA add constraint DIR_CATISLA_pk primary key (CODIGOISLA);

    alter table DIR_CATJERARQUIAOFICINA add constraint DIR_CATJERARQUIAOFICINA_pk primary key (CODIGOJERARQUIAOFICINA);

    alter table DIR_CATLOCALIDAD add constraint DIR_CATLOCALIDAD_pk primary key (ID);

    alter table DIR_CATMOTIVOEXTINCION add constraint DIR_CATMOTIVOEXTINCION_pk primary key (CODIGOMOTIVOEXTINCION);

    alter table DIR_CATNIVELADMINISTRACION add constraint DIR_CATNIVELADMINISTRACION_pk primary key (CODIGONIVELADMINISTRACION);

    alter table DIR_CATPAIS add constraint DIR_CATPAIS_pk primary key (CODIGOPAIS);

    alter table DIR_CATPODER add constraint DIR_CATPODER_pk primary key (CODIGOPODER);

    alter table DIR_CATPROVINCIA add constraint DIR_CATPROVINCIA_pk primary key (CODIGOPROVINCIA);

    alter table DIR_CATSERVICIO add constraint DIR_CATSERVICIO_pk primary key (CODSERVICIO);

    alter table DIR_CATSERVICIOUO add constraint DIR_CATSERVICIOUO_pk primary key (CODSERVICIO);

    alter table DIR_CATTIPOCODFUENTEEXTERNA add constraint DIR_CATTIPOCODFUENTEEXTERNA_pk primary key (CODIGOTIPCODFUENTEEXTERNA);

    alter table DIR_CATTIPOCONTACTO add constraint DIR_CATTIPOCONTACTO_pk primary key (CODIGOTIPOCONTACTO);

    alter table DIR_CATTIPOENTIDADPUBLICA add constraint DIR_CATTIPOENTIDADPUBLICA_pk primary key (CODIGOTIPOENTIDADPUBLICA);

    alter table DIR_CATTIPOSERVICIO add constraint DIR_CATTIPOSERVICIO_pk primary key (CODIGOTIPOSERVICIO);

    alter table DIR_CATTIPOUNIDADORGANICA add constraint DIR_CATTIPOUNIDADORGANICA_pk primary key (CODIGOTIPOUNIDADORGANICA);

    alter table DIR_CATTIPOVIA add constraint DIR_CATTIPOVIA_pk primary key (CODIGOTIPOVIA);

    alter table DIR_CODIGOUO add constraint DIR_CODIGOUO_pk primary key (CODUNIDADORGANICA);

    alter table DIR_CONTACTOOFI add constraint DIR_CONTACTOOFI_pk primary key (CODCONTACTO);

    alter table DIR_CONTACTOUO add constraint DIR_CONTACTOUO_pk primary key (CODCONTACTO);

    alter table DIR_HISTORICOOFI add constraint DIR_HISTORICOOFI_pk primary key (ID);

    alter table DIR_HISTORICOUO add constraint DIR_HISTORICOUO_pk primary key (ID);

    alter table DIR_NIFCIFUO add constraint DIR_NIFCIFUO_pk primary key (ID);

    alter table DIR_OFICINA add constraint DIR_OFICINA_pk primary key (CODIGO);

    alter table DIR_RELACIONORGANIZATIVAOFI add constraint DIR_RELACIONORGANIZATIVAOFI_pk primary key (ID);

    alter table DIR_RELACIONSIROFI add constraint DIR_RELACIONSIROFI_pk primary key (ID);

    alter table DIR_SERVICIOOFI add constraint DIR_SERVICIOOFI_pk primary key (ID);

    alter table DIR_SERVICIOUO add constraint DIR_SERVICIOUO_pk primary key (ID);

    alter table DIR_SINCRONIZACION add constraint DIR_SINCRONIZACION_pk primary key (CODIGO);

    alter table DIR_UNIDAD add constraint DIR_UNIDAD_pk primary key (CODIGO);

 -- FINAL PK's

 -- INICI FK's

    alter table DIR_CATAMBITOTERRITORIAL 
        add constraint DIR_CAMBTER_CATNIVADM_FK 
        foreign key (NIVELADMINISTRACION) 
        references DIR_CATNIVELADMINISTRACION;

    alter table DIR_CATAMBITOTERRITORIAL 
        add constraint DIR_CAMBTER_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_CATCOMUNIDADAUTONOMA 
        add constraint DIR_CCOMAUT_CATPAIS_FK 
        foreign key (PAIS) 
        references DIR_CATPAIS;

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
        add constraint DIR_CATISLA_CATPROV_FK 
        foreign key (PROVINCIA) 
        references DIR_CATPROVINCIA;

    alter table DIR_CATISLA 
        add constraint DIR_CATISLA_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_CATJERARQUIAOFICINA 
        add constraint DIR_CJEROFI_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_CATLOCALIDAD 
        add constraint DIR_CATLOCAL_CATPROVIN_FK 
        foreign key (PROVINCIA) 
        references DIR_CATPROVINCIA;

    alter table DIR_CATLOCALIDAD 
        add constraint DIR_CATLOCAL_CATENTGEOGR_FK 
        foreign key (ENTIDADGEOGRAFICA) 
        references DIR_CATENTIDADGEOGRAFICA;

    alter table DIR_CATLOCALIDAD 
        add constraint DIR_CATLOCAL_CESTENT_FK 
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

    alter table DIR_CATPODER 
        add constraint DIR_CPODER_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_CATPROVINCIA 
        add constraint DIR_CATPROV_CCOMAUT_FK 
        foreign key (COMUNIDADAUTONOMA) 
        references DIR_CATCOMUNIDADAUTONOMA;

    alter table DIR_CATPROVINCIA 
        add constraint DIR_CATPROV_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_CATSERVICIO 
        add constraint DIR_CSERVIC_CTIPSERV_FK 
        foreign key (TIPO) 
        references DIR_CATTIPOSERVICIO;

    alter table DIR_CATSERVICIOUO 
        add constraint DIR_CSERVUO_CTIPSERV_FK 
        foreign key (TIPO) 
        references DIR_CATTIPOSERVICIO;

    alter table DIR_CATTIPOCODFUENTEEXTERNA 
        add constraint DIR_CFUEEXT_CESTENT_FK 
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

    alter table DIR_CODIGOUO 
        add constraint DIR_CODUO_CFUEEXT_FK 
        foreign key (TIPOCODIGO) 
        references DIR_CATTIPOCODFUENTEEXTERNA;

    alter table DIR_CODIGOUO 
        add constraint DIR_CODUO_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_CODIGOUO 
        add constraint DIR_UNIDAD_CODUO_FK 
        foreign key (CODUNIDAD) 
        references DIR_UNIDAD;

    alter table DIR_CONTACTOOFI 
        add constraint DIR_CONOFI_CATTIPCONT_FK 
        foreign key (TIPOCONTACTO) 
        references DIR_CATTIPOCONTACTO;

    alter table DIR_CONTACTOOFI 
        add constraint DIR_CONOFI_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_CONTACTOOFI 
        add constraint DIR_OFICINA_CONTACTOSOFI_FK 
        foreign key (CODOFICINA) 
        references DIR_OFICINA;

    alter table DIR_CONTACTOUO 
        add constraint DIR_CONTUO_CATTIPOCONT_FK 
        foreign key (TIPOCONTACTO) 
        references DIR_CATTIPOCONTACTO;

    alter table DIR_CONTACTOUO 
        add constraint DIR_CONTUO_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_CONTACTOUO 
        add constraint DIR_UNIDAD_CONTACTOSUO_FK 
        foreign key (CODUNIDAD) 
        references DIR_UNIDAD;

    alter table DIR_HISTORICOOFI 
        add constraint DIR_HISOFI_OFIANT_FK 
        foreign key (CODANTERIOR) 
        references DIR_OFICINA;

    alter table DIR_HISTORICOOFI 
        add constraint DIR_HISOFI_OFIULT_FK 
        foreign key (CODULTIMA) 
        references DIR_OFICINA;

    alter table DIR_HISTORICOOFI 
        add constraint DIR_HISOFI_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_HISTORICOUO 
        add constraint DIR_HISTUO_UNIANT_FK 
        foreign key (CODANTERIOR) 
        references DIR_UNIDAD;

    alter table DIR_HISTORICOUO 
        add constraint DIR_HISTUO_UNIULT_FK 
        foreign key (CODULTIMA) 
        references DIR_UNIDAD;

    alter table DIR_HISTORICOUO 
        add constraint DIR_HISTUO_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_NIFCIFUO 
        add constraint DIR_NIFCIF_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_NIFCIFUO 
        add constraint DIR_UNIDAD_NIFCIF_FK 
        foreign key (CODUNIDAD) 
        references DIR_UNIDAD;

    alter table DIR_OFICINA 
        add constraint DIR_OFICINA_CATTIPOVIA_FK 
        foreign key (TIPOVIA) 
        references DIR_CATTIPOVIA;

    alter table DIR_OFICINA 
        add constraint DIR_OFICINA_CATLOCAL_FK 
        foreign key (LOCALIDADID) 
        references DIR_CATLOCALIDAD;

    alter table DIR_OFICINA 
        add constraint DIR_OFICINA_CATPAIS_FK 
        foreign key (CODPAIS) 
        references DIR_CATPAIS;

    alter table DIR_OFICINA 
        add constraint DIR_OFICINA_OFICINA_FK 
        foreign key (CODOFIRESPONSABLE) 
        references DIR_OFICINA;

    alter table DIR_OFICINA 
        add constraint DIR_OFICINA_CATJERAROFI_FK 
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
        add constraint DIR_OFICINA_CATESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_OFICINA 
        add constraint DIR_OFICINA_UNIDAD_FK 
        foreign key (CODUORESPONSABLE) 
        references DIR_UNIDAD;

    alter table DIR_RELACIONORGANIZATIVAOFI 
        add constraint DIR_RELORGANOFI_CATESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_RELACIONORGANIZATIVAOFI 
        add constraint DIR_RELORGOFI_UNIDAD_FK 
        foreign key (CODUNIDAD) 
        references DIR_UNIDAD;

    alter table DIR_RELACIONORGANIZATIVAOFI 
        add constraint DIR_RELORGOFI_OFICINA_FK 
        foreign key (CODOFICINA) 
        references DIR_OFICINA;

    alter table DIR_RELACIONSIROFI 
        add constraint DIR_RELSIROFI_CATESTENTI_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_RELACIONSIROFI 
        add constraint DIR_RELSIROFI_UNIDAD_FK 
        foreign key (CODUNIDAD) 
        references DIR_UNIDAD;

    alter table DIR_RELACIONSIROFI 
        add constraint DIR_RELSIROFI_OFICINA_FK 
        foreign key (CODOFICINA) 
        references DIR_OFICINA;

    alter table DIR_SERVICIOOFI 
        add constraint DIR_SERVOFI_CATSERVOFI_FK 
        foreign key (CODSERVICIO) 
        references DIR_CATSERVICIO;

    alter table DIR_SERVICIOOFI 
        add constraint DIR_SERVOFI_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_SERVICIOOFI 
        add constraint DIR_SERVOFI_OFICINA_FK 
        foreign key (CODOFICINA) 
        references DIR_OFICINA;

    alter table DIR_SERVICIOUO 
        add constraint DIR_SERVUO_CATSERVUO_FK 
        foreign key (CODSERVICIO) 
        references DIR_CATSERVICIOUO;

    alter table DIR_SERVICIOUO 
        add constraint DIR_SERVUO_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_SERVICIOUO 
        add constraint DIR_SERVUO_UNIDAD_FK 
        foreign key (CODUNIDAD) 
        references DIR_UNIDAD;

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
        add constraint DIR_UNIDAD_CATAMBITTERR_FK 
        foreign key (CODAMBITOTERRITORIALID) 
        references DIR_CATAMBITOTERRITORIAL;

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
        add constraint DIR_UNIDAD_CATTIPENTPUBL_FK 
        foreign key (CODTIPOENTPUBLICA) 
        references DIR_CATTIPOENTIDADPUBLICA;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_MUNICIPIO_FK 
        foreign key (CODAMBLOCALIDADID) 
        references DIR_CATLOCALIDAD;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_CATISLA_FK 
        foreign key (CODAMBISLA) 
        references DIR_CATISLA;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_CATTIPUNIORG_FK_I 
        foreign key (CODTIPOUNIDAD) 
        references DIR_CATTIPOUNIDADORGANICA;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_CATLOCAL_FK 
        foreign key (CODLOCALIDADID) 
        references DIR_CATLOCALIDAD;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_CATNIVELADMIN_FK 
        foreign key (NIVELADMINISTRACION) 
        references DIR_CATNIVELADMINISTRACION;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_CATESTENTIDAD_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_CATPODER_FK 
        foreign key (PODER) 
        references DIR_CATPODER;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_UNIDADEDPPRINC_FK 
        foreign key (CODEDPPRINCIPAL) 
        references DIR_UNIDAD;
 -- FINAL FK's

 -- INICI UNIQUES
    alter table DIR_CATAMBITOTERRITORIAL add constraint DIR_CATAMBTER_CODAMB_NIVADM_UK unique (CODIGOAMBITO, NIVELADMINISTRACION);
    alter table DIR_CATLOCALIDAD add constraint DIR_CATLOC_LOC_PRO_ENTGEO_UK unique (CODIGOLOCALIDAD, PROVINCIA, ENTIDADGEOGRAFICA);
    alter table DIR_RELACIONORGANIZATIVAOFI add constraint DIR_RELORG_CODOFI_CODUNI_UK unique (CODOFICINA, CODUNIDAD);
    alter table DIR_RELACIONSIROFI add constraint DIR_RELSIROFI_OFI_UNI_UK unique (CODOFICINA, CODUNIDAD);
    alter table DIR_UNIDAD add constraint DIR_UNIDAD_COD_VER_UK unique (CODIGO, VERSION);
 -- FINAL UNIQUES

