package tr.bays.dto.yalin.kullanici;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tr.bays.common.base.BaseDto;
import util.Util;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
public class KullaniciYalinDto extends BaseDto {
	private String ad;
	private String soyad;

	public String getAdSoyad() {
		if (ad == null || soyad == null)
			return null;
		return ad + " " + soyad;
	}

	public String getAdSoyadKisaltma() {
		String adKisaltma = Util.notEmpty(ad) ? ((ad.length() > 10) ? ad.substring(0, 10) + "." : ad) : "";
		String soyadKisaltma = Util.notEmpty(soyad) ? ((soyad != null && soyad.length() > 10) ? soyad.substring(0, 10) + "." : soyad) : "";

		return adKisaltma + " " + soyadKisaltma;
	}

}
