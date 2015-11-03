

--16/03/2015  Descarga fechas pasadas a Date.

alter table DIR_DESCARGA ADD FECHAINICIO2 TIMESTAMP(6);
alter table DIR_DESCARGA ADD FECHAFIN2 TIMESTAMP(6);
alter table DIR_DESCARGA ADD FECHAIMPORTACION2 TIMESTAMP(6);


UPDATE DIR_DESCARGA SET FECHAINICIO2 = TO_DATE(FECHAINICIO, 'dd/mm/yyyy') where FECHAINICIO is not null ;
UPDATE DIR_DESCARGA SET FECHAFIN2 = TO_DATE(FECHAFIN, 'dd/mm/yyyy') where FECHAFIN is not null;
UPDATE DIR_DESCARGA SET FECHAIMPORTACION2 = TO_DATE(FECHAIMPORTACION, 'dd/mm/yyyy') where FECHAIMPORTACION is not null;

alter table DIR_DESCARGA DROP COLUMN FECHAINICIO;
alter table DIR_DESCARGA DROP COLUMN FECHAFIN;
alter table DIR_DESCARGA DROP COLUMN FECHAIMPORTACION;

alter table DIR_DESCARGA RENAME COLUMN FECHAINICIO2 to FECHAINICIO;
alter table DIR_DESCARGA RENAME COLUMN FECHAFIN2 to FECHAFIN;
alter table DIR_DESCARGA RENAME COLUMN FECHAIMPORTACION2 to FECHAIMPORTACION;


-- 26/03/2015 Index amb nom incorrecte

drop index RWE_CATPROVINCIA_PK_I;
create index DIR_CATPROVINCIA_PK_I on DIR_CATPROVINCIA (CODIGOPROVINCIA);

-- 23/04/2015 Ampliació longitud camps (ve de dir3 madrid)   (POSTGRESQL)
ALTER TABLE dir_cattipoentidadpublica alter column descripciontipoentidadpublica type character varying(100);
ALTER TABLE dir_catpais  alter column descripcionpais type character varying(100);

-- 23/04/2015 Ampliació longitud camps (ve de dir3 madrid)     (ORACLE)
ALTER TABLE dir_cattipoentidadpublica MODIFY descripciontipoentidadpublica VARCHAR2(100 CHAR);
ALTER TABLE dir_catpais MODIFY descripcionpais VARCHAR2(100 CHAR);

--(PENDIENTE DE APLICAR EN PORTS Y CAIB)
--20/10/2015 cambiar fechaimportacion de date a timestamp. Pendiente de aplicar y ver si versionamos(ORACLE)
ALTER TABLE dir_unidad MODIFY fechaimportacion TIMESTAMP;
ALTER TABLE dir_oficina MODIFY fechaimportacion TIMESTAMP;


--20/10/2015 cambiar fechaimportacion de date a timestamp. Pendiente de aplicar y ver si versionamos(POSTGRESQL)
ALTER TABLE dir_unidad ALTER COLUMN fechaimportacion TYPE timestamp;
ALTER TABLE dir_oficina ALTER COLUMN fechaimportacion TYPE timestamp;







