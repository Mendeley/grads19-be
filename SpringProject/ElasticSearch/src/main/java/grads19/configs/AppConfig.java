package grads19.configs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Value("${elastic-search.endpoint}")
    private String elasticSearchUrl;

    @Value("${elastic-search.protocol}")
    private String elasticSearchProtocol;

    @Value("${elastic-search.port}")
    private int elasticSearchPort;

    @Bean(name = "mysqlDataSource")
    @ConfigurationProperties(prefix = "mysql.datasource")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "elasticSearchClient")
    public RestHighLevelClient elasticSearchClient() {
        return new RestHighLevelClient(RestClient.builder(
                new HttpHost(elasticSearchUrl, elasticSearchPort, elasticSearchProtocol)));
    }

    @Bean(name = "objectMapper")
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
        return mapper;
    }
}