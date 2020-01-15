First make sure your file structure is as shown below:
 
    gradproject2019(or whatever you have named it)
        ↳ grads19-weblet
        ↳ grads19-be
 
and that you are in the latest versions of the GP19-153 and GP19-154 Branches.

TO USE DOCKER-COMPOSE (local):
-

1. Open the command line and navigate to `grads19-be/SpringProject` and run `docker-compose up --build`
2. To stop the containers hit `control` and `c`, then run `docker-compose down`

TO USE DOCKERFILES (local & deployment):
-

Add the start statements to the bottom of each Dockerfile:

- Java: `ENTRYPOINT ["java","-cp","app:app/lib/*","gradproject2019.Application"]` (if you are doing this for deployment purposes, make sure you define values for the following environment variables `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_PASSWORD`, `SPRING_DATASOURCE_USERNAME` and `SPRING_DATA_ELASTICSEARCH_CLUSTER-NODES=${equivalent of localhost for ES}:9200`)
- React: `CMD ["npm", "start"]`
- Batch: `ENTRYPOINT ["java","-cp","app:app/lib/*","grads19.ElasticSearchIngester"]` (for deployment purposes, make sure you define values for the following environment variables `ELASTICSEARCH_ENDPOINT=${equivalent of localhost for ES}`, `MYSQL_DATASOURCE_JDBCURL=${SPRING_DATASOURCE_URL}&useSSL=false`, `MYSQL_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}` and `MYSQL_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}`)
- Mysql: N/A
- ElasticSearch: N/A (for deployment purposes make sure the container configuration includes the following code)

        ulimits:
            memlock:
                soft: -1
                hard: -1

Then you can do the following (replaces step 1 of the docker-compose method) (for deployment purposes ignore the `docker run` statements):

1. For the MySQL open the command line and navigate to `grads19-be/SpringProject/MainApplication/src/main/resources` and run `docker build . -t mysql` followed by `docker run -p 3306:3306 mysql`

2. For the Java open the command line and navigate to `grads19-be/SpringProject` and run `docker build . -t java` followed by `docker run -p 8080:8080 -e SPRING_DATASOURCE_URL='jdbc:mysql://localhost:3306/conference_finder?autoReconnect=true' -e SPRING_DATASOURCE_PASSWORD='password' -e SPRING_DATASOURCE_USERNAME='root' -e SPRING_DATA_ELASTICSEARCH_CLUSTER-NODES='localhost:9200' java`

3. For the Elastic Search open the command line and navigate to `grads19-be/SpringProject/ElasticSearch` and run `docker build . -t elasticsearch` followed by `docker run -p 9200:9200 -ti -v /usr/share/elasticsearch/data --ulimit memlock=-1:-1 elasticsearch`

4. For the Batch Run open the command line and navigate to `grads19-be/SpringProject` and run `docker build -f ./ElasticSearch/BatchDockerfile/Dockerfile . -t batchrun` followed by `docker run -p 8090:8090 -e ELASTICSEARCH_ENDPOINT='localhost' -e MYSQL_DATASOURCE_JDBCURL='jdbc:mysql://localhost:3306/conference_finder?autoReconnect=true&useSSL=false' -e MYSQL_DATASOURCE_PASSWORD='password' -e MYSQL_DATASOURCE_USERNAME='root' batchrun`

5. For the React open the command line and navigate to `grads19-weblet` and run `docker build . -t react` followed by `docker run -p 3000:3000 react`