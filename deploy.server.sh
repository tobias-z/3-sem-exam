cd server

echo "##############################"
echo "DEPLOYING SERVER"
echo "##############################"
mvn -DskipTests -Dremote.user=$REMOTE_USER -Dremote.password=$REMOTE_PW tomcat7:deploy