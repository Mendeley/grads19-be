package grads19.tasklets;


import grads19.services.ElasticSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteIndicesTasklet implements Tasklet {

    private static final Logger LOG = LoggerFactory.getLogger(CreateIndicesTasklet.class);

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        final var result = elasticSearchService.deleteIndices();

        if (result) {
            LOG.info("index sucessfully deleted");
        } else {
            LOG.error("Index failed to delete");
        }
        return RepeatStatus.FINISHED;
    }
}