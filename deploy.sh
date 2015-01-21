#!/usr/bin/env bash

env mvn $@ -DskipTests install 

if [ $? == 0 ]; then
  if [ "$DIR3CAIB_DEPLOY_DIR" == "" ];  then
    echo  =================================================================
    echo    Definex la variable d\'entorn DIR3CAIB_DEPLOY_DIR apuntant al
    echo    directori de deploy del JBOSS  i automaticament s\'hi copiara
    echo    l\'ear generat.
    echo  =================================================================  
  else
    if [ -f 'versio.txt' ]; then
	echo --------- COPIANT EAR `cat versio.txt` ---------
    else
	echo --------- COPIANT EAR ---------
    fi
    if [ -f './ear/target/dir3caib.ear' ]; then
      cp ./ear/target/dir3caib.ear $DIR3CAIB_DEPLOY_DIR
    else
      echo NO S\'HA TROBAT dir3caib.ear!
    fi
  fi
fi
