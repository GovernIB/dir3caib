update DIR_DESCARGA set estado='5' where estado='01' and fechaimportacion is not null;
update DIR_DESCARGA set estado='4' where estado='01' and fechaimportacion is null;
update DIR_DESCARGA set estado='2' where estado='14';