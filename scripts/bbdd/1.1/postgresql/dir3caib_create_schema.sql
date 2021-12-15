
    create table DIR_CATAMBITOTERRITORIAL (
        ID int8 not null,
        CODIGOAMBITO varchar(2) not null,
        DESCRIPCIONAMBITO varchar(30) not null,
        ESTADO varchar(2),
        NIVELADMINISTRACION int8,
        primary key (ID),
        unique (CODIGOAMBITO, NIVELADMINISTRACION)
    );

    create table DIR_CATCOMUNIDADAUTONOMA (
        CODIGOCOMUNIDAD int8 not null,
        C_CODIGO_DIR2 int8,
        C_COMUNIDAD_RPC varchar(2),
        DESCRIPCIONCOMUNIDAD varchar(50) not null,
        ESTADO varchar(2),
        PAIS int8,
        primary key (CODIGOCOMUNIDAD)
    );

    create table DIR_CATENTIDADGEOGRAFICA (
        CODIGOENTIDADGEOGRAFICA varchar(2) not null,
        DESCRIPCIONENTIDADGEOGRAFICA varchar(50) not null,
        ESTADO varchar(2),
        primary key (CODIGOENTIDADGEOGRAFICA)
    );

    create table DIR_CATESTADOENTIDAD (
        CODIGOESTADOENTIDAD varchar(2) not null,
        DESCRIPCIONESTADOENTIDAD varchar(50) not null,
        ESTADO varchar(2),
        primary key (CODIGOESTADOENTIDAD)
    );

    create table DIR_CATISLA (
        CODIGOISLA int8 not null,
        DESCRIPCIONISLA varchar(50) not null,
        ESTADO varchar(2),
        PROVINCIA int8,
        primary key (CODIGOISLA)
    );

    create table DIR_CATJERARQUIAOFICINA (
        CODIGOJERARQUIAOFICINA int8 not null,
        DESCRIPCIONJERARQUIAOFICINA varchar(50) not null,
        ESTADO varchar(2),
        primary key (CODIGOJERARQUIAOFICINA)
    );

    create table DIR_CATLOCALIDAD (
        ID int8 not null,
        CODIGOLOCALIDAD int8 not null,
        DESCRIPCIONLOCALIDAD varchar(50),
        ENTIDADGEOGRAFICA varchar(2),
        ESTADO varchar(2),
        PROVINCIA int8,
        primary key (ID),
        unique (CODIGOLOCALIDAD, PROVINCIA, ENTIDADGEOGRAFICA)
    );

    create table DIR_CATMOTIVOEXTINCION (
        CODIGOMOTIVOEXTINCION varchar(3) not null,
        DESCRIPCIONMOTIVOEXTINCION varchar(400) not null,
        ESTADO varchar(2),
        primary key (CODIGOMOTIVOEXTINCION)
    );

    create table DIR_CATNIVELADMINISTRACION (
        CODIGONIVELADMINISTRACION int8 not null,
        DESCRIPCIONNIVELADMINISTRACION varchar(300) not null,
        ESTADO varchar(2),
        primary key (CODIGONIVELADMINISTRACION)
    );

    create table DIR_CATPAIS (
        CODIGOPAIS int8 not null,
        ALFA2PAIS varchar(2),
        ALFA3PAIS varchar(3),
        DESCRIPCIONPAIS varchar(100) not null,
        ESTADO varchar(2),
        primary key (CODIGOPAIS)
    );

    create table DIR_CATPODER (
        CODIGOPODER int8 not null,
        DESCRIPCIONPODER varchar(50) not null,
        ESTADO varchar(2),
        primary key (CODIGOPODER)
    );

    create table DIR_CATPROVINCIA (
        CODIGOPROVINCIA int8 not null,
        DESCRIPCIONPROVINCIA varchar(50) not null,
        COMUNIDADAUTONOMA int8,
        ESTADO varchar(2),
        primary key (CODIGOPROVINCIA)
    );

    create table DIR_CATSERVICIO (
        CODSERVICIO int8 not null,
        DESCSERVICIO varchar(300) not null,
        TIPO int8,
        primary key (CODSERVICIO)
    );

    create table DIR_CATTIPOCODFUENTEEXTERNA (
        CODIGOTIPCODFUENTEEXTERNA int8 not null,
        DESCRIPCIONTIPCODFUENTEEXTERNA varchar(30) not null,
        ESTADO varchar(2),
        primary key (CODIGOTIPCODFUENTEEXTERNA)
    );

    create table DIR_CATTIPOCONTACTO (
        CODIGOTIPOCONTACTO varchar(2) not null,
        DESCRIPCIONTIPOCONTACTO varchar(30) not null,
        ESTADO varchar(2),
        primary key (CODIGOTIPOCONTACTO)
    );

    create table DIR_CATTIPOENTIDADPUBLICA (
        CODIGOTIPOENTIDADPUBLICA varchar(2) not null,
        DESCRIPCIONTIPOENTIDADPUBLICA varchar(100) not null,
        ESTADO varchar(2),
        primary key (CODIGOTIPOENTIDADPUBLICA)
    );

    create table DIR_CATTIPOSERVICIO (
        CODIGOTIPOSERVICIO int8 not null,
        DESCRIPCIONTIPOSERVICIO varchar(300) not null,
        primary key (CODIGOTIPOSERVICIO)
    );

    create table DIR_CATTIPOUNIDADORGANICA (
        CODIGOTIPOUNIDADORGANICA varchar(3) not null,
        DESCRIPCIONTIPOUNIDADORGANICA varchar(100) not null,
        ESTADO varchar(2),
        primary key (CODIGOTIPOUNIDADORGANICA)
    );

    create table DIR_CATTIPOVIA (
        CODIGOTIPOVIA int8 not null,
        DESCRIPCIONTIPOVIA varchar(50) not null,
        ESTADO varchar(2),
        primary key (CODIGOTIPOVIA)
    );

    create table DIR_CONTACTOOFI (
        CODCONTACTO int8 not null,
        VALORCONTACTO varchar(100) not null,
        VISIBILIDAD bool,
        CODOFICINA varchar(9),
        TIPOCONTACTO varchar(2),
        primary key (CODCONTACTO)
    );

    create table DIR_CONTACTOUO (
        CODCONTACTO int8 not null,
        VALORCONTACTO varchar(100) not null,
        VISIBILIDAD bool,
        TIPOCONTACTO varchar(2),
        CODUNIDAD varchar(9),
        primary key (CODCONTACTO)
    );

    create table DIR_DESCARGA (
        CODIGO int8 not null,
        ESTADO varchar(2),
        FECHAFIN timestamp,
        FECHAIMPORTACION timestamp,
        FECHAINICIO timestamp,
        TIPO varchar(255),
        primary key (CODIGO)
    );

    create table DIR_HISTORICOOFI (
        CODANTERIOR varchar(9) not null,
        CODULTIMA varchar(9) not null,
        primary key (CODANTERIOR, CODULTIMA)
    );

    create table DIR_HISTORICOUO (
        CODANTERIOR varchar(9) not null,
        CODULTIMA varchar(9) not null,
        primary key (CODANTERIOR, CODULTIMA)
    );

    create table DIR_OFICINA (
        CODIGO varchar(9) not null,
        CODPOSTAL varchar(14),
        COMPLEMENTO varchar(300),
        DENOMINACION varchar(300),
        DIASSINHABILES varchar(400),
        DIREXTRANJERA varchar(200),
        DIRECCIONOBSERVACIONES varchar(400),
        FECHAALTAOFICIAL timestamp,
        FECHAANULACION timestamp,
        FECHAEXTINCION timestamp,
        FECHAIMPORTACION timestamp,
        HORARIOANTENCION varchar(400),
        LOCEXTRANJERA varchar(40),
        NOMBREVIA varchar(300),
        NUMVIA varchar(20),
        OBSERVACIONES varchar(400),
        CODCOMUNIDAD int8,
        CODOFIRESPONSABLE varchar(9),
        CODPAIS int8,
        CODUORESPONSABLE varchar(9),
        ESTADO varchar(2),
        LOCALIDADID int8,
        NIVELADMINISTRACION int8,
        TIPOOFICINA int8,
        TIPOVIA int8,
        primary key (CODIGO)
    );

    create table DIR_RELACIONORGANIZATIVAOFI (
        id int8 not null,
        ESTADO varchar(2),
        CODOFICINA varchar(9) not null,
        CODUNIDAD varchar(9) not null,
        primary key (id),
        unique (CODOFICINA, CODUNIDAD)
    );

    create table DIR_RELACIONSIROFI (
        ID int8 not null,
        ESTADO varchar(2),
        CODOFICINA varchar(9) not null,
        CODUNIDAD varchar(9) not null,
        primary key (ID),
        unique (CODOFICINA, CODUNIDAD)
    );

    create table DIR_SERVICIOOFI (
        CODOFICINA varchar(9) not null,
        CODSERVICIO int8 not null,
        primary key (CODOFICINA, CODSERVICIO)
    );

    create table DIR_SERVICIOUO (
        CODUNIDAD varchar(9) not null,
        CODSERVICIO int8 not null,
        primary key (CODUNIDAD, CODSERVICIO)
    );

    create table DIR_SINCRONIZACION (
        CODIGO int8 not null,
        ESTADO int8,
        FECHAFIN timestamp,
        FECHAIMPORTACION timestamp,
        FECHAINICIO timestamp,
        TIPO varchar(255),
        primary key (CODIGO)
    );

    create table DIR_UNIDAD (
        CODIGO varchar(9) not null,
        CODAMBELM int8,
        CODAMBLOCEXTRANJERA varchar(50),
        CODEXTERNO varchar(40),
        CODPOSTAL varchar(14),
        COMPETENCIAS varchar(400),
        COMPLEMENTO varchar(300),
        DENOMINACION varchar(300),
        DIREXTRANJERA varchar(200),
        DISPOSICIONLEGAL varchar(400),
        ESEDP bool,
        FECHAALTAOFICIAL timestamp,
        FECHAANULACION timestamp,
        FECHABAJAOFICIAL timestamp,
        FECHAEXTINCION timestamp,
        FECHAIMPORTACION timestamp,
        LOCEXTRANJERA varchar(40),
        NIFCIF varchar(9),
        NIVELJERARQUICO int8,
        NOMBREVIA varchar(300),
        NUMVIA varchar(20),
        OBSERVBAJA varchar(400),
        OBSERVGENERALES varchar(400),
        OBSERVACIONES varchar(400),
        SIGLAS varchar(10),
        CODAMBLOCALIDADID int8,
        CODAMBCOMUNIDAD int8,
        CODAMBENTGEOGRAFICA varchar(2),
        CODAMBISLA int8,
        CODAMBPAIS int8,
        CODAMBPROVINCIA int8,
        CODAMBITOTERRITORIALID int8,
        CODCOMUNIDAD int8,
        CODEDPPRINCIPAL varchar(9),
        CODLOCALIDADID int8,
        CODPAIS int8,
        CODTIPOENTPUBLICA varchar(2),
        CODTIPOUNIDAD varchar(3),
        CODUNIDADRAIZ varchar(9),
        CODUNIDADSUPERIOR varchar(9),
        ESTADO varchar(2),
        NIVELADMINISTRACION int8,
        TIPOVIA int8,
        primary key (CODIGO)
    );

    create index DIR_CAMBTER_CESTENT_FK_I on DIR_CATAMBITOTERRITORIAL (ESTADO);

    create index DIR_CATAMBTERR_CATNIVADM_FK_I on DIR_CATAMBITOTERRITORIAL (NIVELADMINISTRACION);

    create index DIR_CATAMBITOTERRITORIAL_PK_I on DIR_CATAMBITOTERRITORIAL (CODIGOAMBITO, NIVELADMINISTRACION);

    alter table DIR_CATAMBITOTERRITORIAL 
        add constraint DIR_CATAMBTERR_CATNIVADM_FK 
        foreign key (NIVELADMINISTRACION) 
        references DIR_CATNIVELADMINISTRACION;

    alter table DIR_CATAMBITOTERRITORIAL 
        add constraint DIR_CAMBTER_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    create index DIR_CCOMAUT_CESTENT_FK_I on DIR_CATCOMUNIDADAUTONOMA (ESTADO);

    create index DIR_CATCOMUNAUT_CATPAIS_FK_I on DIR_CATCOMUNIDADAUTONOMA (PAIS);

    alter table DIR_CATCOMUNIDADAUTONOMA 
        add constraint DIR_CATCOMUNAUT_CATPAIS_FK 
        foreign key (PAIS) 
        references DIR_CATPAIS;

    alter table DIR_CATCOMUNIDADAUTONOMA 
        add constraint DIR_CCOMAUT_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    create index DIR_CENTGEO_CESTENT_FK_I on DIR_CATENTIDADGEOGRAFICA (ESTADO);

    alter table DIR_CATENTIDADGEOGRAFICA 
        add constraint DIR_CENTGEO_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    create index DIR_CESTENT_CESTENT_FK_I on DIR_CATESTADOENTIDAD (ESTADO);

    alter table DIR_CATESTADOENTIDAD 
        add constraint DIR_CESTENT_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    create index DIR_CATISLA_CATPROV_FK_I on DIR_CATISLA (PROVINCIA);

    create index DIR_CATISLA_CESTENT_FK_I on DIR_CATISLA (ESTADO);

    alter table DIR_CATISLA 
        add constraint DIR_CATISLA_CATPROV_FK 
        foreign key (PROVINCIA) 
        references DIR_CATPROVINCIA;

    alter table DIR_CATISLA 
        add constraint DIR_CATISLA_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    create index DIR_CJEROFI_CESTENT_FK_I on DIR_CATJERARQUIAOFICINA (ESTADO);

    alter table DIR_CATJERARQUIAOFICINA 
        add constraint DIR_CJEROFI_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    create index DIR_CLOCAL_CESTENT_FK_I on DIR_CATLOCALIDAD (ESTADO);

    create index DIR_CATLOCAL_CATPROVIN_FK_I on DIR_CATLOCALIDAD (PROVINCIA);

    create index DIR_CATLOCAL_CATENTGEOGR_FK_I on DIR_CATLOCALIDAD (ENTIDADGEOGRAFICA);

    alter table DIR_CATLOCALIDAD 
        add constraint DIR_CATLOCAL_CATPROVIN_FK 
        foreign key (PROVINCIA) 
        references DIR_CATPROVINCIA;

    alter table DIR_CATLOCALIDAD 
        add constraint DIR_CATLOCAL_CATENTGEOGR_FK 
        foreign key (ENTIDADGEOGRAFICA) 
        references DIR_CATENTIDADGEOGRAFICA;

    alter table DIR_CATLOCALIDAD 
        add constraint DIR_CLOCAL_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    create index DIR_CMOTEXT_CESTENT_FK_I on DIR_CATMOTIVOEXTINCION (ESTADO);

    alter table DIR_CATMOTIVOEXTINCION 
        add constraint DIR_CMOTEXT_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    create index DIR_CNIVADM_CESTENT_FK_I on DIR_CATNIVELADMINISTRACION (ESTADO);

    alter table DIR_CATNIVELADMINISTRACION 
        add constraint DIR_CNIVADM_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    create index DIR_CPAIS_CESTENT_FK_I on DIR_CATPAIS (ESTADO);

    alter table DIR_CATPAIS 
        add constraint DIR_CPAIS_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    create index DIR_CPODER_CESTENT_FK_I on DIR_CATPODER (ESTADO);

    alter table DIR_CATPODER 
        add constraint DIR_CPODER_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    create index DIR_CATPROVINCIA_PK_I on DIR_CATPROVINCIA (CODIGOPROVINCIA);

    create index DIR_CATPROV_CATCOMUNAUT_FK_I on DIR_CATPROVINCIA (COMUNIDADAUTONOMA);

    create index DIR_CPROVIN_CESTENT_FK_I on DIR_CATPROVINCIA (ESTADO);

    alter table DIR_CATPROVINCIA 
        add constraint DIR_CATPROVINC_CATCOMUNAUTO_FK 
        foreign key (COMUNIDADAUTONOMA) 
        references DIR_CATCOMUNIDADAUTONOMA;

    alter table DIR_CATPROVINCIA 
        add constraint DIR_CPROVIN_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    create index DIR_CSERVIC_CTIPSERV_FK_I on DIR_CATSERVICIO (TIPO);

    alter table DIR_CATSERVICIO 
        add constraint DIR_CSERVIC_CTIPSERV_FK 
        foreign key (TIPO) 
        references DIR_CATTIPOSERVICIO;

    create index DIR_CFUEEXT_CESTENT_FK_I on DIR_CATTIPOCODFUENTEEXTERNA (ESTADO);

    alter table DIR_CATTIPOCODFUENTEEXTERNA 
        add constraint DIR_CFUEEXT_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    create index DIR_CTIPCON_CESTENT_FK_I on DIR_CATTIPOCONTACTO (ESTADO);

    alter table DIR_CATTIPOCONTACTO 
        add constraint DIR_CATTIPCON_CATESTENTIDAD_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    create index DIR_CTIENPU_CESTENT_FK_I on DIR_CATTIPOENTIDADPUBLICA (ESTADO);

    alter table DIR_CATTIPOENTIDADPUBLICA 
        add constraint DIR_CTIENPU_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    create index DIR_CTIUNOR_CESTENT_FK_I on DIR_CATTIPOUNIDADORGANICA (ESTADO);

    alter table DIR_CATTIPOUNIDADORGANICA 
        add constraint DIR_CTIUNOR_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    create index DIR_CTIPVIA_CESTENT_FK_I on DIR_CATTIPOVIA (ESTADO);

    alter table DIR_CATTIPOVIA 
        add constraint DIR_CTIPVIA_CESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    create index DIR_CONTOFI_CATTIPCONT_FK_I on DIR_CONTACTOOFI (TIPOCONTACTO);

    create index DIR_OFICINA_CONTACTOSOFI_FK_I on DIR_CONTACTOOFI (CODOFICINA);

    alter table DIR_CONTACTOOFI 
        add constraint DIR_CONTACOFI_CATTIPCONTAC_FK 
        foreign key (TIPOCONTACTO) 
        references DIR_CATTIPOCONTACTO;

    alter table DIR_CONTACTOOFI 
        add constraint DIR_OFICINA_CONTACTOSOFI_FK 
        foreign key (CODOFICINA) 
        references DIR_OFICINA;

    create index DIR_UNIDAD_CONTACTOSUO_FK_I on DIR_CONTACTOUO (CODUNIDAD);

    create index DIR_CONTACUO_CATTIPOCONT_FK_I on DIR_CONTACTOUO (TIPOCONTACTO);

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

    create index DIR_OFICINA_OFICINA_FK_I on DIR_OFICINA (CODOFIRESPONSABLE);

    create index DIR_OFICINA_CATTIPOVIA_FK_I on DIR_OFICINA (TIPOVIA);

    create index DIR_OFICINA_CATESTADENTI_FK_I on DIR_OFICINA (ESTADO);

    create index DIR_OFICINA_CATPAIS_FK_I on DIR_OFICINA (CODPAIS);

    create index DIR_OFICINA_CATLOCAL_FK_I on DIR_OFICINA (LOCALIDADID);

    create index DIR_OFICINA_CATNIVELADMIN_FK_I on DIR_OFICINA (NIVELADMINISTRACION);

    create index DIR_OFICINA_UNIDAD_FK_I on DIR_OFICINA (CODUORESPONSABLE);

    create index DIR_OFICINA_CATCOMUNIAUT_FK_I on DIR_OFICINA (CODCOMUNIDAD);

    create index DIR_OFICINA_CATJERAROFI_FK_I on DIR_OFICINA (TIPOOFICINA);

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
        add constraint DIR_OFICINA_UNIDAD_FK 
        foreign key (CODUORESPONSABLE) 
        references DIR_UNIDAD;

    create index DIR_RELORGANOFI_CATESTENT_FK_I on DIR_RELACIONORGANIZATIVAOFI (ESTADO);

    create index DIR_OFICINA_RELORGOFI_FK_I on DIR_RELACIONORGANIZATIVAOFI (CODOFICINA);

    create index DIR_UNIDAD_RELORGOFI_FK_I on DIR_RELACIONORGANIZATIVAOFI (CODUNIDAD);

    alter table DIR_RELACIONORGANIZATIVAOFI 
        add constraint DIR_RELORGANOFI_CATESTENT_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_RELACIONORGANIZATIVAOFI 
        add constraint DIR_RELORGOFI_CATUNIDAD_FK 
        foreign key (CODUNIDAD) 
        references DIR_UNIDAD;

    alter table DIR_RELACIONORGANIZATIVAOFI 
        add constraint DIR_RELORGOFI_CATOFI_FK 
        foreign key (CODOFICINA) 
        references DIR_OFICINA;

    create index DIR_OFICINA_RELSIROFI_FK_I on DIR_RELACIONSIROFI (CODOFICINA);

    create index DIR_UNIDAD_RELSIROFI_FK_I on DIR_RELACIONSIROFI (CODUNIDAD);

    create index DIR_RELSIROFI_CATESTENTI_FK_I on DIR_RELACIONSIROFI (ESTADO);

    alter table DIR_RELACIONSIROFI 
        add constraint DIR_RELSIROFI_CATESTENTI_FK 
        foreign key (ESTADO) 
        references DIR_CATESTADOENTIDAD;

    alter table DIR_RELACIONSIROFI 
        add constraint DIR_RELSIROFI_CATUNIDAD_FK 
        foreign key (CODUNIDAD) 
        references DIR_UNIDAD;

    alter table DIR_RELACIONSIROFI 
        add constraint DIR_RELSIROFI_CATOFI_FK 
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

    alter table DIR_SERVICIOUO 
        add constraint DIR_UNI_SERV_FK 
        foreign key (CODSERVICIO) 
        references DIR_CATSERVICIO;

    alter table DIR_SERVICIOUO 
        add constraint DIR_SER_UNI_FK 
        foreign key (CODUNIDAD) 
        references DIR_UNIDAD;

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
        add constraint DIR_UNIDAD_CATTIPENTPUBLICA_FK 
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
        add constraint DIR_UNIDAD_CATTIPUNIDORGAN_FK 
        foreign key (CODTIPOUNIDAD) 
        references DIR_CATTIPOUNIDADORGANICA;

    alter table DIR_UNIDAD 
        add constraint DIR_UNIDAD_LOCDIRECCION_FK 
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
        add constraint DIR_UNIDAD_UNIDADEDPPRINC_FK 
        foreign key (CODEDPPRINCIPAL) 
        references DIR_UNIDAD;

    create sequence DIR_CAMBTER_SEQ;

    create sequence DIR_CLOCA_SEQ;

    create sequence DIR_SEQ_ALL;
