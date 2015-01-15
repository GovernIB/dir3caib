@echo off
echo
echo Parametres Opcionals:
echo          -Dcaib : Compila amb les opcions de seguretat per WS de la CAIB
echo  

cmd /C mvn -DskipTests %* install

if %errorlevel% EQU 0 (

	@echo off
	IF DEFINED DIR3CAIB_DEPLOY_DIR (
      for /f "tokens=* delims=" %%x in (versio.txt) do set DIR3CAIB_VERSIO=%%x
	  @echo on
	  echo --------- COPIANT EAR %DIR3CAIB_VERSIO% ---------

	  xcopy /Y ear\target\dir3caib.ear %DIR3CAIB_DEPLOY_DIR%

	) ELSE (
	  echo  =================================================================
	  echo    Definex la variable d'entorn DIR3CAIB_DEPLOY_DIR apuntant al
	  echo    directori de deploy del JBOSS  i automaticament s'hi copiara
	  echo    l'ear generat.
	  echo  =================================================================
	) 

)