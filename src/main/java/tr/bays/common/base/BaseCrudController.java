package tr.bays.common.base;

import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataIntegrityViolationException;

import lombok.Getter;
import tr.bays.service.BaseCrudService;
import tr.bays.service.hataKodlari.HataKodlariService;
import util.BaysResource;

@Getter
public abstract class BaseCrudController<DTO extends BaseDto> {
	protected static Logger LOGGER = LoggerFactory.getLogger(BaseCrudController.class);
	private BaseCrudService<DTO> crudService;

	private DTO secilen;

	private boolean formGoster;

	protected BaseLazyDataModel<DTO> list;

	@Autowired
	protected HataKodlariService hataKodlariService;

	@Autowired
	private ResourceLoader resourceLoader;

	public BaseCrudController(BaseCrudService<DTO> crudService) {
		this.crudService = crudService;
		this.list = new BaseLazyDataModel<DTO>(this.crudService, createNewModel());
	}

	protected abstract DTO createNewModel();

	@PostConstruct
	public void init() {
		Locale.setDefault(new Locale("TR"));
	}

	public void addInfo(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
	}

	public void addWarning(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, null));
	}

	public void addError(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
	}

	public void formTemizle() {
		secilen = null;
		list.setSorguKriteri(createNewModel());
	}

	public void onRowSelect(SelectEvent<DTO> event) {
		if (event.getObject() != null) {
			this.secilen = (DTO) event.getObject();
		}
	}

	public void onRowUnselect(UnselectEvent<DTO> event) {
		this.secilen = null;
	}

	public void setSecilen(DTO secilen) {
		this.secilen = secilen;
	}

	public DTO getSecilen() {
		return secilen;
	}

	public DTO getSorguKriteri() {
		return list.getSorguKriteri();
	}

	public void setSorguKriteri(DTO sorguKriteri) {
		list.setSorguKriteri(sorguKriteri);
	}

	protected List<DTO> listele() {
		return crudService.listele();
	}

	public void ekleFormAc() {
		setFormGoster(true);
		secilen = createNewModel();
	}

	public void guncelleFormAc() {
		if (secilen == null) {
			addError(BaysResource.get("kayit_secilmedi"));
			return;
		}
		setFormGoster(true);
	}

	protected boolean validateEkle() {
		return true;
	}

	protected boolean validateGuncelle() {
		return true;
	}

	protected void preEkle() throws Exception {
	}

	protected void preGuncelle() throws Exception {
	}

	public DTO kaydet() {
		if (secilen.getId() != null && secilen.getId() != 0) {
			try {
				if (validateGuncelle()) {
					preGuncelle();
					nesneKaydet(BaysResource.get("guncelle"));
					if (LOGGER.isDebugEnabled())
						LOGGER.debug("Mevcut kayit guncellendi: " + secilen.toString());
					afterGuncelle();
					return secilen;
				} else {
					LOGGER.error("Validation Error!");
				}
			} catch (Exception e) {
				LOGGER.error("Kayit guncellenirken bir hata olustu: " + secilen.toString(), e);
				addError(BaysResource.get("kaydetme_basarisiz"));
			}
		} else {
			try {
				if (validateEkle()) {
					preEkle();
					nesneKaydet(BaysResource.get("ekle"));
					if (LOGGER.isDebugEnabled())
						LOGGER.debug("Yeni kayit olusturuldu: " + secilen.toString());
					afterEkle();
					return secilen;
				} else {
					LOGGER.error("Validation Error!");
				}
			} catch (Exception e) {
				LOGGER.error("Kayit eklenirken bir hata olustu: " + secilen.toString(), e);
				addError(BaysResource.get("kaydetme_basarisiz"));
			}
		}
		return null;
	}

	public void nesneKaydet(String islem) {
		try {
			secilen = crudService.kaydet(secilen);
			addInfo(BaysResource.get("kaydetme_basarili"));
		} catch (DataIntegrityViolationException e) {
			hataKodlariService.vtHatasi(e, this.getClass().getName(), 401, islem);
		} catch (Exception e) {
			LOGGER.error("Error", e);
			hataKodlariService.vtHatasi(e, this.getClass().getName(), 402, islem, e.getMessage());
		}
	}

	protected void afterEkle() {
		setFormGoster(false);
	}

	protected void afterGuncelle() {
		setFormGoster(false);
	}

	protected boolean validateSil() {
		return secilen != null;
	}

	protected void preSil() {
	}

	public boolean sil() {
		boolean ret = false;

		if (validateSil()) {
			preSil();
			ret = nesneSil(secilen);
			if (ret == false)
				return ret;
			if (LOGGER.isDebugEnabled())
				LOGGER.debug(BaysResource.get("kayit_silindi") + ": " + secilen.toString());
			afterSil();
			return ret;
		} else {
			addError(BaysResource.get("kayit_secilmedi"));
			return false;
		}
	}

	public boolean nesneSil(DTO entity) {
		try {
			crudService.sil(entity);
			addInfo(BaysResource.get("silme_basarili"));
		} catch (DataIntegrityViolationException e) {
			hataKodlariService.vtHatasi(e, this.getClass().getName(), 403, "Silme");
			return false;
		} catch (Exception e) {
			LOGGER.error("Error", e);
			hataKodlariService.vtHatasi(e, this.getClass().getName(), 404, "Silme", e.getMessage());
			return false;
		}
		return true;
	}

	protected void afterSil() {
		secilen = null;
	}

	public void iptal() {
		setFormGoster(false);
		setSecilen(null);
	}

	public boolean isFormGoster() {
		return formGoster;
	}

	public void setFormGoster(boolean formGoster) {
		this.formGoster = formGoster;
	}

	public BaseLazyDataModel<DTO> getList() {
		return this.list;
	}

	public List<DTO> autoComplete(String query) {
		return crudService.combo(query);
	}

}