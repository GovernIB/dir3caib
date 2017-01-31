--Campos de tablas de relaciones a NOT NULL
alter TABLE dir_relacionorganizativaofi ALTER COLUMN codoficina SET not null;
alter TABLE dir_relacionorganizativaofi ALTER COLUMN codunidad SET not null;

alter TABLE dir_relacionsirofi ALTER COLUMN codoficina SET not null;
alter TABLE dir_relacionsirofi ALTER COLUMN codunidad SET not null;