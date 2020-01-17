#FROM docker.elastic.co/elasticsearch/elasticsearch:7.1.0
#COPY --chown=elasticsearch:elasticsearch ./dockerfile-resources/elasticsearch.yml /usr/share/elasticsearch/config/
#ENV ES_JAVA_OPTS='-Xms512m -Xmx512m'
#EXPOSE 9200
#




FROM docker.elastic.co/elasticsearch/elasticsearch:7.1.0
#WORKDIR /usr/share/elasticsearch
USER root

# copying custom-entrypoint and configuration
# to their respective directories in /usr/share/elasticsearch (already the WORKDIR)
COPY ./dockerfile-resources/custom-entrypoint /custom-entrypoint
COPY ./dockerfile-resources/elasticsearch.yml /usr/share/elasticsearch/config

# assuring "elasticsearch" user have appropriate access to configuration and custom-entrypoint
# make sure custom-entrypoint is executable
RUN chown elasticsearch:elasticsearch /usr/share/elasticsearch/config/elasticsearch.yml /custom-entrypoint && \
    chmod 0750 /custom-entrypoint

ENV ES_JAVA_OPTS='-Xms512m -Xmx512m'
EXPOSE 9200

# start by running the custom entrypoint (as root)
ENTRYPOINT ["/bin/bash", "/custom-entrypoint"]
CMD ["elasticsearch"]