package tr.bays.entity.kullanici;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tr.bays.entity.BaseEntity;

@SuppressWarnings("serial")
@Entity(name = "KullaniciIletisim")
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "kullanici_iletisim")
@Cache(region = "baysKullaniciIletisimCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class KullaniciIletisim extends BaseEntity implements Serializable {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kullanici_id", nullable = false)
	private Kullanici kullanici_id;
	@Column(name = "aktif", nullable = false)
	private int aktif;
	@Column(name = "eposta", unique = true)
	private String eposta;
	@Column(name = "cep_telefonu", unique = true)
	private String cep_telefonu;

	public String toString() {
		String str = "{";

		str += "ID = [" + id + "], ";
		str += "Kullanici ID = [" + (kullanici_id != null ? kullanici_id.getId() : kullanici_id) + "], ";
		str += "Aktif = [" + aktif + "], ";
		str += "e-Posta = [" + eposta + "], ";
		str += "Cep Telefonu = [" + cep_telefonu + "], ";

		str += "}";

		return str;
	}

}
