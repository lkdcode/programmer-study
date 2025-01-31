package org.jpa.chap03.schedule;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.util.Random;

@DisallowConcurrentExecution
public class HelloJob implements Job {
    private static final Random RANDOM = new Random();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (RANDOM.nextInt(10) % 2 == 0) {
            System.out.println("HelloJob.execute!!!!!!!! Time: " + LocalDateTime.now());
        } else {
            throw new IllegalArgumentException("으앆!!!!!!!!1");
        }
    }
}