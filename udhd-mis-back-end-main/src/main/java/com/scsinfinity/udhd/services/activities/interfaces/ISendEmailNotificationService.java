package com.scsinfinity.udhd.services.activities.interfaces;

import com.scsinfinity.udhd.services.activities.dto.SendEmailDTO;

public interface ISendEmailNotificationService {

	Boolean sendEmail(SendEmailDTO emailDTO);
}
