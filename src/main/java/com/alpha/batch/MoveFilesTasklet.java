package com.alpha.batch;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MoveFilesTasklet implements Tasklet {
	 @Value("${filepath}")
	 private String filePath;
	 @Value("${fileUploadedpath}")
	 private String newfilepath;
	 
	 
	 private Logger log=LoggerFactory.getLogger(MoveFilesTasklet.class);
	 
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
	
		log.info("File Started moving from "+ filePath);
	 Date date =new Date();
	 SimpleDateFormat formatter=new SimpleDateFormat("MM-dd-yyyy hh");
	 String todayDate=formatter.format(date)+"/";
		
	    File srcDirectory = new File(filePath);
		 
	  File destDirectory = new File(newfilepath+todayDate);		 
	       destDirectory.mkdir();
	       log.info(">>>>>>>>>>>>>File Starts moving from "+ filePath+" to "+newfilepath+todayDate);  
		  
	        Arrays.asList(srcDirectory.listFiles())
	                .stream()
	                .forEach(singleFile -> singleFile.renameTo(new File(newfilepath+todayDate+singleFile.getName())));               
	        return RepeatStatus.FINISHED;
	}

}
