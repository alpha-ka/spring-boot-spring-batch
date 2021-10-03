package com.alpha.config;

import java.io.IOException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.alpha.batch.MoveFilesTasklet;
import com.alpha.model.Student;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private MoveFilesTasklet moveFilesTasklet;
	// @Value(value = "${input}")
	// public Resource[] resource;
	@Value("${input}")
	public String filepath;

	@Bean
	public FlatFileItemReader<Student> fileItemReader() {
		FlatFileItemReader<Student> fileItemReader = new FlatFileItemReader<Student>();
		fileItemReader.setName("CSV-Reader");
		fileItemReader.setLinesToSkip(1);
		fileItemReader.setLineMapper(lineMapper());
		return fileItemReader;
	}
	
	public LineMapper<Student> lineMapper() {
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setDelimiter(",");
		delimitedLineTokenizer.setStrict(false);
		delimitedLineTokenizer.setNames(new String[] { "rollno", "name", "email", "age", "location" });

		BeanWrapperFieldSetMapper<Student> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<Student>();
		beanWrapperFieldSetMapper.setTargetType(Student.class);

		DefaultLineMapper<Student> lineMapper = new DefaultLineMapper<Student>();
		lineMapper.setLineTokenizer(delimitedLineTokenizer);
		lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

		return lineMapper;
	}
	
	public MultiResourceItemReader<Student> multiResourceItemReader() {
		MultiResourceItemReader<Student> multiResourceItemReader = new MultiResourceItemReader<Student>();
		// multiResourceItemReader.setResources(resource);
		Resource[] resources = null;
		ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
		try {
			// resources = patternResolver.getResources("classpath:input/*.csv");
			resources = patternResolver.getResources(filepath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		multiResourceItemReader.setResources(resources);
		multiResourceItemReader.setDelegate(fileItemReader());
		return multiResourceItemReader;
	}

	@Bean
	public Job job(ItemProcessor<Student, Student> itemProcessor, ItemWriter<Student> itemWriter) {
		Step flowStep = stepBuilderFactory.get("Flow Step").<Student, Student>chunk(100)
				.reader(multiResourceItemReader()).processor(itemProcessor).writer(itemWriter).build();

		Step moveStep = stepBuilderFactory.get("Uploaded-Files-Move").tasklet(moveFilesTasklet).build();

		return jobBuilderFactory.get("Beeline Line File Reader").incrementer(new RunIdIncrementer()).flow(flowStep)
				.next(moveStep).end().build();

	}
}
