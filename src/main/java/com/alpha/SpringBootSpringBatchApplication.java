package com.alpha;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.alpha.joblaucher.BatchJobLaucher;

@SpringBootApplication
public class SpringBootSpringBatchApplication {

	public static void main(String[] args) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		ConfigurableApplicationContext context=SpringApplication.run(SpringBootSpringBatchApplication.class, args);
		BatchJobLaucher jobLaucher=context.getBean(BatchJobLaucher.class);
		jobLaucher.load();
	
	}

}
