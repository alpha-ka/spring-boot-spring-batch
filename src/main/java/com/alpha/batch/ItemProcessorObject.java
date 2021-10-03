package com.alpha.batch;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.alpha.model.Student;

@Component
public class ItemProcessorObject implements ItemProcessor<Student, Student> {

	private Logger log=LoggerFactory.getLogger(ItemProcessorObject.class);
	public static final Map<String, String> Country_Name = new HashMap<String, String>();

	public ItemProcessorObject() {
		Country_Name.put("USA", "United States");
		Country_Name.put("IND", "India");
		Country_Name.put("MYS", "Malaysia");
	}

	@Override
	public Student process(Student student) throws Exception {
		// TODO Auto-generated method stub
		
		log.info(">>>>>>>>>Entered into Item Processor - "+student);
		student.setName(student.getName().toUpperCase());
		student.setLocation(Country_Name.get(student.getLocation()));

		log.info(">>>>>>>>>Changes in Item Processor - "+student);
		
		return student;
	}

}
