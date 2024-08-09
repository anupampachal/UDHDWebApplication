package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@Table(name = "IA_NON_COMPLIANCE_TDS_ON_GST")
public class IANonComplianceTDSOnGSTEntity extends IANonComplianceOfTDSNVAT {
}
