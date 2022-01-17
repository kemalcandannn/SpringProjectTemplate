package tr.bays.service.impl.hataKodlari;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.springframework.stereotype.Service;

import tr.bays.service.hataKodlari.HataKodlariService;
import util.enums.HataKodlariEnum;


@Service
public class HataKodlariServiceImpl implements HataKodlariService {

	private static ResourceBundle bundle = null;
	
	private static final String ayirac = "|";
	
	public HataKodlariServiceImpl() {
		bundleYukle();
	}

	private void bundleYukle() {
		if(bundle == null)
			bundle = ResourceBundle.getBundle("error/hataKodlari");
	}

	@Override
	public void alertYaz(String baslik, String icerik) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, baslik, icerik);
        PrimeFaces.current().dialog().showMessageDynamic(message);
	}
	
	private void ekranaBilgiYaz(String mesaj1, String islem) {
		String mesaj2 = null;
		if(mesaj1.contains(ayirac)) {
			mesaj2 = mesaj1.substring(mesaj1.indexOf("|")+1);
			mesaj1 = mesaj1.substring(0,mesaj1.indexOf("|"));
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mesaj1, mesaj2));
	}

	private void ekranaTehlikeYaz(String mesaj1, String islem) {
		String mesaj2 = null;
		if(mesaj1.contains(ayirac)) {
			mesaj2 = mesaj1.substring(mesaj1.indexOf("|")+1);
			mesaj1 = mesaj1.substring(0,mesaj1.indexOf("|"));
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mesaj1, mesaj2));
	}

	private void ekranaHataYaz(String mesaj1, String islem) {
		String mesaj2 = null;
		if(mesaj1.contains(ayirac)) {
			mesaj2 = mesaj1.substring(mesaj1.indexOf("|")+1);
			mesaj1 = mesaj1.substring(0,mesaj1.indexOf("|"));
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mesaj1, mesaj2));
	}

	@Override
	public void vtHatasi(Exception e, String sinif, int kod, String islem, String... parametre) {
		String modulIsmi = modulGetir(sinif);
		String ekranIsmi = ekranGetir(sinif,islem);
		
		String value = bundle.getString(kod+"");
		if(value == null)//olmamasi gerekiyor
			return;
		
		value = value.substring(value.indexOf("|")+1);
		value = value.substring(value.indexOf("|")+1);
		value = value.substring(value.indexOf("|")+1);
		
		String mesaj = "[" + kod + "]" + " " + value;
		
		if(parametre != null) {
			mesaj = parametreIsle(mesaj,parametre);
		}
		
		String ekranAdi = ekranIsmi + "/" + modulIsmi;
		ekranaHataYaz(mesaj, ekranAdi);		
		
		//hatalari bastiriyoruz.
//		if(e != null) {
//			
//			StackTraceElement[] elements = e.getStackTrace();
//	    	StackTraceElement[] five = new StackTraceElement[5];
//	    	String hata = "";
//	    	for(int i = 0; i < elements.length; i++) {
//	    		hata = elements[i].toString();
//	    		if(hata.startsWith("tr.bays.controller")) {
//					for(int j = 0; j < 5; j++) {
//						five[j] = elements[i+j];
//					}
//					break;
//				}
//	    	}
//	    	String fiveStr = Arrays.toString(five);
//			sunucuHataOzelLogService.ozelLogKaydet(fiveStr);
//		}
	}

	private String ekranGetir(String sinif, String islem) {
		return sinif.substring(sinif.lastIndexOf(".")+1)+" "+islem;
	}

	private String modulGetir(String sinif) {
		sinif = sinif.substring(0, sinif.lastIndexOf("."));
		return sinif.substring(sinif.lastIndexOf(".")+1);
	}

	@Override
	public void hataKoduGetir(int kod, String... parametre) {
		String value = bundle.getString(kod+"");
		if(value == null)//olmamasi gerekiyor
			return;
		//ERROR|Belge|SDF Belge Sorgulama|Sorgulama başarısız. gibi
		//split fonksiyonu kullanilmamasinin sebebi, mesaj içerisinde de | kullanılıyor ya da kullanılmıyor
		String hataEnum = value.substring(0, value.indexOf("|"));
		HataKodlariEnum hkEnum = HataKodlariEnum.getWithName(hataEnum);
		value = value.substring(value.indexOf("|")+1);
		
		String ekranIsmi = value.substring(0, value.indexOf("|"));
		value = value.substring(value.indexOf("|")+1);
		
		String modulIsmi = value.substring(0, value.indexOf("|"));
		value = value.substring(value.indexOf("|")+1);
		
		String mesaj = "[" + kod + "] - " + value;
		
		if(parametre != null) {
			mesaj = parametreIsle(mesaj,parametre);
		}
		
		String ekranAdi = ekranIsmi + "/" + modulIsmi;
		
		switch (hkEnum) {
		case INFO:
			ekranaBilgiYaz(mesaj, ekranAdi);
			
			break;
		case WARNING:
			
			ekranaTehlikeYaz(mesaj, ekranAdi);						
			break;
		case ERROR:
			
			ekranaHataYaz(mesaj, ekranAdi);						
			break;
			
		default:
			break;
		}
	}//hataKoduGetir

	private String parametreIsle(String mesaj, String[] parametre) {
		for (int i = 0; i < parametre.length; i++) {
			mesaj = mesaj.replaceAll("#p"+(i+1)+"#", parametre[i]);
		}
		return mesaj;
	}

	@Override
	public String hataMesajiGetir(int kod, String... parametre) {
		String value = bundle.getString(kod+"");
		if(value == null)//olmamasi gerekiyor
			return kod+"";

		//ERROR|#p1# #p2#
		//split fonksiyonu kullanilmamasinin sebebi, mesaj içerisinde de | kullanılıyor ya da kullanılmıyor
		value = value.substring(value.indexOf("|")+1);
		
//		String mesaj = "[" + kod + "] - " + value;
		String mesaj = value;
		
		if(parametre != null) {
			mesaj = parametreIsle(mesaj,parametre);
		}
		
		return mesaj;
	}

}
