package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "IA_AUDIT_TEAM_TEAM_LEADERS")
public class IAAuditTeamTeamLeadersEntity extends IAAuditTeamAbstractEntity {

}
