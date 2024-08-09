package com.scsinfinity.udhd.services.audit.internalaudit.interfaces;

import java.util.List;

import com.scsinfinity.udhd.services.common.BooleanResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAExSumCommentOpinionAcknowledgementDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAExecSummaryStrengthWeaknessOrRecommendationInputDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAExecutiveSummaryDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAExecutiveSummaryIntroductionRequestDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAExecutiveSummarySWOREnum;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAStengthWeaknessOrRecommendationOutputDTO;

public interface IIAExecutiveSummaryService {
	IAExecutiveSummaryDTO createUpdateIntroductionForExecutiveSummary(IAExecutiveSummaryIntroductionRequestDTO dto);

	IAStengthWeaknessOrRecommendationOutputDTO createUpdateStrengthWeaknessRecommendation(
			IAExecSummaryStrengthWeaknessOrRecommendationInputDTO swrDTO);

	IAStengthWeaknessOrRecommendationOutputDTO getSworById(
			IAExecSummaryStrengthWeaknessOrRecommendationInputDTO sworDTO);

	IAExecutiveSummaryDTO createUpdateCommentOpinionAcknowledgement(IAExSumCommentOpinionAcknowledgementDTO inputDTO);

	IAExecutiveSummaryDTO getExecutiveSummaryById(Long ia);

	String getCommentOpinionAcknowledgement(IAExSumCommentOpinionAcknowledgementDTO inputDTO);

	List<IAStengthWeaknessOrRecommendationOutputDTO> getListOfStrengthsWeaknessesOrRecommendations(Long iaId,
			IAExecutiveSummarySWOREnum type);

	IAExecutiveSummaryDTO uploadExSumCommentFile(MultipartFile file, Long parseLong);

	Resource getFile(Long iaId);

	BooleanResponseDTO deleteStrengthWeaknessRecommendation(IAExecSummaryStrengthWeaknessOrRecommendationInputDTO swrDTO);
}
