First make sure your file structure is as shown below:
 
    gradproject2019(or whatever you have named it)
        ↳ grads19-weblet
        ↳ grads19-be
 
and that you are in the latest versions of the GP19-153 and GP19-154 Branches.

TO USE DOCKER-COMPOSE (local):
-

1. Open the command line and navigate to `grads19-be/SpringProject` and run `docker-compose up --build`
2. Then run `ElasticSearchIngester.java` (found in `grads19-be/SpringProject/ElasticSearch`)

- To stop the containers hit `control` and `c`, then run `docker-compose down`

TO USE DOCKERFILES (local & deployment):
-

Add the start statements to the bottom of each Dockerfile:

- Java: `ENTRYPOINT ["java","-cp","app:app/lib/*","gradproject2019.Application","-Dspring.datasource.url=jdbc:mysql://mysql:3306/conference_finder?autoReconnect=true -Dspring.datasource.password=password -Dspring.datasource.username=root"]` (if you are doing this for deployment purposes, ignore this last set of "")
- React: `CMD ["npm", "start"]`
- Mysql: N/A
- ElasticSearch: N/A (for deployment purposes make sure the configuration files include the following environment variable, `ES_JAVA_OPTS='-Xms512m -Xmx512m'`, and a container configuration including the following code)

        ulimits:
            memlock:
                soft: -1
                hard: -1

Then you can do the following (replaces step 1 of the docker-compose method) (for deployment purposes ignore the `docker run` statements):

- For the Java open the command line and navigate to `grads19-be/SpringProject` and run `docker build . -t java` followed by `docker run -p 8080:8080 java`

- For the React open the command line and navigate to `grads19-weblet` and run `docker build . -t react` followed by `docker run -p 3000:3000 react`

- For the MySQL open the command line and navigate to `grads19-be/SpringProject/MainApplication/src/main/resources` and run `docker build . -t mysql` followed by `docker run -p 3306:3306 mysql`

- For the Elastic Search open the command line and navigate to `grads19-be/SpringProject/ElasticSearch` and run `docker build . -t elasticsearch` followed by `docker run -p 9200:9200 -ti -v /usr/share/elasticsearch/data --ulimit memlock=-1:-1 -e ES_JAVA_OPTS='-Xms512m -Xmx512m' elasticsearch`