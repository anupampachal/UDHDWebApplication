package com.scsinfinity.udhd.services.deas.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class KeyForMinorHeadWithMajorHeadIdAndMinorHeadIdDTO {
	private String majorHeadCode;
	private String minorHeadCode;
}
