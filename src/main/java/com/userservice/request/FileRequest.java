package com.userservice.request;

import org.springframework.web.multipart.MultipartFile;

public class FileRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;

	private String jobName;
	private MultipartFile file;
	private String filePath;
	private String fileName;
	private String userName;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public FileRequest() {
		super();
	}

	public FileRequest(String jobName, MultipartFile file, String filePath, String fileName, String userName) {
		super();
		this.jobName = jobName;
		this.file = file;
		this.filePath = filePath;
		this.fileName = fileName;
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "FileRequest [jobName=" + jobName + ", file=" + file + ", filePath=" + filePath + ", fileName="
				+ fileName + ", userName=" + userName + "]";
	}

	

}
