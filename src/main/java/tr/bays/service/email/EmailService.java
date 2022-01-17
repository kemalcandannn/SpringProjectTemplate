package tr.bays.service.email;

import java.io.File;
import java.util.List;

import tr.bays.dto.kullanici.KullaniciDto;

public interface EmailService {

	void sendSimpleMessage(String to, String subject, String text);

	void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment);

	void sendMessageWithAttachment(List<String> toList, String subject, String text, File file, String ip, KullaniciDto kullaniciDto);

	void sendMessageWithAttachment(List<String> toList, String subject, String text, List<File> fileList, String ip, KullaniciDto kullaniciDto);

}