
for /f "tokens=* delims=" %%x in (versio.txt) do set DIR3CAIB_VERSIO=%%x

zip -r dir3caib%1-%DIR3CAIB_VERSIO%.zip scripts doc/pdf versio.txt ear/target/dir3caib.ear ws/dir3caib-api/target/dir3caib-ws-api-1.0.0.jar -x "**/.svn**"  -x "scripts/sqlgenenerator/**" -x "scripts/pom.xml" 