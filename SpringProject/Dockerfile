FROM maven:3.6.2-jdk-11 AS Build
ARG MAVEN_VERSION=3.6.2
ENV MAVEN_CONFIG=''
WORKDIR /app
# installing mvnw wrapper
RUN mvn -N io.takari:maven:wrapper -Dmaven=${MAVEN_VERSION} && ./mvnw --version
COPY ./MainApplication/pom.xml .
COPY ./pom.xml ../
# resolves all project dependencies locally
RUN ./mvnw dependency:go-offline -B
COPY ./MainApplication .
# compile  the code and package
RUN ./mvnw package\
    && unzip target/*.jar  -d target/dependency
FROM openjdk:11-jdk
VOLUME /tmp
ARG DEPENDENCY=/app/target/dependency
COPY --from=Build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=Build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=Build ${DEPENDENCY}/BOOT-INF/classes /app
COPY wait-for-it.sh /usr/wait-for-it.sh
RUN chmod +x /usr/wait-for-it.sh
EXPOSE 8080