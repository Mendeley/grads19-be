
package gradproject2019.elasticsearch.persistence;


import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;



@Configuration
@EnableElasticsearchRepositories(basePackages = "gradproject2019.conferences.repository")
@ComponentScan(basePackages = "gradproject2019.conferences.service")
public class SearchConfig {
    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String esHost;

    @Bean
    public RestHighLevelClient client() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(esHost)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client());
    }
}



//    @Bean
//    @Override
//    public EntityMapper EntityMapper() {
//        ElasticsearchEntityMapper entityMapper = new ElasticsearchEntityMapper(
//                elasticsearchMappingContext(), new DefaultConversionService());
//        entityMapper.setConversions(elasticsearchCustomConversions());
//
//        return entityMapper;
//    }
//
//    @Bean
//    @Override
//    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
//        return new ElasticsearchCustomConversions(
//                Arrays.asList(new AddressToMap(), new MapToAddress()));
//    }
//
//    @WritingConverter
//    static class AddressToMap implements Converter<Address, Map<String, Object>> {
//
//        @Override
//        public Map<String, Object> convert(Address source) {
//
//            LinkedHashMap<String, Object> target = new LinkedHashMap<>();
//            target.put("ciudad", source.getCity());
//            // ...
//
//            return target;
//        }
//    }
//
//    @ReadingConverter
//    static class MapToAddress implements Converter<Map<String, Object>, Address> {
//
//        @Override
//        public Address convert(Map<String, Object> source) {
//
//            // ...
//            return address;
//        }
//    }
//
//
//
//
//
//        @Bean
//    @Primary
//    public ElasticsearchOperations elasticsearchTemplate(final JestClient jestClient,
//                                                         final ElasticsearchConverter elasticsearchConverter,
//                                                         final SimpleElasticsearchMappingContext simpleElasticsearchMappingContext, EntityMapper mapper) {
//        return new JestElasticsearchTemplate(jestClient, elasticsearchConverter,
//                new DefaultJestResultsMapper(simpleElasticsearchMappingContext, mapper));
//    }
//
//    public class CustomEntityMapper implements EntityMapper {
//
//        private ObjectMapper mapper;
//
//        @Autowired
//        public CustomEntityMapper() {
//            this.mapper = new ObjectMapper();
//            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//            mapper.disable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//            mapper.registerModule(new JavaTimeModule());
//        }
//
//        @Override
//        public String mapToString(Object object) throws IOException {
//            return mapper.writeValueAsString(object);
//        }
//
//        @Override
//        public <T> T mapToObject(String source, Class<T> clazz) throws IOException {
//            return mapper.readValue(source, clazz);
//        }
//
//    }
//}