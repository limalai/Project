package com.project.table;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "type")
public class Type {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public int ID;
	@Column(name = "Name")
	private String Name;
	
	@OneToMany(targetEntity=Work.class, mappedBy="type",
    		cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Work> works;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public List<Work> getWorks() {
		return works;
	}

	public void setWorks(List<Work> works) {
		this.works = works;
	}
	

}
