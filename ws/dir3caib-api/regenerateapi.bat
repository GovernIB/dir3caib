
set MAVEN_OPTS=-Xmx512m -XX:MaxPermSize=128m

mvn -Dhttps.protocols=TLSv1.2 clean install -DskipTests -Dregenerateapi -e

