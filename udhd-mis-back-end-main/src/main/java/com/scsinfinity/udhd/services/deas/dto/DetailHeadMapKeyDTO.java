package com.scsinfinity.udhd.services.deas.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DetailHeadMapKeyDTO extends KeyForMinorHeadWithMajorHeadIdAndMinorHeadIdDTO {
	public DetailHeadMapKeyDTO(String majorHeadCode, String minorHeadCode) {
		super(majorHeadCode, minorHeadCode);
	}

	public DetailHeadMapKeyDTO(String majorHeadCode, String minorHeadCode, String detailHeadCode) {
		super(majorHeadCode, minorHeadCode);
		this.detailHeadKey = detailHeadCode;
	}

	private String detailHeadKey;
}
