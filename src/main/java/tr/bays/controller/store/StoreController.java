package tr.bays.controller.store;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import tr.bays.BaysJsfController;
import util.enums.AktifEnum;
import util.enums.CinsiyetEnum;
import util.enums.EvetHayirEnum;
import util.enums.GerekliEnum;
import util.enums.HataKodlariEnum;
import util.enums.RolEnum;

@BaysJsfController
public class StoreController {

	public List<SelectItem> getAktifStore() {
		List<SelectItem> aktifStore = new ArrayList<SelectItem>();
		for (AktifEnum aktifEnum : AktifEnum.values()) {
			aktifStore.add(new SelectItem(aktifEnum.getValue(), aktifEnum.getLabel()));
		}

		return aktifStore;
	}

	public List<SelectItem> getCinsiyetStore() {
		List<SelectItem> cinsiyetStore = new ArrayList<SelectItem>();
		for (CinsiyetEnum cinsiyetEnum : CinsiyetEnum.values()) {
			cinsiyetStore.add(new SelectItem(cinsiyetEnum.getValue(), cinsiyetEnum.getLabel()));
		}

		return cinsiyetStore;
	}

	public List<SelectItem> getEvetHayirStore() {
		List<SelectItem> evetHayirStore = new ArrayList<SelectItem>();
		for (EvetHayirEnum evetHayirEnum : EvetHayirEnum.values()) {
			evetHayirStore.add(new SelectItem(evetHayirEnum.getValue(), evetHayirEnum.getLabel()));
		}

		return evetHayirStore;
	}

	public List<SelectItem> getGerekliStore() {
		List<SelectItem> gerekliStore = new ArrayList<SelectItem>();
		for (GerekliEnum gerekliEnum : GerekliEnum.values()) {
			gerekliStore.add(new SelectItem(gerekliEnum.getValue(), gerekliEnum.getLabel()));
		}

		return gerekliStore;
	}

	public List<SelectItem> getHataKodlariStore() {
		List<SelectItem> hataKodlariStore = new ArrayList<SelectItem>();
		for (HataKodlariEnum hataKodlariEnum : HataKodlariEnum.values()) {
			hataKodlariStore.add(new SelectItem(hataKodlariEnum.getValue(), hataKodlariEnum.getLabel()));
		}

		return hataKodlariStore;
	}

	public List<SelectItem> getRolStore() {
		List<SelectItem> rolStore = new ArrayList<SelectItem>();
		for (RolEnum rolEnum : RolEnum.values()) {
			rolStore.add(new SelectItem(rolEnum.getValue(), rolEnum.getLabel()));
		}

		return rolStore;
	}

	public String getLabel(List<SelectItem> store, int value) {
		String label = "";
		try {
			for (SelectItem selectItem : store) {
				if(((Integer) selectItem.getValue()) == value) {
					label = selectItem.getLabel();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return label;
	}

}
