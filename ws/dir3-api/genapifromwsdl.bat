

wget http://pre-dir3ws.redsara.es/directorio/services/SC21CT_VolcadoCatalogos?wsdl  -O src/main/resources/wsdl/SC21CT_VolcadoCatalogos.wsdl
call wsconsume -k -s src/main/java -n -p es.caib.dir3caib.ws.dir3.catalogo.client http://pre-dir3ws.redsara.es/directorio/services/SC21CT_VolcadoCatalogos?wsdl


wget http://pre-dir3ws.redsara.es/directorio/services/SD02OF_DescargaOficinas?wsdl  -O src/main/resources/wsdl/SD02OF_DescargaOficinas.wsdl
call wsconsume -k -s src/main/java -n -p es.caib.dir3caib.ws.dir3.oficina.client http://pre-dir3ws.redsara.es/directorio/services/SD02OF_DescargaOficinas?wsdl


wget http://pre-dir3ws.redsara.es/directorio/services/SD01UN_DescargaUnidades?wsdl  -O src/main/resources/wsdl/SD02OF_DescargaUnidades.wsdl
call wsconsume -k -s src/main/java -n -p es.caib.dir3caib.ws.dir3.unidad.client http://pre-dir3ws.redsara.es/directorio/services/SD01UN_DescargaUnidades?wsdl

