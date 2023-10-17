package com.userservice.models;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emi {

	private Long eId;
	private String loanNo;
	private BigDecimal emiAmount;
	private BigDecimal interestAmount;
	private long noOfPayment;
	private LocalDate emiDate;
	private BigDecimal lateFineCharge;
	private BigDecimal totalAmount;
	private String status;
	private boolean emiStatus;
	private String createdBy;
	private LocalDateTime createdDate;
	private String modifiedBy;
	private LocalDateTime modifiedDate;

}
