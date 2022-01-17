package tr.bays.service.impl.email;
import java.io.File;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tr.bays.dto.kullanici.KullaniciDto;
import tr.bays.service.email.EmailService;
import util.KutukIslemleri;

@Service("EmailService")
@Slf4j
public class EmailServiceImpl implements EmailService {

	@Value("${spring.mail.properties.mail.smtp.from}")
    private String NOREPLY_ADDRESS;

//	private static Log LOGGER = LogFactory.getLog(BaseCrudController.class);

    @Autowired
    private JavaMailSender emailSender;

    public EmailServiceImpl() {
    }
    
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(NOREPLY_ADDRESS);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            log.info("Mail gonderiliyor..."+to);
            emailSender.send(message);
            log.info("Mail gonderildi."+to);
            
        } catch (MailException exception) {
        	log.info("------------Mail Gonderim Hatasi");
        	exception.printStackTrace();
        	log.error("Error", exception);
        }
    }


    @Override
    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(NOREPLY_ADDRESS);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment(new KutukIslemleri().randomName()+".pdf", file);

            log.info("Mail gonderiliyor..."+to);
            emailSender.send(message);
            log.info("Mail gonderildi."+to);
            
        } catch (MessagingException e) {
        	log.error("Error", e);
        }
    }


	@Override
	@Async
	public void sendMessageWithAttachment(List<String> toList, String subject, String text, File fileToSend, String ip, KullaniciDto kullaniciDto) {     
		for (String to : toList) {
            try {
                MimeMessage message = emailSender.createMimeMessage();
                // pass 'true' to the constructor to create a multipart message
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setFrom(NOREPLY_ADDRESS);
                helper.setTo(to.toLowerCase());
                helper.setSubject(subject);
                helper.setText(text);

                FileSystemResource file = new FileSystemResource(fileToSend);
                helper.addAttachment(fileToSend.getName(), file);

                log.info("Mail gonderiliyor..."+to);
                emailSender.send(message);
                log.info("Mail gonderildi."+to);
                
            } catch (MessagingException e) {
            	log.error("Error", e);
            }
		}
	}


//	@Override
//	public void sendMessageWithAttachment(String to, String subject, String text, String[] pathToAttachment) {
//		 try {
//	            MimeMessage message = emailSender.createMimeMessage();
//	            // pass 'true' to the constructor to create a multipart message
//	            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//	            helper.setFrom(NOREPLY_ADDRESS);
//	            helper.setTo(to);
//	            helper.setSubject(subject);
//	            helper.setText(text);
//
//	            FileSystemResource file = new FileSystemResource(new File(pathToAttachment[0]));
//	            helper.addAttachment(new KutukIslemleri().randomName()+".pdf", file);
//	            
//	            file = new FileSystemResource(new File(pathToAttachment[1]));
//	            helper.addAttachment(new KutukIslemleri().randomName()+".pdf", file);
//
//	            emailSender.send(message);
//	        } catch (MessagingException e) {
//	        	LOGGER.error("Error", e);
//	        }
//	}


	@Override
	@Async
	public void sendMessageWithAttachment(List<String> toList, String subject, String text, List<File> fileList, String ip, KullaniciDto kullaniciDto) {
		for (String to : toList) {
            try {
                MimeMessage message = emailSender.createMimeMessage();
                // pass 'true' to the constructor to create a multipart message
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setFrom(NOREPLY_ADDRESS);
                helper.setTo(to.toLowerCase());
                helper.setSubject(subject);
                helper.setText(text, true);

                FileSystemResource fileTmp = null;
                for (File file : fileList) {
                	fileTmp = new FileSystemResource(file);
                	helper.addAttachment(file.getName(), fileTmp);
				}

                log.info("Mail gonderiliyor..."+to);
                emailSender.send(message);
                log.info("Mail gonderildi."+to);
                
            } catch (MessagingException e) {
            	log.error("Error", e);
            }
		}
	}
	
	
    
}