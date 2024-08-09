package com.scsinfinity.udhd.dao.entities.deas;

public enum TrialUploadStatusEnum {

	ACCEPTED("Accepted"), ACCEPTED_WITH_WARNING("Accepted with warning"), IGNORED("Ignored"), REJECTED("Rejected");

	private final String text;

	/**
	 * @param text
	 */
	TrialUploadStatusEnum(final String text) {
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return text;
	}
}
