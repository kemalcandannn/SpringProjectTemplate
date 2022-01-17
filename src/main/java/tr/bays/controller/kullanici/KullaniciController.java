package tr.bays.controller.kullanici;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tr.bays.BaysJsfController;
import tr.bays.common.base.BaseCrudController;
import tr.bays.controller.sistem.SessionController;
import tr.bays.dto.kullanici.KullaniciDto;
import tr.bays.service.kullanici.KullaniciService;
import util.WordUtils;
import util.enums.GerekliEnum;

@BaysJsfController
@Data
@EqualsAndHashCode(callSuper = false)
public class KullaniciController extends BaseCrudController<KullaniciDto> {

	private KullaniciService kullaniciService;

	private SessionController sessionController;
	private PasswordEncoder passwordEncoder;

	private String mevcutParola;
	private String yeniParola;
	private String yeniParolaTekrar;
	
	public KullaniciController(KullaniciService kullaniciService, SessionController sessionController, PasswordEncoder passwordEncoder) {
		super(kullaniciService);

		this.kullaniciService = kullaniciService;
		this.sessionController = sessionController;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected KullaniciDto createNewModel() {
		return new KullaniciDto();
	}
	
	@Override
	protected void preEkle() throws Exception {
		super.preEkle();
		
		getSecilen().setParola(passwordEncoder.encode(getSecilen().getParola()));
		getSecilen().setParola_degistir(GerekliEnum.GEREKSIZ.getValue());
		getSecilen().setParola_degistirme_tarihi(new Date());
	}
	
	public String sessionAdSoyadGetir() {
		return sessionController.getKullaniciDto().getAdSoyad();
	}

	public void parolaGuncelle() {
		KullaniciDto kullaniciDto = util.SecurityUtil.getOnlineUser(kullaniciService);

		if (passwordEncoder.matches(mevcutParola, kullaniciDto.getParola()) == false) {
			hataKodlariService.hataKoduGetir(2);
			return;
		}

		if (!yeniParola.equals(yeniParolaTekrar)) {
			hataKodlariService.hataKoduGetir(3);
			return;
		}

		if (mevcutParola.equals(yeniParola) && mevcutParola.equals(yeniParolaTekrar)) {
			hataKodlariService.hataKoduGetir(4);
			return;
		}

		if (WordUtils.buyukHarfIceriyorMu(yeniParola) == false) {
			hataKodlariService.hataKoduGetir(5);
			return;
		}

		if (WordUtils.sayisalDegerIceriyorMu(yeniParola) == false) {
			hataKodlariService.hataKoduGetir(6);
			return;
		}

		if (WordUtils.paroladakiMinimumKarakterSayisindanFazlaMi(yeniParola) == false) {
			hataKodlariService.hataKoduGetir(7, (WordUtils.PAROLADAKI_MINIMUM_KARAKTER_SAYISI + ""));
			return;
		}

		if (WordUtils.ozelKarakterIceriyorMu(yeniParola) == false) {
			hataKodlariService.hataKoduGetir(8);
			return;
		}

		boolean lastPasswordSame = passwordEncoder.matches(yeniParola, kullaniciDto.getParola());
		if (lastPasswordSame) {
			hataKodlariService.hataKoduGetir(9);
			return;
		}

		try {
			kullaniciDto = kullaniciService.parolaGuncelle(kullaniciDto, passwordEncoder.encode(yeniParola));
			sessionController.setKullaniciDto(kullaniciDto);
			hataKodlariService.hataKoduGetir(10);
		} catch (Exception e) {
			e.printStackTrace();
			hataKodlariService.hataKoduGetir(11);
		}
	}

}
