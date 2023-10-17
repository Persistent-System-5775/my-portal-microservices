package com.userservice.models;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

	private Long loanId;
	private Long userId;
	private Long emiId;
	private String loanType;
	private String loanNo;
	private BigDecimal loanAmount;
	private Long totalEmi;
	private Long emiPaid;
	private Long emiRemaining;
	private BigDecimal emiAmount;
	private String interestType;
	private BigDecimal interestRate;
	private LocalDate disbursalDate;
	private LocalDate lastPaidEmiDate;
	private LocalDate firstEmiDate;
	private LocalDate lastEmiDate;
	private boolean loanStatus;
	private String status;
	private String logoName;
	private String bank;
	private String extAttr2;
	private String extAttr3;
	private String extAttr4;
	private BigDecimal interestPaid;
	private String createdBy;
	private LocalDateTime createdDate;
	private String modifiedBy;
	private LocalDateTime modifiedDate;
	private Emi emi;


}
