# Run Backend on Docker (without CI/CD pipelines)

1. create network for this stack `docker network create mynet123`
2. create MySQL docker image (within src/resources/Database): `docker build -t wissquiz_db:1.0 .`
3. run MySQL container: `docker run -d --name wissquiz_db --network mynet123 -e MYSQL_ROOT_PASSWORD=schnappie -d wissquiz_db:1.0`

4. create Spring-Backend Docker image (within root folder): `docker build -t wissquiz_rest:0.1 .`
4. deploy Spring-Backend Docker image: `docker run -d --name wissquiz_api -p 8080:8080 -e MYSQL_HOST=wissquiz_db --network mynet123 wissquiz_rest:0.1`
