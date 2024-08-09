package com.scsinfinity.udhd.configurations.application.date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FinYearDateDTO {

	private String inputDate;
	private String startDate;
	private String endDate;
	//private DateQuartersEnum quarter;

}
