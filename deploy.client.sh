cd client

echo "##############################"
echo "Installing dependencies"
echo "##############################"
npm install 

echo "##############################"
echo "Building the frontend project"
echo "##############################"
npm run build

echo "##############################"
echo "Deploying Frontend project..."
echo "##############################"

scp -i ../deploy_key -r ./build/*  root@$SERVER_IP_ADDRESS:/var/www/$PROJECT_NAME