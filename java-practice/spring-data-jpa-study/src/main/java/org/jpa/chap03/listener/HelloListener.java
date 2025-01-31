package org.jpa.chap03.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.stereotype.Service;

@Service
public class HelloListener implements JobListener {

    @Override
    public String getName() {
        return "HelloListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        System.out.println("Job is about to execute: " + context.getJobDetail().getKey());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("Job execution was vetoed: " + context.getJobDetail().getKey());
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        if (jobException != null) {
            System.err.println("Job failed: " + context.getJobDetail().getKey());
            System.err.println("Reason: " + jobException.getMessage());
        } else {
            System.out.println("Job executed successfully: " + context.getJobDetail().getKey());
        }
    }
}
