package tr.bays.service.kullanici;

import java.util.List;

import tr.bays.dto.kullanici.KullaniciIletisimDto;
import tr.bays.service.BaseCrudService;

public interface KullaniciIletisimService extends BaseCrudService<KullaniciIletisimDto> {

	List<KullaniciIletisimDto> kullanicininTumIletisimBilgileriniGetir(Long kullanici_id);

	void aktifHaleGetir(KullaniciIletisimDto secilen);

}
