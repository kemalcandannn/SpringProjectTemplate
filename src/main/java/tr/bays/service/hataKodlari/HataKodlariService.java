package tr.bays.service.hataKodlari;

public interface HataKodlariService {

	void hataKoduGetir(int kod, String... parametre);

	void vtHatasi(Exception e, String sinif, int kod, String islem, String... parametre);

	String hataMesajiGetir(int kod, String... parametre);

	void alertYaz(String baslik, String icerik);

}
