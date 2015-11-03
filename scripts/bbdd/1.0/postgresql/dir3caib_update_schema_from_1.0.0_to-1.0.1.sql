--20/10/2015 cambiar fechaimportacion de date a timestamp. Debemos versionar a la 1.0.1
--(PENDIENTE DE APLICAR EN PORTS Y CAIB)
ALTER TABLE dir_unidad ALTER COLUMN fechaimportacion TYPE timestamp;
ALTER TABLE dir_oficina ALTER COLUMN fechaimportacion TYPE timestamp;