package com.tohours.imo.bean;

import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Table;
@Table("attract_sub_excellence")
public class SubExcellence extends BasePojo{
	@Id
	private int id;
	@Column
	private String name;
	@Column
	private Long fileCounts;
	@Column
	private String fileTypes;
	@Column
	private String filePaths;
	@Column
	private String fileNames;
	@Column
	private String contentTypes;
	@Column
	private String fileIds;
	@Many(target = Peoples.class,field="sub_excel_id")
	private List<Peoples> peoplesList;
	@Column
	private int top_excel_id;
	@Column
	private Boolean deleteFlag;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getFileCounts() {
		return fileCounts;
	}
	public void setFileCounts(Long fileCounts) {
		this.fileCounts = fileCounts;
	}
	public String getFileTypes() {
		return fileTypes;
	}
	public void setFileTypes(String fileTypes) {
		this.fileTypes = fileTypes;
	}
	public String getFilePaths() {
		return filePaths;
	}
	public void setFilePaths(String filePaths) {
		this.filePaths = filePaths;
	}
	public String getFileNames() {
		return fileNames;
	}
	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}
	public String getContentTypes() {
		return contentTypes;
	}
	public void setContentTypes(String contentTypes) {
		this.contentTypes = contentTypes;
	}
	public String getFileIds() {
		return fileIds;
	}
	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Peoples> getPeoplesList() {
		return peoplesList;
	}
	public void setPeoplesList(List<Peoples> peoplesList) {
		this.peoplesList = peoplesList;
	}
	public int getTop_excel_id() {
		return top_excel_id;
	}
	public void setTop_excel_id(int top_excel_id) {
		this.top_excel_id = top_excel_id;
	}
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
}
