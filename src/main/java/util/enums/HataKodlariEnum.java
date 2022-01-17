package util.enums;

import util.BaysResource;

public enum HataKodlariEnum {
	INFO (1, "INFO", BaysResource.get("bilgi")), 
	WARNING (2, "WARNING", BaysResource.get("uyari")),
	ERROR (3, "ERROR", BaysResource.get("hata"));

	private int value;
	private String name;
	private String label;

	private HataKodlariEnum(int value, String name, String label) {
		this.value = value;
		this.name = name;
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

	public static HataKodlariEnum getWithName(String name) {
		for (HataKodlariEnum hkEnum : HataKodlariEnum.values()) {
			if (hkEnum.name.equals(name))
				return hkEnum;
		}
		return INFO;
	}

	public static String getLabel(int val) {
		String label = "";

		for (HataKodlariEnum hataKodlariEnum : HataKodlariEnum.values()) {
			if (hataKodlariEnum.getValue() == val) {
				label = hataKodlariEnum.getLabel();
				break;
			}
		}

		return label;
	}
}
