# Load .env file
if [ -f .env ]; then
    export $(cat .env | grep -v '#' | awk '/=/ {print $1}')
fi

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

scp -i ../deploy_key -r ./build/*  root@$DROPLET_URL:/var/www/$PROJECT_NAME