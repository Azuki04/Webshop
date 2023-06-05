# Dockerfile good to go :-)

docker build -t wissquiz-frontend:0.2 .

docker run -d -p 80:80 --name wissquiz-frontend wissquiz-frontend:0.2

