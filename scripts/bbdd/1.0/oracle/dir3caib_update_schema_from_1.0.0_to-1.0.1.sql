--20/10/2015 cambiar fechaimportacion de date a timestamp. (ORACLE)
--Pendiente de aplicar en la APB Y CAIB
ALTER TABLE dir_unidad MODIFY fechaimportacion TIMESTAMP;
ALTER TABLE dir_oficina MODIFY fechaimportacion TIMESTAMP;