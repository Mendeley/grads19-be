package grads19.configs;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionListener extends JobExecutionListenerSupport {

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            System.out.println("FINISHED SUCCESSFULLY");
            System.exit(0);
        } else {
            System.out.println("FINISHED UNSUCCESSFULLY");
            System.exit(-1);
        }
    }
}