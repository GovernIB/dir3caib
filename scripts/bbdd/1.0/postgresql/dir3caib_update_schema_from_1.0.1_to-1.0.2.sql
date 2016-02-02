ALTER TABLE dir_descarga ADD COLUMN estado varchar(2);

UPDATE dir_descarga set estado='01';