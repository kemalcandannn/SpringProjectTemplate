package tr.bays.entity.kullanici;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tr.bays.entity.BaseEntity;
import util.DateTimeUtil;

@SuppressWarnings("serial")
@Entity(name = "Kullanici")
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "kullanici")
@Cache(region = "baysKullaniciCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Kullanici extends BaseEntity implements Serializable {

	@Column(name = "kullanici_adi", unique = true, nullable = false)
	private String kullanici_adi;
	@Column(name = "parola", nullable = false)
	private String parola;
	@Column(name = "parola_degistir", nullable = false)
	private int parola_degistir;
	@Column(name = "parola_degistirme_tarihi")
	private Date parola_degistirme_tarihi;
	@Column(name = "tc_kimlik_no", nullable = false, length = 11)
	private String tc_kimlik_no;
	@Column(name = "ad", nullable = false)
	private String ad;
	@Column(name = "soyad", nullable = false)
	private String soyad;
	@Column(name = "cinsiyet", nullable = false)
	private int cinsiyet;
	@Column(name = "dogum_tarihi")
	private Date dogum_tarihi;

	public String toString() {
		String str = "{";

		str += "ID = [" + id + "], ";
		str += "Kullanici Adi = [" + kullanici_adi + "], ";
		str += "Parola = [" + parola + "], ";
		str += "Parola Degistir = [" + parola_degistir + "], ";
		str += "Parola Degistirme Tarihi = [" + DateTimeUtil.getDateString(parola_degistirme_tarihi, "dd/MM/yyyy HH:mm") + "], ";
		str += "TC Kimlik No = [" + tc_kimlik_no + "], ";
		str += "Ad = [" + ad + "], ";
		str += "Soyad = [" + soyad + "], ";
		str += "Cinsiyet = [" + cinsiyet + "], ";
		str += "Dogum Tarihi = [" + DateTimeUtil.getDateString(dogum_tarihi, "dd/MM/yyyy HH:mm") + "], ";

		str += "}";

		return str;
	}

}
