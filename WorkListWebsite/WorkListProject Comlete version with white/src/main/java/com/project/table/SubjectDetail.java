package com.project.table;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "subject_detail")
public class SubjectDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "detailID")
	private int detailID;
	
	@Column(name = "Detail")
	private String Detail;
	
	@Column(name = "midterm_score")
	private	double midtermScore;
	
	@Column(name = "final_score")
	private	double finalScore;
	
	@Column(name = "project_score")
	private	double projectScore;
	
	@Column(name = "assignment_score")
	private	double assignmentScore;
	
	//Primary key sharing
    @OneToOne
    @MapsId
    @JoinColumn(name = "detailID")
    private Subject SubjectID;
	

	public int getDetailID() {
		return detailID;
	}

	public void setDetailID(int detailID) {
		this.detailID = detailID;
	}

	public String getDetail() {
		return Detail;
	}

	public void setDetail(String Detail) {
		this.Detail = Detail;
	}
	
	public double getMidtermScore() {
		return midtermScore;
	}

	public void setMidtermScore(double midtermScore) {
		this.midtermScore = midtermScore;
	}

	public double getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(double finalScore) {
		this.finalScore = finalScore;
	}

	public double getProjectScore() {
		return projectScore;
	}

	public void setProjectScore(double projectScore) {
		this.projectScore = projectScore;
	}

	public double getAssignmentScore() {
		return assignmentScore;
	}

	public void setAssignmentScore(double assignmentScore) {
		this.assignmentScore = assignmentScore;
	}


	public Subject getSubject() {
		return SubjectID;
	}

	public void setSubject(Subject subject) {
		this.SubjectID = subject;
	}
    
	
}
