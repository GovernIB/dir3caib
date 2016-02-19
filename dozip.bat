
for /f "tokens=* delims=" %%x in (versio.txt) do set DIR3CAIB_VERSIO=%%x

zip -r dir3caib%1-%DIR3CAIB_VERSIO%.zip scripts doc/pdf versio.txt ear/target/dir3caib.ear ws/dir3caib-api/target/dir3caib-ws-api-*.jar ws/dir3-api/target/dir3caib-ws-dir3api-*.jar -x "**/.svn**"  -x "./scripts/sqlgenerator/**" -x "./scripts/pom.xml" -x "./scripts/templates/**" -x "./scripts/sqlgenerator/" -x "./scripts/templates/"