package com.alpha.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="Students")
public class Student {
@Id
private long rollno;
private String name;
private String email;
private int age;
private String location;

@Column(insertable = true, updatable = false)
@CreationTimestamp
private LocalDateTime createdate;
@Column(insertable = true, updatable = true)
@UpdateTimestamp
private LocalDateTime updatedate;



public long getRollno() {
	return rollno;
}



public void setRollno(long rollno) {
	this.rollno = rollno;
}



public String getName() {
	return name;
}



public void setName(String name) {
	this.name = name;
}



public String getEmail() {
	return email;
}



public void setEmail(String email) {
	this.email = email;
}



public int getAge() {
	return age;
}



public void setAge(int age) {
	this.age = age;
}



public String getLocation() {
	return location;
}



public void setLocation(String location) {
	this.location = location;
}



@Override
public String toString() {
	return "Student [rollno=" + rollno + ", name=" + name + ", email=" + email + ", age=" + age + ", location="
			+ location + ", createdate=" + createdate + ", updatedate=" + updatedate + "]";
}






}
