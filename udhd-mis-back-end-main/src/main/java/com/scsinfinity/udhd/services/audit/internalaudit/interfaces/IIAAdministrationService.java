package com.scsinfinity.udhd.services.audit.internalaudit.interfaces;

import java.util.List;

import com.scsinfinity.udhd.services.audit.internalaudit.dto.AdminAuditTeamTypeEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAAdministrationAuditTeamDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAAdministrationDTO;

public interface IIAAdministrationService {
	IAAdministrationDTO createAdministration(final IAAdministrationDTO administrationDTO);

	IAAdministrationDTO retrieveAdministration(final Long iaId);

	IAAdministrationAuditTeamDTO createAuditTeamMemberByType(Long iaId, IAAdministrationAuditTeamDTO teamMember);

	List<IAAdministrationAuditTeamDTO> getListByType(Long iaId, AdminAuditTeamTypeEnum type);

	IAAdministrationAuditTeamDTO getMemberByTypeAndId(Long iaId, Long memId, AdminAuditTeamTypeEnum type);

	Boolean deleteMemberByType(Long iaId, Long id, AdminAuditTeamTypeEnum type);
}
