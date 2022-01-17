package tr.bays.dto.kullanici;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tr.bays.dto.yalin.kullanici.KullaniciIletisimYalinDto;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
public class KullaniciIletisimDto extends KullaniciIletisimYalinDto {
	private KullaniciDto kullanici_id;
	private int aktif;
}
