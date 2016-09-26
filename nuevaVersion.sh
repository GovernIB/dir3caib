#!/bin/bash


env mvn -DgroupId=es.caib.dir3caib -DartifactId=* versions:set -DnewVersion=$@
