/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ve.co.bsc.sigai.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * 
 * @author User
 */
@Service
public class EmailService {

	private static final Logger logger = Logger.getLogger(EmailService.class);

	@Autowired
	private MailSender mailSender;

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMessage(String from, String to, String subject, String msg)
			throws MailException {
		logger.debug("Metodo para el Envio de Correo Electrnico");
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		try {
			mailSender.send(message);
		} catch (Exception e) {
			logger.error("No se pudo enviar mensaje [" + message + "]", e);
		}
		logger.debug("Correo Electrnico enviado");
	}
}
