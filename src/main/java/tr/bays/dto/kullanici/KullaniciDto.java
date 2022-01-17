package tr.bays.dto.kullanici;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tr.bays.dto.yalin.kullanici.KullaniciYalinDto;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
public class KullaniciDto extends KullaniciYalinDto {
	private String kullanici_adi;
	private String parola;
	private int parola_degistir;
	private Date parola_degistirme_tarihi;
	private String tc_kimlik_no;
	private int cinsiyet;
	private Date dogum_tarihi;
}
