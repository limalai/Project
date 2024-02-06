package com.project.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Repository.StatusRepository;
import com.project.Repository.SubjectRepository;
import com.project.Repository.TypeRepository;
import com.project.Repository.WorkRepository;
import com.project.table.Status;
import com.project.table.Subject;
import com.project.table.Type;
import com.project.table.Work;

@Service
public class WorkService {
	private WorkRepository workRepository;
	private SubjectRepository subjectRepository;
	private TypeRepository typeRepository;
	private StatusRepository statusRepository;
	
	@Autowired
	public void setWorkRepository(WorkRepository workRepository) {
		this.workRepository=workRepository;
	}
	
	@Autowired
	public void setSubjectRepository(SubjectRepository subjectRepository) {
		this.subjectRepository=subjectRepository;
	}
	
	@Autowired
	public void setTypeRepository(TypeRepository typeRepository) {
		this.typeRepository=typeRepository;
	}
	
	@Autowired
	public void setStatusRepository(StatusRepository statusRepository) {
		this.statusRepository=statusRepository;
	}
	
	@Autowired
	public void setWorkService(WorkRepository workRepository, SubjectRepository subjectRepository, TypeRepository typeRepository, StatusRepository statusRepository) {
		this.workRepository=workRepository;
		this.subjectRepository=subjectRepository;
		this.typeRepository=typeRepository;
		this.statusRepository=statusRepository;
	}
	
	//--------------------------------------------------------------------------------------
	
	public List<Subject> getSubjectAll(){
		  return (List<Subject>) this.subjectRepository.findAll();
	  }
	
	public  Subject  getSubjectById(Integer id){
		  return this.subjectRepository.findById(id).get();
	  }
	
	public void deletSubjectById(Integer id) {
		Subject subject = this.subjectRepository.findById(  id).orElseThrow(() -> new IllegalArgumentException("Invalid subject Id:" + id));
		subjectRepository.delete(subject);
	}
	
	public void updateSubjectFromWork(Integer id, Subject sub) {
		Work work = this.workRepository.findById(id).get();

		if (work != null) {
			Subject subPro = this.subjectRepository.findById(work.getSubject().getIdsubject()).get();
			if (subPro == null)
				sub.setIdsubject(id);

			this.subjectRepository.save(sub);
		}
	}
	
	//--------------------------------------------------------------------------------------
	
	public List<Type> getTypeAll(){
		  return (List<Type>) this.typeRepository.findAll();
	  }
	
	public  Type  getTypeById(Integer id){
		  return this.typeRepository.findById(id).get();
	  }
	
	public void deletTypeById(Integer id) {
		Type type = this.typeRepository.findById(  id).orElseThrow(() -> new IllegalArgumentException("Invalid type Id:" + id));
		typeRepository.delete(type);
	}
	
	public void updateTypeFromWork(Integer id, Type type) {
		Work work = this.workRepository.findById(id).get();

		if (work != null) {
			Type typePro = this.typeRepository.findById(work.getType().getID()).get();
			if (typePro == null)
				type.setID(id);

			this.typeRepository.save(type);
		}
	}
	
	//--------------------------------------------------------------------------------------
	public List<Status> getStatusAll(){
		  return (List<Status>) this.statusRepository.findAll();
	  }
	
	public  Status  getStatusById(Integer id){
		  return this.statusRepository.findById(id).get();
	  }
	
	public void deletStatusById(Integer id) {
		Status status = this.statusRepository.findById(  id).orElseThrow(() -> new IllegalArgumentException("Invalid status Id:" + id));
		statusRepository.delete(status);
	}
	
	public void updateStatusFromWork(Integer id, Status status) {
		Work work = this.workRepository.findById(id).get();

		if (work != null) {
			Status statusPro = this.statusRepository.findById(work.getStatus().getID()).get();
			if (statusPro == null)
				status.setID(id);

			this.statusRepository.save(status);
		}
	}
	
	//--------------------------------------------------------------------------------------

	public void saveWork(Work work) {
		this.workRepository.save(work);
	}
	
	public void saveSubject(Subject subject) {
		this.subjectRepository.save(subject);
	}
	
	public void saveType(Type type) {
		this.typeRepository.save(type);
	}
	
	public void saveStatus(Status status) {
		this.statusRepository.save(status);
	}
	
	//--------------------------------------------------------------------------------------

	public void deleteWork(Work work) {
		this.workRepository.delete(work);
	}
	
	public void deleteSubject(Subject subject) {
		this.subjectRepository.delete(subject);
	}
	
	public void deleteType(Type type) {
		this.typeRepository.delete(type);
	}
	
	public void deleteStatus(Status status) {
		this.statusRepository.delete(status);
	}
	
	//--------------------------------------------------------------------------------------
	public List<Work> getWorktAll() {
		return (List<Work>) this.workRepository.findAll();
	}
	
	public Work getWorkById(Integer id) {
		return this.workRepository.findById(id).get();
	}
	
	public void deletWorkById(Integer id) {
		var work = workRepository.findById(id).get();
		workRepository.delete(work);
	}
	
	//--------
	
	public List<Work> searchWork(String name) {
	    //List<Work> workResults;
	    //workResults = workRepository.findByName(query);
	    return workRepository.findByNameContaining(name);
	}
	public List<Subject> searchSubject(String name) {
	    return subjectRepository.findByNameContaining(name);
	}
	public List<Type> searchType(String name) {
	    return typeRepository.findByNameContaining(name);
	}
	public List<Status> searchStatus(String name) {
	    return statusRepository.findByNameContaining(name);
	}
	
}
