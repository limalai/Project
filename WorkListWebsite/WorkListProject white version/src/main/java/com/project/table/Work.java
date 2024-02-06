package com.project.table;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "work")
public class Work {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public int WorkID;
	private String name;
	private Date date;
	
	@JsonIgnore 
	@ManyToOne(optional=false)
	@JoinColumn(name="Status")
    private Status status;
	
	@JsonIgnore 
	@ManyToOne(optional=false)
	@JoinColumn(name="Type")
    private Type type;
	
	@JsonIgnore 
	@ManyToOne(optional=false)
	@JoinColumn(name="Subject")
    private Subject subject;

	public int getIdwork() {
		return WorkID;
	}

	public void setIdwork(int WorkID) {
		this.WorkID = WorkID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	public String getSubjectName() {
		return subject.getName();	
	}
	
	public String getTypeName() {
		return type.getName();	
	}
	
	public String getStatustName() {
		return status.getName();	
	}

	
}
