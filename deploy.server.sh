# Load .env file
if [ -f .env ]; then
    export $(cat .env | grep -v '#' | awk '/=/ {print $1}')
fi

cd server

echo "##############################"
echo "DEPLOYING SERVER"
echo "##############################"
mvn -DskipTests -Dremote.user=$REMOTE_USER -Dremote.password=$REMOTE_PW tomcat7:deploy