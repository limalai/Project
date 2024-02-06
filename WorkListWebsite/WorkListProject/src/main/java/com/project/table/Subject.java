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
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "subject")
public class Subject {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "subjectID")
	public int subjectID;
	private String Name;
	
	@OneToOne(mappedBy = "SubjectID", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
    private SubjectDetail subjectDetail;
	
	@OneToMany(targetEntity=Work.class, mappedBy="subject",
    		cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Work> works;

	public int getIdsubject() {
		return subjectID;
	}

	public void setIdsubject(int subjectID) {
		this.subjectID = subjectID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public SubjectDetail getSubjectDetail() {
		return subjectDetail;
	}

	public void setSubjectDetail(SubjectDetail subjectDetail) {
		this.subjectDetail = subjectDetail;
	}
	
	public String getDetail() {
		return subjectDetail.getDetail();	
	}
	
	public double getMid() {
		return subjectDetail.getMidtermScore();	
	}
	
	public double getFinal() {
		return subjectDetail.getFinalScore();	
	}
	
	public double getProject() {
		return subjectDetail.getProjectScore();	
	}
	
	public double getAssignment() {
		return subjectDetail.getAssignmentScore();	
	}

}
