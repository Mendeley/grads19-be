package grads19.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import grads19.batch.Conference;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean createIndices() {
        try {
            final var indicesBytes = readIndicesFile();

            final var request = new CreateIndexRequest("conferences");
            final var bytesReference = new BytesArray(indicesBytes);
            request.mapping(bytesReference, XContentType.JSON);

            final var createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);

            return createIndexResponse.isAcknowledged();

        } catch (Exception e) {
            LOG.error("failed to create indices", e);
            return false;
        }
    }

    @Override
    public boolean insertData(List<Conference> conferences) {
        BulkRequest bulkRequest = new BulkRequest();

        for (Conference conference : conferences) {
            IndexRequest indexRequest = createIndexRequest(conference);
            bulkRequest.add(indexRequest);
        }
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
            return true;
        } catch (Exception e) {
            LOG.error("failed to insert data", e);
            return false;
        }
    }

    @Override
    public boolean deleteIndices() {
        try {
            final var request = new DeleteIndexRequest("conferences");
            final var deleteIndexResponse = client.indices().delete(request, RequestOptions.DEFAULT);

            return deleteIndexResponse.isAcknowledged();
        } catch (Exception e) {
            LOG.error("failed to delete indices", e);
            return false;
        }
    }

    private IndexRequest createIndexRequest(Conference conference) {
        try {
            final var stringObject = objectMapper.writeValueAsString(conference);
            IndexRequest indexRequest = new IndexRequest("conferences");
            indexRequest.source(stringObject, XContentType.JSON);
            return indexRequest;
        } catch (Exception e) {
            LOG.error("failed to create index request", e);
            throw new RuntimeException();
        }
    }

    private byte[] readIndicesFile() throws Exception {
        return Files.readAllBytes(Paths.get(ResourceUtils.getFile("classpath:indices/conference.json").getPath()));
    }
}