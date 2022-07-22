rem set JAVA_HOME=C:\Program Files\Java\jdk1.7.0_80
rem set MAVEN_OPTS=-Xmx512m -XX:MaxPermSize=128m -Dhttps.protocols=TLSv1.2
rem mvn -Dhttps.protocols=TLSv1.2 clean install -DskipTests -Dregenerateapi -e


rem set JAVA_HOME=C:\PROGRA~1\Java\jdk1.7.0_80
rem set MAVEN_OPTS=-Xmx512m -XX:MaxPermSize=128m -Dhttps.protocols=TLSv1.2

mvn clean install -DskipTests -Dregenerateapi

