/**
 * 
 */
package com.preciouspaws.test;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.jboss.logging.Logger;

import com.preciouspaws.controller.AppointmentController;

/**
 * @author pavanrao
 *
 */
public class BusinessUtil {

	private static final Logger logger = Logger.getLogger(AppointmentController.class);
	
	public static void sendEmail(String to, String subject, String message) {
		Email email = new SimpleEmail();
		email.setHostName("smtp.googlemail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("pavan47kalyan@gmail.com", "CHINTUPS"));
		email.setSSLOnConnect(true);
		try {
			email.setFrom("pavan47kalyan@gmail.com");
			email.setSubject(subject);
			email.setMsg(message);
			email.addTo(to);
			email.send();
			logger.info("Sent email to "+to+" successfully");
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			logger.error("Failed to send email to "+to,e);
			e.printStackTrace();
		}
		
	}

}
