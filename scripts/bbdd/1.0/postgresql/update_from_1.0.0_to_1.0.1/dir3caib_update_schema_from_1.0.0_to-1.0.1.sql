--20/10/2015 cambiar fechaimportacion de date a timestamp.
ALTER TABLE dir_unidad ALTER COLUMN fechaimportacion TYPE timestamp;
ALTER TABLE dir_oficina ALTER COLUMN fechaimportacion TYPE timestamp;