ALTER TABLE dir_descarga ADD estado varchar2(2 char);

UPDATE dir_descarga set estado='01';