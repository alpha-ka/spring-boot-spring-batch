package com.alpha.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alpha.model.Student;
import com.alpha.repository.StudentRepository;

@Component
public class ItemWriterDB implements ItemWriter<Student> {
	@Autowired
	public StudentRepository studentRepo;
	
	private Logger log=LoggerFactory.getLogger(ItemWriterDB.class);
	
	@Override
	public void write(List<? extends Student> students) throws Exception {
		// TODO Auto-generated method stub
		
		
		log.info(">>>>>>>>>>>>>Data Saved in Students Table: " +  students);
		
		
		studentRepo.saveAll(students);
		
	}

}
