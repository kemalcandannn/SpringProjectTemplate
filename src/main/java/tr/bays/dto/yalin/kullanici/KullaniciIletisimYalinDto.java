package tr.bays.dto.yalin.kullanici;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tr.bays.common.base.BaseDto;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
public class KullaniciIletisimYalinDto extends BaseDto {
	private String eposta;
	private String cep_telefonu;
}
