

DIR3CAIB_VERSIO=`cat versio.txt`

echo ]]$DIR3CAIB_VERSIO[[

zip -r dir3caib$1-$DIR3CAIB_VERSIO.zip scripts doc/pdf versio.txt ear/target/dir3caib.ear ws/dir3caib-api/target/dir3caib-ws-api-*.jar -x "**/.svn**"  -x "scripts/sqlgenenerator/**" -x "scripts/pom.xml"
