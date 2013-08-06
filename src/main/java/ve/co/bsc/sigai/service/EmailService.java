/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ve.co.bsc.sigai.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * 
 * @author User
 */
@Service
public class EmailService implements ApplicationContextAware, InitializingBean {

	private static final Logger logger = Logger.getLogger(EmailService.class);
	private static EmailService emailService;
	private MailSender mailSender;
	private static ApplicationContext context;
	private ExecutorService executor;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.executor = Executors.newFixedThreadPool(1);
	}

	private static ApplicationContext getContext() {
		if (context != null) {
			return context;
		} else {
			throw new IllegalStateException(
					"The Spring application context is not yet available. "
							+ "The call to this method has been performed before the application context "
							+ "provider was initialized");
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		if (context == null) {
			context = applicationContext;
		} else {
			throw new IllegalStateException(
					"The application context provider was already initialized. "
							+ "It is illegal to place try to initialize the context provider twice or create "
							+ "two different beans of this type (even if the contexts are different)");
		}
	}

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public static EmailService getInstance() {

		if (emailService == null) {
			emailService = (EmailService) context.getBean("emailService");
		}
		logger.debug("EmailService.getInstance(): instance is: " + emailService);
		return emailService;
	}

	public void sendMessage(final String from, final String to,
			final String subject, final String message) throws MailException {

		final SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);
		msg.setFrom(from);
		msg.setSubject(subject);
		msg.setText(message);

		logger.debug("Colocando mensaje en cola [" + msg + "]");
		this.executor.submit(new Runnable() {

			@Override
			public void run() {
				try {
					logger.debug(Thread.currentThread().getId()
							+ " intentando enviar mail [" + msg + "]");
					mailSender.send(msg);
					logger.debug(Thread.currentThread().getId()
							+ " mail enviado satisfactoriamente [" + msg + "]");
				} catch (Exception e) {
					logger.error("\n\nNo se pudo enviar mensaje [" + msg + "]"
							+ e.getCause().toString() + "\n", e.getCause());
				}
			}
		});
		logger.debug("Colocado mensaje en cola [" + msg + "]");
	}
}
