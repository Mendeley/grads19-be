package grads19.configs;


import grads19.batch.*;
import grads19.tasklets.CreateIndicesTasklet;
import grads19.tasklets.DeleteIndicesTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfig {

    @Value("${spring.batch.chunk-size}")
    private Integer chunkSize;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ConferenceReader conferenceReader;

    @Autowired
    private ConferenceProcessor conferenceProcessor;

    @Autowired
    private ConferenceWriter conferenceWriter;

    @Autowired
    private JobCompletionListener jobCompletionListener;

    @Autowired
    private DeleteIndicesTasklet deleteIndicesTasklet;

    @Autowired
    private CreateIndicesTasklet createIndicesTasklet;

    @Bean
    public Job conferencesJob() {
        return jobBuilderFactory.get("conferencesJob")
                .incrementer(new RunIdIncrementer())
                .listener(jobCompletionListener)
                .start(deleteIndicesStep())
                .next(createIndicesStep())
                .next(importConferences())
                .preventRestart()
                .build();
    }

    private Step deleteIndicesStep() {
        return stepBuilderFactory.get("deleteIndices")
                .tasklet(deleteIndicesTasklet)
                .build();
    }

    private Step createIndicesStep() {
        return stepBuilderFactory.get("createIndices")
                .tasklet(createIndicesTasklet)
                .build();
    }

    private Step importConferences() {
        return stepBuilderFactory.get("importConferencesStep").<ConferenceDTO, Conference>chunk(chunkSize)
                .reader(conferenceReader)
                .processor(conferenceProcessor)
                .writer(conferenceWriter)
                .build();
    }
}