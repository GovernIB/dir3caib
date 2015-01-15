
set THEHOST=localhost:8080

wget http://%THEHOST%/dir3caib/ws/Dir3CaibObtenerOficinas?wsdl  -O src/main/resources/wsdl/Dir3CaibObtenerOficinas.wsdl
call wsconsume -k -s src/main/java -n -p es.caib.dir3caib.ws.api.oficina http://%THEHOST%/dir3caib/ws/Dir3CaibObtenerOficinas?wsdl

wget http://%THEHOST%/dir3caib/ws/Dir3CaibObtenerCatalogos?wsdl  -O src/main/resources/wsdl/Dir3CaibObtenerCatalogos.wsdl
rem -w classpath:/wsdl/Dir3CaibObtenerCatalogos.wsdl
call wsconsume -k -s src/main/java -n -p es.caib.dir3caib.ws.api.catalogo http://%THEHOST%/dir3caib/ws/Dir3CaibObtenerCatalogos?wsdl

wget http://%THEHOST%/dir3caib/ws/Dir3CaibObtenerUnidades?wsdl  -O src/main/resources/wsdl/Dir3CaibObtenerUnidades.wsdl
call wsconsume -k -s src/main/java -n -p es.caib.dir3caib.ws.api.unidad http://%THEHOST%/dir3caib/ws/Dir3CaibObtenerUnidades?wsdl

