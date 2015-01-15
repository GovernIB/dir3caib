rem start /WAIT 
cmd /C mvn -DskipTests %* -Dcaib clean install

echo ELIMINANDO TEMPORALES JBOSS
rd /q /s D:\dades\dades\SICRES3\jboss-5.2.0-caibcompleto\server\default\tmp
rd /q /s D:\dades\dades\SICRES3\jboss-5.2.0-caibcompleto\server\default\log
rd /q /s D:\dades\dades\SICRES3\jboss-5.2.0-caibcompleto\server\default\work

echo COPIANT EAR
xcopy /Y ear\target\dir3caib.ear D:\dades\dades\SICRES3\jboss-5.2.0-caibcompleto\server\default\deploy

