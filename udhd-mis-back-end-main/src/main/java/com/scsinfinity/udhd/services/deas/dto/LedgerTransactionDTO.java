package com.scsinfinity.udhd.services.deas.dto;

import java.math.BigDecimal;

import com.scsinfinity.udhd.dao.entities.deas.TrialUploadStatusEnum;
import com.scsinfinity.udhd.dao.entities.ledger.BaseHeadEntity;
import com.scsinfinity.udhd.dao.entities.ledger.DetailHeadEntity;
import com.scsinfinity.udhd.dao.entities.ledger.MajorHeadEntity;
import com.scsinfinity.udhd.dao.entities.ledger.MinorHeadEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LedgerTransactionDTO {

	private String accountCode;
	private String particular;
	private BigDecimal openingBlncDebit;
	private BigDecimal openingBlncCredit;
	private Boolean openingDebitOrCredit; // true for credit and false for debit
	private BigDecimal closingBlncDebit;
	private BigDecimal closingBlncCredit;
	private Boolean closingDebitOrCredit;// true for credit and false for debit
	private BigDecimal debitAmount;
	private BigDecimal creditAmount;
	private BaseHeadEntity baseHead;
	private MajorHeadEntity majorHead;
	private MinorHeadEntity minorHead;
	private DetailHeadEntity detailHead;
	private BigDecimal openingBlnc;
	private BigDecimal closingBlnc;
	private Boolean active;
	private String error1;
	private String error2;
	private String error3;
	private String error4;
	private TrialUploadStatusEnum status;
}
