package com.scsinfinity.udhd.services.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ResponseObject {

	private String responseMessage;
	private List<Object> objectList;
	private Object object;
	private Boolean success;

	public ResponseObject(String responseMessage) {
		this.responseMessage = responseMessage;
	}

}
