package com.userservice.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

//@Data
@Entity
@Table(name = "EMAIL_LOGGER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmailLogger {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "ERROR_CODE")
	private String errorCode;

	@Column(name = "ERROR_DESCRIPTION")
	private String errorDescription;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private LocalDateTime createdDate;

/*	public EmailLogger() {
	}*/


}