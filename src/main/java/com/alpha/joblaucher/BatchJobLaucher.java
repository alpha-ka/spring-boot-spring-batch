package com.alpha.joblaucher;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchJobLaucher {
	

	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;
 
 
	public BatchStatus load() throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {

		Map<String, JobParameter> maps = new HashMap<String, JobParameter>();

		maps.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters parameters = new JobParameters(maps);

		JobExecution jobExecution = jobLauncher.run(job, parameters);

		System.out.println("Batch job running...........");

		while (jobExecution.isRunning()) {
			System.out.println("....");

		}
		System.out.println("Job Execution Status :" + jobExecution.getStatus());
		return jobExecution.getStatus();

	}
}
