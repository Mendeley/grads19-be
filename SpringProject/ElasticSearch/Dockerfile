FROM docker.elastic.co/elasticsearch/elasticsearch:7.1.0
COPY --chown=elasticsearch:elasticsearch ./dockerfile-resources/elasticsearch.yml /usr/share/elasticsearch/config/
ENV ES_JAVA_OPTS='-Xms512m -Xmx512m'
EXPOSE 9200