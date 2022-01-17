package tr.bays.controller.sistem;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import lombok.Getter;
import tr.bays.BaysJsfController;
import tr.bays.dto.kullanici.KullaniciDto;
import tr.bays.service.kullanici.KullaniciService;
import util.Util;

@SuppressWarnings("serial")
@BaysJsfController
@Getter
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
//ayrıca basecruddan extends etmiyor, zira crud değil
public class SessionController implements Serializable {

	private KullaniciDto kullaniciDto;
	private List<String> yetkiListesi;
	private KullaniciService kullaniciService;

	public SessionController(KullaniciService kullaniciService) {
		this.kullaniciService = kullaniciService;
	}

	@PostConstruct
	public void init() {
		getKullanici();
		yetkileriHazirla();
	}

	public void onItemSelect() {}

	public KullaniciDto getKullanici() {
		if (kullaniciDto == null) {
			kullaniciDto = kullaniciService.kullaniciAdiIleGetir(Util.getKullaniciAdi());
		}

		return kullaniciDto;
	}

	public void setKullaniciDto(KullaniciDto kullaniciDto) {
		this.kullaniciDto = kullaniciDto;
	}

	public void yetkileriHazirla() {
		yetkiListesi = new ArrayList<String>();
	}

	public boolean yetkiliMi(String str) {
		if (yetkiListesi.size() == 0)
			return true; // TODO şimdilik her zaman yetkili
		return yetkiListesi.contains(str);
	}

	public void cikis() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession(false);
		session.invalidate();
		FacesContext temp = FacesContext.getCurrentInstance();
		try {
			temp.getExternalContext().redirect("/logout");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
