package tr.bays.controller.sistem;

import java.io.Serializable;

import tr.bays.BaysJsfController;
import tr.bays.service.hataKodlari.HataKodlariService;

@SuppressWarnings("serial")
@BaysJsfController
public class HataKodlariController implements Serializable {

	private HataKodlariService hataKodlariService;

	public HataKodlariController(HataKodlariService hataKodlariService) {
		this.hataKodlariService = hataKodlariService;
	}

	public String hataMesajiGetir(int kod) {
		return hataKodlariService.hataMesajiGetir(kod);
	}

	public String hataMesajiGetir(int kod, String parametre_1) {
		return hataKodlariService.hataMesajiGetir(kod, parametre_1);
	}

	public String hataMesajiGetir(int kod, String parametre_1, String parametre_2) {
		return hataKodlariService.hataMesajiGetir(kod, parametre_1, parametre_2);
	}

	public String hataMesajiGetir(int kod, String parametre_1, String parametre_2, String parametre_3) {
		return hataKodlariService.hataMesajiGetir(kod, parametre_1, parametre_2, parametre_3);
	}
	
}