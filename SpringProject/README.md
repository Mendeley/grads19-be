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

TO USE DOCKERFILES (deployment):
-

Add the start statements to the bottom of each Dockerfile:

- Java: `ENTRYPOINT ["java","-cp","app:app/lib/*","gradproject2019.Application"]`
- React: `CMD ["npm", "start"]`
- Mysql: N/A
- ElasticSearch: 

Then you can do the following (replaces step 1 of the docker-compose method):

- For the Java open the command line and navigate to `grads19-be/SpringProject` and run `docker build . -t java` followed by `docker run -p 8080:8080 java`

- For the React open the command line and navigate to `grads19-weblet` and run `docker build . -t react` followed by `docker run -p 3000:3000 react`

- For the MySQL open the command line and navigate to `grads19-be/SpringProject/MainApplication/src/main/resources` and run `docker build . -t mysql` followed by `docker run -p 3306:3306 mysql`

- For the Elastic Search open the command line and navigate to `grads19-be/SpringProject/ElasticSearch` and run `docker build . -t elasticsearch` followed by `docker run -p 9200:9200 elasticsearch`

(p.s. open this in intelij for clearer formatting)