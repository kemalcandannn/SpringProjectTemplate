package util.enums;

import util.BaysResource;

public enum AktifEnum {
	AKTIF (1, BaysResource.get("aktif")), 
	PASIF (2, BaysResource.get("pasif"));

	private final int value;
	private final String label;

	AktifEnum(int value, String label) {
		this.value = value;
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

	public static String getLabel(int val) {
		String label = "";

		for (AktifEnum aktifEnum : AktifEnum.values()) {
			if (aktifEnum.getValue() == val) {
				label = aktifEnum.getLabel();
				break;
			}
		}

		return label;
	}

}