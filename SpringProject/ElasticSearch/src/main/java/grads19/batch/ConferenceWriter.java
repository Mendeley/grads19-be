package grads19.batch;


import grads19.services.ElasticSearchService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConferenceWriter implements ItemWriter<Conference> {

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Override
    public void write(List<? extends Conference> items) {
        elasticSearchService.insertData((List<Conference>) items);
    }
}
