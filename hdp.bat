IF NOT DEFINED in_subprocess (cmd /k SET in_subprocess=y ^& %0 %*) & EXIT)

::stop existing running containers
docker container stop mysql
docker container stop heart-disease-prediction-using-ai-webapp

::remove existing running containers
docker container rm mysql
docker container rm heart-disease-prediction-using-ai-webapp

::build app and generate war file
CALL mvn clean install

::create docker image
docker build -t shubhdes/heart-disease-prediction-using-ai-webapp:1.0 .

::run docker images
docker compose -f docker-compose.yaml up